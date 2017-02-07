package com.softage.epurchase.web.rest;

import com.softage.epurchase.EpurchaseApp;

import com.softage.epurchase.domain.Department;
import com.softage.epurchase.repository.DepartmentRepository;
import com.softage.epurchase.repository.search.DepartmentSearchRepository;
import com.softage.epurchase.service.dto.DepartmentDTO;
import com.softage.epurchase.service.mapper.DepartmentMapper;

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
 * Test class for the DepartmentResource REST controller.
 *
 * @see DepartmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpurchaseApp.class)
public class DepartmentResourceIntTest {

    private static final Long DEFAULT_DEPART_ID = 1L;
    private static final Long UPDATED_DEPART_ID = 2L;

    private static final String DEFAULT_DEPART_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPART_NAME = "BBBBBBBBBB";

    @Inject
    private DepartmentRepository departmentRepository;

    @Inject
    private DepartmentMapper departmentMapper;

    @Inject
    private DepartmentSearchRepository departmentSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDepartmentMockMvc;

    private Department department;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DepartmentResource departmentResource = new DepartmentResource();
        ReflectionTestUtils.setField(departmentResource, "departmentSearchRepository", departmentSearchRepository);
        ReflectionTestUtils.setField(departmentResource, "departmentRepository", departmentRepository);
        ReflectionTestUtils.setField(departmentResource, "departmentMapper", departmentMapper);
        this.restDepartmentMockMvc = MockMvcBuilders.standaloneSetup(departmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Department createEntity(EntityManager em) {
        Department department = new Department()
                .departId(DEFAULT_DEPART_ID)
                .departName(DEFAULT_DEPART_NAME);
        return department;
    }

    @Before
    public void initTest() {
        departmentSearchRepository.deleteAll();
        department = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartment() throws Exception {
        int databaseSizeBeforeCreate = departmentRepository.findAll().size();

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.departmentToDepartmentDTO(department);

        restDepartmentMockMvc.perform(post("/api/departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeCreate + 1);
        Department testDepartment = departmentList.get(departmentList.size() - 1);
        assertThat(testDepartment.getDepartId()).isEqualTo(DEFAULT_DEPART_ID);
        assertThat(testDepartment.getDepartName()).isEqualTo(DEFAULT_DEPART_NAME);

        // Validate the Department in ElasticSearch
        Department departmentEs = departmentSearchRepository.findOne(testDepartment.getId());
        assertThat(departmentEs).isEqualToComparingFieldByField(testDepartment);
    }

    @Test
    @Transactional
    public void createDepartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departmentRepository.findAll().size();

        // Create the Department with an existing ID
        Department existingDepartment = new Department();
        existingDepartment.setId(1L);
        DepartmentDTO existingDepartmentDTO = departmentMapper.departmentToDepartmentDTO(existingDepartment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentMockMvc.perform(post("/api/departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDepartmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDepartments() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList
        restDepartmentMockMvc.perform(get("/api/departments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(department.getId().intValue())))
            .andExpect(jsonPath("$.[*].departId").value(hasItem(DEFAULT_DEPART_ID.intValue())))
            .andExpect(jsonPath("$.[*].departName").value(hasItem(DEFAULT_DEPART_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get the department
        restDepartmentMockMvc.perform(get("/api/departments/{id}", department.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(department.getId().intValue()))
            .andExpect(jsonPath("$.departId").value(DEFAULT_DEPART_ID.intValue()))
            .andExpect(jsonPath("$.departName").value(DEFAULT_DEPART_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepartment() throws Exception {
        // Get the department
        restDepartmentMockMvc.perform(get("/api/departments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);
        departmentSearchRepository.save(department);
        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();

        // Update the department
        Department updatedDepartment = departmentRepository.findOne(department.getId());
        updatedDepartment
                .departId(UPDATED_DEPART_ID)
                .departName(UPDATED_DEPART_NAME);
        DepartmentDTO departmentDTO = departmentMapper.departmentToDepartmentDTO(updatedDepartment);

        restDepartmentMockMvc.perform(put("/api/departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isOk());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
        Department testDepartment = departmentList.get(departmentList.size() - 1);
        assertThat(testDepartment.getDepartId()).isEqualTo(UPDATED_DEPART_ID);
        assertThat(testDepartment.getDepartName()).isEqualTo(UPDATED_DEPART_NAME);

        // Validate the Department in ElasticSearch
        Department departmentEs = departmentSearchRepository.findOne(testDepartment.getId());
        assertThat(departmentEs).isEqualToComparingFieldByField(testDepartment);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartment() throws Exception {
        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.departmentToDepartmentDTO(department);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDepartmentMockMvc.perform(put("/api/departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);
        departmentSearchRepository.save(department);
        int databaseSizeBeforeDelete = departmentRepository.findAll().size();

        // Get the department
        restDepartmentMockMvc.perform(delete("/api/departments/{id}", department.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean departmentExistsInEs = departmentSearchRepository.exists(department.getId());
        assertThat(departmentExistsInEs).isFalse();

        // Validate the database is empty
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);
        departmentSearchRepository.save(department);

        // Search the department
        restDepartmentMockMvc.perform(get("/api/_search/departments?query=id:" + department.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(department.getId().intValue())))
            .andExpect(jsonPath("$.[*].departId").value(hasItem(DEFAULT_DEPART_ID.intValue())))
            .andExpect(jsonPath("$.[*].departName").value(hasItem(DEFAULT_DEPART_NAME.toString())));
    }
}
