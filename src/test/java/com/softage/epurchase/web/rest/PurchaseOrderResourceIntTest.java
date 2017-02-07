package com.softage.epurchase.web.rest;

import com.softage.epurchase.EpurchaseApp;

import com.softage.epurchase.domain.PurchaseOrder;
import com.softage.epurchase.repository.PurchaseOrderRepository;
import com.softage.epurchase.repository.search.PurchaseOrderSearchRepository;
import com.softage.epurchase.service.dto.PurchaseOrderDTO;
import com.softage.epurchase.service.mapper.PurchaseOrderMapper;

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
 * Test class for the PurchaseOrderResource REST controller.
 *
 * @see PurchaseOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpurchaseApp.class)
public class PurchaseOrderResourceIntTest {

    private static final String DEFAULT_PO_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PO_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_PO_DATE = 1L;
    private static final Long UPDATED_PO_DATE = 2L;

    private static final String DEFAULT_DEV_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DEV_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_BILL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BILL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PARTICULARS = "AAAAAAAAAA";
    private static final String UPDATED_PARTICULARS = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final Long DEFAULT_UNIT_PRICE = 1L;
    private static final Long UPDATED_UNIT_PRICE = 2L;

    private static final Long DEFAULT_TOTAL_PRICE = 1L;
    private static final Long UPDATED_TOTAL_PRICE = 2L;

    private static final Long DEFAULT_ESIC_DEDUCTION = 1L;
    private static final Long UPDATED_ESIC_DEDUCTION = 2L;

    private static final Long DEFAULT_GRAND_TOTAL = 1L;
    private static final Long UPDATED_GRAND_TOTAL = 2L;

    @Inject
    private PurchaseOrderRepository purchaseOrderRepository;

    @Inject
    private PurchaseOrderMapper purchaseOrderMapper;

    @Inject
    private PurchaseOrderSearchRepository purchaseOrderSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPurchaseOrderMockMvc;

    private PurchaseOrder purchaseOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PurchaseOrderResource purchaseOrderResource = new PurchaseOrderResource();
        ReflectionTestUtils.setField(purchaseOrderResource, "purchaseOrderSearchRepository", purchaseOrderSearchRepository);
        ReflectionTestUtils.setField(purchaseOrderResource, "purchaseOrderRepository", purchaseOrderRepository);
        ReflectionTestUtils.setField(purchaseOrderResource, "purchaseOrderMapper", purchaseOrderMapper);
        this.restPurchaseOrderMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseOrder createEntity(EntityManager em) {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
                .poNumber(DEFAULT_PO_NUMBER)
                .poDate(DEFAULT_PO_DATE)
                .devAddress(DEFAULT_DEV_ADDRESS)
                .billAddress(DEFAULT_BILL_ADDRESS)
                .vendorAddress(DEFAULT_VENDOR_ADDRESS)
                .particulars(DEFAULT_PARTICULARS)
                .quantity(DEFAULT_QUANTITY)
                .unitPrice(DEFAULT_UNIT_PRICE)
                .totalPrice(DEFAULT_TOTAL_PRICE)
                .esicDeduction(DEFAULT_ESIC_DEDUCTION)
                .grandTotal(DEFAULT_GRAND_TOTAL);
        return purchaseOrder;
    }

    @Before
    public void initTest() {
        purchaseOrderSearchRepository.deleteAll();
        purchaseOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrder() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(purchaseOrder);

        restPurchaseOrderMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getPoNumber()).isEqualTo(DEFAULT_PO_NUMBER);
        assertThat(testPurchaseOrder.getPoDate()).isEqualTo(DEFAULT_PO_DATE);
        assertThat(testPurchaseOrder.getDevAddress()).isEqualTo(DEFAULT_DEV_ADDRESS);
        assertThat(testPurchaseOrder.getBillAddress()).isEqualTo(DEFAULT_BILL_ADDRESS);
        assertThat(testPurchaseOrder.getVendorAddress()).isEqualTo(DEFAULT_VENDOR_ADDRESS);
        assertThat(testPurchaseOrder.getParticulars()).isEqualTo(DEFAULT_PARTICULARS);
        assertThat(testPurchaseOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPurchaseOrder.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testPurchaseOrder.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testPurchaseOrder.getEsicDeduction()).isEqualTo(DEFAULT_ESIC_DEDUCTION);
        assertThat(testPurchaseOrder.getGrandTotal()).isEqualTo(DEFAULT_GRAND_TOTAL);

