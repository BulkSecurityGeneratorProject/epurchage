package com.softage.epurchase.web.rest;

import com.softage.epurchase.EpurchaseApp;

import com.softage.epurchase.domain.ReqItem;
import com.softage.epurchase.repository.ReqItemRepository;
import com.softage.epurchase.repository.search.ReqItemSearchRepository;
import com.softage.epurchase.service.dto.ReqItemDTO;
import com.softage.epurchase.service.mapper.ReqItemMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReqItemResource REST controller.
 *
 * @see ReqItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpurchaseApp.class)
public class ReqItemResourceIntTest {

    private static final Long DEFAULT_ITEM_ID = 1L;
    private static final Long UPDATED_ITEM_ID = 2L;

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIFICATION_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_SPECIFICATION_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTY = 1;
    private static final Integer UPDATED_QTY = 2;

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    @Inject
    private ReqItemRepository reqItemRepository;

    @Inject
    private ReqItemMapper reqItemMapper;

    @Inject
    private ReqItemSearchRepository reqItemSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restReqItemMockMvc;

    private ReqItem reqItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReqItemResource reqItemResource = new ReqItemResource();
        ReflectionTestUtils.setField(reqItemResource, "reqItemSearchRepository", reqItemSearchRepository);
        ReflectionTestUtils.setField(reqItemResource, "reqItemRepository", reqItemRepository);
        ReflectionTestUtils.setField(reqItemResource, "reqItemMapper", reqItemMapper);
        this.restReqItemMockMvc = MockMvcBuilders.standaloneSetup(reqItemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReqItem createEntity(EntityManager em) {
        ReqItem reqItem = new ReqItem()
                .itemId(DEFAULT_ITEM_ID)
                .itemName(DEFAULT_ITEM_NAME)
                .purpose(DEFAULT_PURPOSE)
                .specificationItem(DEFAULT_SPECIFICATION_ITEM)
                .location(DEFAULT_LOCATION)
                .officeCode(DEFAULT_OFFICE_CODE)
                .qty(DEFAULT_QTY)
                .unit(DEFAULT_UNIT);
        return reqItem;
    }

    @Before
    public void initTest() {
        reqItemSearchRepository.deleteAll();
        reqItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createReqItem() throws Exception {
        int databaseSizeBeforeCreate = reqItemRepository.findAll().size();

        // Create the ReqItem
        ReqItemDTO reqItemDTO = reqItemMapper.reqItemToReqItemDTO(reqItem);

        restReqItemMockMvc.perform(post("/api/req-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reqItemDTO)))
            .andExpect(status().isCreated());

        // Validate the ReqItem in the database
        List<ReqItem> reqItemList = reqItemRepository.findAll();
        assertThat(reqItemList).hasSize(databaseSizeBeforeCreate + 1);
        ReqItem testReqItem = reqItemList.get(reqItemList.size() - 1);
        assertThat(testReqItem.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testReqItem.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testReqItem.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testReqItem.getSpecificationItem()).isEqualTo(DEFAULT_SPECIFICATION_ITEM);
        assertThat(testReqItem.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testReqItem.getOfficeCode()).isEqualTo(DEFAULT_OFFICE_CODE);
        assertThat(testReqItem.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testReqItem.getUnit()).isEqualTo(DEFAULT_UNIT);

        // Validate the ReqItem in ElasticSearch
        ReqItem reqItemEs = reqItemSearchRepository.findOne(testReqItem.getId());
        assertThat(reqItemEs).isEqualToComparingFieldByField(testReqItem);
    }

    @Test
    @Transactional
    public void createReqItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reqItemRepository.findAll().size();

        // Create the ReqItem with an existing ID
        ReqItem existingReqItem = new ReqItem();
        existingReqItem.setId(1L);
        ReqItemDTO existingReqItemDTO = reqItemMapper.reqItemToReqItemDTO(existingReqItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReqItemMockMvc.perform(post("/api/req-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingReqItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ReqItem> reqItemList = reqItemRepository.findAll();
        assertThat(reqItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReqItems() throws Exception {
        // Initialize the database
        reqItemRepository.saveAndFlush(reqItem);

        // Get all the reqItemList
        restReqItemMockMvc.perform(get("/api/req-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reqItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].specificationItem").value(hasItem(DEFAULT_SPECIFICATION_ITEM.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].officeCode").value(hasItem(DEFAULT_OFFICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())));
    }

    @Test
    @Transactional
    public void getReqItem() throws Exception {
        // Initialize the database
        reqItemRepository.saveAndFlush(reqItem);

        // Get the reqItem
        restReqItemMockMvc.perform(get("/api/req-items/{id}", reqItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reqItem.getId().intValue()))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME.toString()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE.toString()))
            .andExpect(jsonPath("$.specificationItem").value(DEFAULT_SPECIFICATION_ITEM.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.officeCode").value(DEFAULT_OFFICE_CODE.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReqItem() throws Exception {
        // Get the reqItem
        restReqItemMockMvc.perform(get("/api/req-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReqItem() throws Exception {
        // Initialize the database
        reqItemRepository.saveAndFlush(reqItem);
        reqItemSearchRepository.save(reqItem);
        int databaseSizeBeforeUpdate = reqItemRepository.findAll().size();

        // Update the reqItem
        ReqItem updatedReqItem = reqItemRepository.findOne(reqItem.getId());
        updatedReqItem
                .itemId(UPDATED_ITEM_ID)
                .itemName(UPDATED_ITEM_NAME)
                .purpose(UPDATED_PURPOSE)
                .specificationItem(UPDATED_SPECIFICATION_ITEM)
                .location(UPDATED_LOCATION)
                .officeCode(UPDATED_OFFICE_CODE)
                .qty(UPDATED_QTY)
                .unit(UPDATED_UNIT);
        ReqItemDTO reqItemDTO = reqItemMapper.reqItemToReqItemDTO(updatedReqItem);

        restReqItemMockMvc.perform(put("/api/req-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reqItemDTO)))
            .andExpect(status().isOk());

        // Validate the ReqItem in the database
        List<ReqItem> reqItemList = reqItemRepository.findAll();
        assertThat(reqItemList).hasSize(databaseSizeBeforeUpdate);
        ReqItem testReqItem = reqItemList.get(reqItemList.size() - 1);
        assertThat(testReqItem.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testReqItem.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testReqItem.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testReqItem.getSpecificationItem()).isEqualTo(UPDATED_SPECIFICATION_ITEM);
        assertThat(testReqItem.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testReqItem.getOfficeCode()).isEqualTo(UPDATED_OFFICE_CODE);
        assertThat(testReqItem.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testReqItem.getUnit()).isEqualTo(UPDATED_UNIT);

        // Validate the ReqItem in ElasticSearch
        ReqItem reqItemEs = reqItemSearchRepository.findOne(testReqItem.getId());
        assertThat(reqItemEs).isEqualToComparingFieldByField(testReqItem);
    }

    @Test
    @Transactional
    public void updateNonExistingReqItem() throws Exception {
        int databaseSizeBeforeUpdate = reqItemRepository.findAll().size();

        // Create the ReqItem
        ReqItemDTO reqItemDTO = reqItemMapper.reqItemToReqItemDTO(reqItem);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReqItemMockMvc.perform(put("/api/req-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reqItemDTO)))
            .andExpect(status().isCreated());

        // Validate the ReqItem in the database
        List<ReqItem> reqItemList = reqItemRepository.findAll();
        assertThat(reqItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReqItem() throws Exception {
        // Initialize the database
        reqItemRepository.saveAndFlush(reqItem);
        reqItemSearchRepository.save(reqItem);
        int databaseSizeBeforeDelete = reqItemRepository.findAll().size();

        // Get the reqItem
        restReqItemMockMvc.perform(delete("/api/req-items/{id}", reqItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean reqItemExistsInEs = reqItemSearchRepository.exists(reqItem.getId());
        assertThat(reqItemExistsInEs).isFalse();

        // Validate the database is empty
        List<ReqItem> reqItemList = reqItemRepository.findAll();
        assertThat(reqItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReqItem() throws Exception {
        // Initialize the database
        reqItemRepository.saveAndFlush(reqItem);
        reqItemSearchRepository.save(reqItem);

        // Search the reqItem
        restReqItemMockMvc.perform(get("/api/_search/req-items?query=id:" + reqItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reqItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].specificationItem").value(hasItem(DEFAULT_SPECIFICATION_ITEM.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].officeCode").value(hasItem(DEFAULT_OFFICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())));
    }
}
