package com.softage.epurchase.web.rest;

import com.softage.epurchase.EpurchaseApp;

import com.softage.epurchase.domain.Vendor;
import com.softage.epurchase.repository.VendorRepository;
import com.softage.epurchase.repository.search.VendorSearchRepository;
import com.softage.epurchase.service.dto.VendorDTO;
import com.softage.epurchase.service.mapper.VendorMapper;

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
 * Test class for the VendorResource REST controller.
 *
 * @see VendorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpurchaseApp.class)
public class VendorResourceIntTest {

    private static final String DEFAULT_REG_NO = "AAAAAAAAAA";
    private static final String UPDATED_REG_NO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PAN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PAN_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TIN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TIN_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ST_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ST_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EXERCISE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EXERCISE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PF_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PF_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ESIC_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ESIC_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ACCOUNT_NUMBER = 1L;
    private static final Long UPDATED_ACCOUNT_NUMBER = 2L;

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SWIFT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SWIFT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BANK_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VENDORSTATUS = "AAAAAAAAAA";
    private static final String UPDATED_VENDORSTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_VENDOR_TIME_SPAN = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_TIME_SPAN = "BBBBBBBBBB";

    @Inject
    private VendorRepository vendorRepository;

    @Inject
    private VendorMapper vendorMapper;

    @Inject
    private VendorSearchRepository vendorSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restVendorMockMvc;