        // Validate the PurchaseOrder in ElasticSearch
        PurchaseOrder purchaseOrderEs = purchaseOrderSearchRepository.findOne(testPurchaseOrder.getId());
        assertThat(purchaseOrderEs).isEqualToComparingFieldByField(testPurchaseOrder);
    }

    @Test
    @Transactional
    public void createPurchaseOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder with an existing ID
        PurchaseOrder existingPurchaseOrder = new PurchaseOrder();
        existingPurchaseOrder.setId(1L);
        PurchaseOrderDTO existingPurchaseOrderDTO = purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(existingPurchaseOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrderMockMvc.perform(post("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPurchaseOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].poNumber").value(hasItem(DEFAULT_PO_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].poDate").value(hasItem(DEFAULT_PO_DATE.intValue())))
            .andExpect(jsonPath("$.[*].devAddress").value(hasItem(DEFAULT_DEV_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].billAddress").value(hasItem(DEFAULT_BILL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].vendorAddress").value(hasItem(DEFAULT_VENDOR_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].esicDeduction").value(hasItem(DEFAULT_ESIC_DEDUCTION.intValue())))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(DEFAULT_GRAND_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getPurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/{id}", purchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrder.getId().intValue()))
            .andExpect(jsonPath("$.poNumber").value(DEFAULT_PO_NUMBER.toString()))
            .andExpect(jsonPath("$.poDate").value(DEFAULT_PO_DATE.intValue()))
            .andExpect(jsonPath("$.devAddress").value(DEFAULT_DEV_ADDRESS.toString()))
            .andExpect(jsonPath("$.billAddress").value(DEFAULT_BILL_ADDRESS.toString()))
            .andExpect(jsonPath("$.vendorAddress").value(DEFAULT_VENDOR_ADDRESS.toString()))
            .andExpect(jsonPath("$.particulars").value(DEFAULT_PARTICULARS.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.esicDeduction").value(DEFAULT_ESIC_DEDUCTION.intValue()))
            .andExpect(jsonPath("$.grandTotal").value(DEFAULT_GRAND_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseOrder() throws Exception {
        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        purchaseOrderSearchRepository.save(purchaseOrder);
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Update the purchaseOrder
        PurchaseOrder updatedPurchaseOrder = purchaseOrderRepository.findOne(purchaseOrder.getId());
        updatedPurchaseOrder
                .poNumber(UPDATED_PO_NUMBER)
                .poDate(UPDATED_PO_DATE)
                .devAddress(UPDATED_DEV_ADDRESS)
                .billAddress(UPDATED_BILL_ADDRESS)
                .vendorAddress(UPDATED_VENDOR_ADDRESS)
                .particulars(UPDATED_PARTICULARS)
                .quantity(UPDATED_QUANTITY)
                .unitPrice(UPDATED_UNIT_PRICE)
                .totalPrice(UPDATED_TOTAL_PRICE)
                .esicDeduction(UPDATED_ESIC_DEDUCTION)
                .grandTotal(UPDATED_GRAND_TOTAL);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(updatedPurchaseOrder);

        restPurchaseOrderMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getPoNumber()).isEqualTo(UPDATED_PO_NUMBER);
        assertThat(testPurchaseOrder.getPoDate()).isEqualTo(UPDATED_PO_DATE);
        assertThat(testPurchaseOrder.getDevAddress()).isEqualTo(UPDATED_DEV_ADDRESS);
        assertThat(testPurchaseOrder.getBillAddress()).isEqualTo(UPDATED_BILL_ADDRESS);
        assertThat(testPurchaseOrder.getVendorAddress()).isEqualTo(UPDATED_VENDOR_ADDRESS);
        assertThat(testPurchaseOrder.getParticulars()).isEqualTo(UPDATED_PARTICULARS);
        assertThat(testPurchaseOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPurchaseOrder.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testPurchaseOrder.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testPurchaseOrder.getEsicDeduction()).isEqualTo(UPDATED_ESIC_DEDUCTION);
        assertThat(testPurchaseOrder.getGrandTotal()).isEqualTo(UPDATED_GRAND_TOTAL);

        // Validate the PurchaseOrder in ElasticSearch
        PurchaseOrder purchaseOrderEs = purchaseOrderSearchRepository.findOne(testPurchaseOrder.getId());
        assertThat(purchaseOrderEs).isEqualToComparingFieldByField(testPurchaseOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.purchaseOrderToPurchaseOrderDTO(purchaseOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPurchaseOrderMockMvc.perform(put("/api/purchase-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        purchaseOrderSearchRepository.save(purchaseOrder);
        int databaseSizeBeforeDelete = purchaseOrderRepository.findAll().size();

        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(delete("/api/purchase-orders/{id}", purchaseOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean purchaseOrderExistsInEs = purchaseOrderSearchRepository.exists(purchaseOrder.getId());
        assertThat(purchaseOrderExistsInEs).isFalse();

        // Validate the database is empty
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        purchaseOrderSearchRepository.save(purchaseOrder);

        // Search the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/_search/purchase-orders?query=id:" + purchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].poNumber").value(hasItem(DEFAULT_PO_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].poDate").value(hasItem(DEFAULT_PO_DATE.intValue())))
            .andExpect(jsonPath("$.[*].devAddress").value(hasItem(DEFAULT_DEV_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].billAddress").value(hasItem(DEFAULT_BILL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].vendorAddress").value(hasItem(DEFAULT_VENDOR_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].esicDeduction").value(hasItem(DEFAULT_ESIC_DEDUCTION.intValue())))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(DEFAULT_GRAND_TOTAL.intValue())));
    }
}
