package com.softage.epurchase.web.rest;

import com.softage.epurchase.EpurchaseApp;

import com.softage.epurchase.domain.Requisition;
import com.softage.epurchase.repository.RequisitionRepository;
import com.softage.epurchase.repository.search.RequisitionSearchRepository;
import com.softage.epurchase.service.dto.RequisitionDTO;
import com.softage.epurchase.service.mapper.RequisitionMapper;

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
 * Test class for the RequisitionResource REST controller.
 *
 * @see RequisitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpurchaseApp.class)
public class RequisitionResourceIntTest {

    private static final Long DEFAULT_REQ_NUMBER = 1L;
    private static final Long UPDATED_REQ_NUMBER = 2L;

    private static final String DEFAULT_PO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PO_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_REQ_DATE = 1L;
    private static final Long UPDATED_REQ_DATE = 2L;

    private static final Long DEFAULT_PO_DATE = 1L;
    private static final Long UPDATED_PO_DATE = 2L;

    private static final String DEFAULT_SHIP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SHIP_ADDRESS = "BBBBBBBBBB";

    @Inject
    private RequisitionRepository requisitionRepository;

    @Inject
    private RequisitionMapper requisitionMapper;

    @Inject
    private RequisitionSearchRepository requisitionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRequisitionMockMvc;