    private Vendor vendor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VendorResource vendorResource = new VendorResource();
        ReflectionTestUtils.setField(vendorResource, "vendorSearchRepository", vendorSearchRepository);
        ReflectionTestUtils.setField(vendorResource, "vendorRepository", vendorRepository);
        ReflectionTestUtils.setField(vendorResource, "vendorMapper", vendorMapper);
        this.restVendorMockMvc = MockMvcBuilders.standaloneSetup(vendorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vendor createEntity(EntityManager em) {
        Vendor vendor = new Vendor()
                .regNo(DEFAULT_REG_NO)
                .companyName(DEFAULT_COMPANY_NAME)
                .contactPerson(DEFAULT_CONTACT_PERSON)
                .address(DEFAULT_ADDRESS)
                .productType(DEFAULT_PRODUCT_TYPE)
                .email(DEFAULT_EMAIL)
                .panNumber(DEFAULT_PAN_NUMBER)
                .tinNumber(DEFAULT_TIN_NUMBER)
                .stNumber(DEFAULT_ST_NUMBER)
                .exerciseNumber(DEFAULT_EXERCISE_NUMBER)
                .pfNumber(DEFAULT_PF_NUMBER)
                .esicNumber(DEFAULT_ESIC_NUMBER)
                .accountName(DEFAULT_ACCOUNT_NAME)
                .accountNumber(DEFAULT_ACCOUNT_NUMBER)
                .ifscCode(DEFAULT_IFSC_CODE)
                .swiftNumber(DEFAULT_SWIFT_NUMBER)
                .bankBranch(DEFAULT_BANK_BRANCH)
                .vendorType(DEFAULT_VENDOR_TYPE)
                .vendorstatus(DEFAULT_VENDORSTATUS)
                .vendorTimeSpan(DEFAULT_VENDOR_TIME_SPAN);
        return vendor;
    }

    @Before
    public void initTest() {
        vendorSearchRepository.deleteAll();
        vendor = createEntity(em);
    }

    @Test
    @Transactional
    public void createVendor() throws Exception {
        int databaseSizeBeforeCreate = vendorRepository.findAll().size();

        // Create the Vendor
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isCreated());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeCreate + 1);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getRegNo()).isEqualTo(DEFAULT_REG_NO);
        assertThat(testVendor.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testVendor.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testVendor.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testVendor.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testVendor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVendor.getPanNumber()).isEqualTo(DEFAULT_PAN_NUMBER);
        assertThat(testVendor.getTinNumber()).isEqualTo(DEFAULT_TIN_NUMBER);
        assertThat(testVendor.getStNumber()).isEqualTo(DEFAULT_ST_NUMBER);
        assertThat(testVendor.getExerciseNumber()).isEqualTo(DEFAULT_EXERCISE_NUMBER);
        assertThat(testVendor.getPfNumber()).isEqualTo(DEFAULT_PF_NUMBER);
        assertThat(testVendor.getEsicNumber()).isEqualTo(DEFAULT_ESIC_NUMBER);
        assertThat(testVendor.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testVendor.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testVendor.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testVendor.getSwiftNumber()).isEqualTo(DEFAULT_SWIFT_NUMBER);
        assertThat(testVendor.getBankBranch()).isEqualTo(DEFAULT_BANK_BRANCH);
        assertThat(testVendor.getVendorType()).isEqualTo(DEFAULT_VENDOR_TYPE);
        assertThat(testVendor.getVendorstatus()).isEqualTo(DEFAULT_VENDORSTATUS);
        assertThat(testVendor.getVendorTimeSpan()).isEqualTo(DEFAULT_VENDOR_TIME_SPAN);

        // Validate the Vendor in ElasticSearch
        Vendor vendorEs = vendorSearchRepository.findOne(testVendor.getId());
        assertThat(vendorEs).isEqualToComparingFieldByField(testVendor);
    }

    @Test
    @Transactional
    public void createVendorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendorRepository.findAll().size();

        // Create the Vendor with an existing ID
        Vendor existingVendor = new Vendor();
        existingVendor.setId(1L);
        VendorDTO existingVendorDTO = vendorMapper.vendorToVendorDTO(existingVendor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingVendorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVendors() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList
        restVendorMockMvc.perform(get("/api/vendors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].regNo").value(hasItem(DEFAULT_REG_NO.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].panNumber").value(hasItem(DEFAULT_PAN_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tinNumber").value(hasItem(DEFAULT_TIN_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].stNumber").value(hasItem(DEFAULT_ST_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].exerciseNumber").value(hasItem(DEFAULT_EXERCISE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].pfNumber").value(hasItem(DEFAULT_PF_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].esicNumber").value(hasItem(DEFAULT_ESIC_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE.toString())))
            .andExpect(jsonPath("$.[*].swiftNumber").value(hasItem(DEFAULT_SWIFT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].bankBranch").value(hasItem(DEFAULT_BANK_BRANCH.toString())))
            .andExpect(jsonPath("$.[*].vendorType").value(hasItem(DEFAULT_VENDOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vendorstatus").value(hasItem(DEFAULT_VENDORSTATUS.toString())))
            .andExpect(jsonPath("$.[*].vendorTimeSpan").value(hasItem(DEFAULT_VENDOR_TIME_SPAN.toString())));
    }

    @Test
    @Transactional
    public void getVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get the vendor
        restVendorMockMvc.perform(get("/api/vendors/{id}", vendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vendor.getId().intValue()))
            .andExpect(jsonPath("$.regNo").value(DEFAULT_REG_NO.toString()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.panNumber").value(DEFAULT_PAN_NUMBER.toString()))
            .andExpect(jsonPath("$.tinNumber").value(DEFAULT_TIN_NUMBER.toString()))
            .andExpect(jsonPath("$.stNumber").value(DEFAULT_ST_NUMBER.toString()))
            .andExpect(jsonPath("$.exerciseNumber").value(DEFAULT_EXERCISE_NUMBER.toString()))
            .andExpect(jsonPath("$.pfNumber").value(DEFAULT_PF_NUMBER.toString()))
            .andExpect(jsonPath("$.esicNumber").value(DEFAULT_ESIC_NUMBER.toString()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.intValue()))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE.toString()))
            .andExpect(jsonPath("$.swiftNumber").value(DEFAULT_SWIFT_NUMBER.toString()))
            .andExpect(jsonPath("$.bankBranch").value(DEFAULT_BANK_BRANCH.toString()))
            .andExpect(jsonPath("$.vendorType").value(DEFAULT_VENDOR_TYPE.toString()))
            .andExpect(jsonPath("$.vendorstatus").value(DEFAULT_VENDORSTATUS.toString()))
            .andExpect(jsonPath("$.vendorTimeSpan").value(DEFAULT_VENDOR_TIME_SPAN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVendor() throws Exception {
        // Get the vendor
        restVendorMockMvc.perform(get("/api/vendors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);
        vendorSearchRepository.save(vendor);
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Update the vendor
        Vendor updatedVendor = vendorRepository.findOne(vendor.getId());
        updatedVendor
                .regNo(UPDATED_REG_NO)
                .companyName(UPDATED_COMPANY_NAME)
                .contactPerson(UPDATED_CONTACT_PERSON)
                .address(UPDATED_ADDRESS)
                .productType(UPDATED_PRODUCT_TYPE)
                .email(UPDATED_EMAIL)
                .panNumber(UPDATED_PAN_NUMBER)
                .tinNumber(UPDATED_TIN_NUMBER)
                .stNumber(UPDATED_ST_NUMBER)
                .exerciseNumber(UPDATED_EXERCISE_NUMBER)
                .pfNumber(UPDATED_PF_NUMBER)
                .esicNumber(UPDATED_ESIC_NUMBER)
                .accountName(UPDATED_ACCOUNT_NAME)
                .accountNumber(UPDATED_ACCOUNT_NUMBER)
                .ifscCode(UPDATED_IFSC_CODE)
                .swiftNumber(UPDATED_SWIFT_NUMBER)
                .bankBranch(UPDATED_BANK_BRANCH)
                .vendorType(UPDATED_VENDOR_TYPE)
                .vendorstatus(UPDATED_VENDORSTATUS)
                .vendorTimeSpan(UPDATED_VENDOR_TIME_SPAN);
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(updatedVendor);

        restVendorMockMvc.perform(put("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isOk());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getRegNo()).isEqualTo(UPDATED_REG_NO);
        assertThat(testVendor.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testVendor.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testVendor.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testVendor.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testVendor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVendor.getPanNumber()).isEqualTo(UPDATED_PAN_NUMBER);
        assertThat(testVendor.getTinNumber()).isEqualTo(UPDATED_TIN_NUMBER);
        assertThat(testVendor.getStNumber()).isEqualTo(UPDATED_ST_NUMBER);
        assertThat(testVendor.getExerciseNumber()).isEqualTo(UPDATED_EXERCISE_NUMBER);
        assertThat(testVendor.getPfNumber()).isEqualTo(UPDATED_PF_NUMBER);
        assertThat(testVendor.getEsicNumber()).isEqualTo(UPDATED_ESIC_NUMBER);
        assertThat(testVendor.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testVendor.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testVendor.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testVendor.getSwiftNumber()).isEqualTo(UPDATED_SWIFT_NUMBER);
        assertThat(testVendor.getBankBranch()).isEqualTo(UPDATED_BANK_BRANCH);
        assertThat(testVendor.getVendorType()).isEqualTo(UPDATED_VENDOR_TYPE);
        assertThat(testVendor.getVendorstatus()).isEqualTo(UPDATED_VENDORSTATUS);
        assertThat(testVendor.getVendorTimeSpan()).isEqualTo(UPDATED_VENDOR_TIME_SPAN);

        // Validate the Vendor in ElasticSearch
        Vendor vendorEs = vendorSearchRepository.findOne(testVendor.getId());
        assertThat(vendorEs).isEqualToComparingFieldByField(testVendor);
    }

    @Test
    @Transactional
    public void updateNonExistingVendor() throws Exception {
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Create the Vendor
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVendorMockMvc.perform(put("/api/vendors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isCreated());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);
        vendorSearchRepository.save(vendor);
        int databaseSizeBeforeDelete = vendorRepository.findAll().size();

        // Get the vendor
        restVendorMockMvc.perform(delete("/api/vendors/{id}", vendor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean vendorExistsInEs = vendorSearchRepository.exists(vendor.getId());
        assertThat(vendorExistsInEs).isFalse();

        // Validate the database is empty
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);
        vendorSearchRepository.save(vendor);

        // Search the vendor
        restVendorMockMvc.perform(get("/api/_search/vendors?query=id:" + vendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].regNo").value(hasItem(DEFAULT_REG_NO.toString())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].panNumber").value(hasItem(DEFAULT_PAN_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tinNumber").value(hasItem(DEFAULT_TIN_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].stNumber").value(hasItem(DEFAULT_ST_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].exerciseNumber").value(hasItem(DEFAULT_EXERCISE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].pfNumber").value(hasItem(DEFAULT_PF_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].esicNumber").value(hasItem(DEFAULT_ESIC_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE.toString())))
            .andExpect(jsonPath("$.[*].swiftNumber").value(hasItem(DEFAULT_SWIFT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].bankBranch").value(hasItem(DEFAULT_BANK_BRANCH.toString())))
            .andExpect(jsonPath("$.[*].vendorType").value(hasItem(DEFAULT_VENDOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vendorstatus").value(hasItem(DEFAULT_VENDORSTATUS.toString())))
            .andExpect(jsonPath("$.[*].vendorTimeSpan").value(hasItem(DEFAULT_VENDOR_TIME_SPAN.toString())));
    }
}