    private Requisition requisition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RequisitionResource requisitionResource = new RequisitionResource();
        ReflectionTestUtils.setField(requisitionResource, "requisitionSearchRepository", requisitionSearchRepository);
        ReflectionTestUtils.setField(requisitionResource, "requisitionRepository", requisitionRepository);
        ReflectionTestUtils.setField(requisitionResource, "requisitionMapper", requisitionMapper);
        this.restRequisitionMockMvc = MockMvcBuilders.standaloneSetup(requisitionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Requisition createEntity(EntityManager em) {
        Requisition requisition = new Requisition()
                .reqNumber(DEFAULT_REQ_NUMBER)
                .poNumber(DEFAULT_PO_NUMBER)
                .reqDate(DEFAULT_REQ_DATE)
                .poDate(DEFAULT_PO_DATE)
                .shipAddress(DEFAULT_SHIP_ADDRESS);
        return requisition;
    }

    @Before
    public void initTest() {
        requisitionSearchRepository.deleteAll();
        requisition = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequisition() throws Exception {
        int databaseSizeBeforeCreate = requisitionRepository.findAll().size();

        // Create the Requisition
        RequisitionDTO requisitionDTO = requisitionMapper.requisitionToRequisitionDTO(requisition);

        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionDTO)))
            .andExpect(status().isCreated());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeCreate + 1);
        Requisition testRequisition = requisitionList.get(requisitionList.size() - 1);
        assertThat(testRequisition.getReqNumber()).isEqualTo(DEFAULT_REQ_NUMBER);
        assertThat(testRequisition.getPoNumber()).isEqualTo(DEFAULT_PO_NUMBER);
        assertThat(testRequisition.getReqDate()).isEqualTo(DEFAULT_REQ_DATE);
        assertThat(testRequisition.getPoDate()).isEqualTo(DEFAULT_PO_DATE);
        assertThat(testRequisition.getShipAddress()).isEqualTo(DEFAULT_SHIP_ADDRESS);

        // Validate the Requisition in ElasticSearch
        Requisition requisitionEs = requisitionSearchRepository.findOne(testRequisition.getId());
        assertThat(requisitionEs).isEqualToComparingFieldByField(testRequisition);
    }

    @Test
    @Transactional
    public void createRequisitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requisitionRepository.findAll().size();

        // Create the Requisition with an existing ID
        Requisition existingRequisition = new Requisition();
        existingRequisition.setId(1L);
        RequisitionDTO existingRequisitionDTO = requisitionMapper.requisitionToRequisitionDTO(existingRequisition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequisitionMockMvc.perform(post("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRequisitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRequisitions() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get all the requisitionList
        restRequisitionMockMvc.perform(get("/api/requisitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].reqNumber").value(hasItem(DEFAULT_REQ_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].poNumber").value(hasItem(DEFAULT_PO_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].reqDate").value(hasItem(DEFAULT_REQ_DATE.intValue())))
            .andExpect(jsonPath("$.[*].poDate").value(hasItem(DEFAULT_PO_DATE.intValue())))
            .andExpect(jsonPath("$.[*].shipAddress").value(hasItem(DEFAULT_SHIP_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);

        // Get the requisition
        restRequisitionMockMvc.perform(get("/api/requisitions/{id}", requisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(requisition.getId().intValue()))
            .andExpect(jsonPath("$.reqNumber").value(DEFAULT_REQ_NUMBER.intValue()))
            .andExpect(jsonPath("$.poNumber").value(DEFAULT_PO_NUMBER.toString()))
            .andExpect(jsonPath("$.reqDate").value(DEFAULT_REQ_DATE.intValue()))
            .andExpect(jsonPath("$.poDate").value(DEFAULT_PO_DATE.intValue()))
            .andExpect(jsonPath("$.shipAddress").value(DEFAULT_SHIP_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRequisition() throws Exception {
        // Get the requisition
        restRequisitionMockMvc.perform(get("/api/requisitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);
        requisitionSearchRepository.save(requisition);
        int databaseSizeBeforeUpdate = requisitionRepository.findAll().size();

        // Update the requisition
        Requisition updatedRequisition = requisitionRepository.findOne(requisition.getId());
        updatedRequisition
                .reqNumber(UPDATED_REQ_NUMBER)
                .poNumber(UPDATED_PO_NUMBER)
                .reqDate(UPDATED_REQ_DATE)
                .poDate(UPDATED_PO_DATE)
                .shipAddress(UPDATED_SHIP_ADDRESS);
        RequisitionDTO requisitionDTO = requisitionMapper.requisitionToRequisitionDTO(updatedRequisition);

        restRequisitionMockMvc.perform(put("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionDTO)))
            .andExpect(status().isOk());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeUpdate);
        Requisition testRequisition = requisitionList.get(requisitionList.size() - 1);
        assertThat(testRequisition.getReqNumber()).isEqualTo(UPDATED_REQ_NUMBER);
        assertThat(testRequisition.getPoNumber()).isEqualTo(UPDATED_PO_NUMBER);
        assertThat(testRequisition.getReqDate()).isEqualTo(UPDATED_REQ_DATE);
        assertThat(testRequisition.getPoDate()).isEqualTo(UPDATED_PO_DATE);
        assertThat(testRequisition.getShipAddress()).isEqualTo(UPDATED_SHIP_ADDRESS);

        // Validate the Requisition in ElasticSearch
        Requisition requisitionEs = requisitionSearchRepository.findOne(testRequisition.getId());
        assertThat(requisitionEs).isEqualToComparingFieldByField(testRequisition);
    }

    @Test
    @Transactional
    public void updateNonExistingRequisition() throws Exception {
        int databaseSizeBeforeUpdate = requisitionRepository.findAll().size();

        // Create the Requisition
        RequisitionDTO requisitionDTO = requisitionMapper.requisitionToRequisitionDTO(requisition);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequisitionMockMvc.perform(put("/api/requisitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requisitionDTO)))
            .andExpect(status().isCreated());

        // Validate the Requisition in the database
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);
        requisitionSearchRepository.save(requisition);
        int databaseSizeBeforeDelete = requisitionRepository.findAll().size();

        // Get the requisition
        restRequisitionMockMvc.perform(delete("/api/requisitions/{id}", requisition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean requisitionExistsInEs = requisitionSearchRepository.exists(requisition.getId());
        assertThat(requisitionExistsInEs).isFalse();

        // Validate the database is empty
        List<Requisition> requisitionList = requisitionRepository.findAll();
        assertThat(requisitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRequisition() throws Exception {
        // Initialize the database
        requisitionRepository.saveAndFlush(requisition);
        requisitionSearchRepository.save(requisition);

        // Search the requisition
        restRequisitionMockMvc.perform(get("/api/_search/requisitions?query=id:" + requisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].reqNumber").value(hasItem(DEFAULT_REQ_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].poNumber").value(hasItem(DEFAULT_PO_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].reqDate").value(hasItem(DEFAULT_REQ_DATE.intValue())))
            .andExpect(jsonPath("$.[*].poDate").value(hasItem(DEFAULT_PO_DATE.intValue())))
            .andExpect(jsonPath("$.[*].shipAddress").value(hasItem(DEFAULT_SHIP_ADDRESS.toString())));
    }
}
