package com.niche.jhipster.web.rest;

import com.niche.jhipster.HellopghApp;

import com.niche.jhipster.domain.Zonal;
import com.niche.jhipster.repository.ZonalRepository;
import com.niche.jhipster.service.ZonalService;
import com.niche.jhipster.service.dto.ZonalDTO;
import com.niche.jhipster.service.mapper.ZonalMapper;
import com.niche.jhipster.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.niche.jhipster.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ZonalResource REST controller.
 *
 * @see ZonalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HellopghApp.class)
public class ZonalResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ZonalRepository zonalRepository;


    @Autowired
    private ZonalMapper zonalMapper;
    

    @Autowired
    private ZonalService zonalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restZonalMockMvc;

    private Zonal zonal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZonalResource zonalResource = new ZonalResource(zonalService);
        this.restZonalMockMvc = MockMvcBuilders.standaloneSetup(zonalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zonal createEntity(EntityManager em) {
        Zonal zonal = new Zonal()
            .name(DEFAULT_NAME)
            .date(DEFAULT_DATE);
        return zonal;
    }

    @Before
    public void initTest() {
        zonal = createEntity(em);
    }

    @Test
    @Transactional
    public void createZonal() throws Exception {
        int databaseSizeBeforeCreate = zonalRepository.findAll().size();

        // Create the Zonal
        ZonalDTO zonalDTO = zonalMapper.toDto(zonal);
        restZonalMockMvc.perform(post("/api/zonals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zonalDTO)))
            .andExpect(status().isCreated());

        // Validate the Zonal in the database
        List<Zonal> zonalList = zonalRepository.findAll();
        assertThat(zonalList).hasSize(databaseSizeBeforeCreate + 1);
        Zonal testZonal = zonalList.get(zonalList.size() - 1);
        assertThat(testZonal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testZonal.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createZonalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zonalRepository.findAll().size();

        // Create the Zonal with an existing ID
        zonal.setId(1L);
        ZonalDTO zonalDTO = zonalMapper.toDto(zonal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZonalMockMvc.perform(post("/api/zonals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zonalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Zonal in the database
        List<Zonal> zonalList = zonalRepository.findAll();
        assertThat(zonalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = zonalRepository.findAll().size();
        // set the field null
        zonal.setName(null);

        // Create the Zonal, which fails.
        ZonalDTO zonalDTO = zonalMapper.toDto(zonal);

        restZonalMockMvc.perform(post("/api/zonals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zonalDTO)))
            .andExpect(status().isBadRequest());

        List<Zonal> zonalList = zonalRepository.findAll();
        assertThat(zonalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZonals() throws Exception {
        // Initialize the database
        zonalRepository.saveAndFlush(zonal);

        // Get all the zonalList
        restZonalMockMvc.perform(get("/api/zonals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zonal.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getZonal() throws Exception {
        // Initialize the database
        zonalRepository.saveAndFlush(zonal);

        // Get the zonal
        restZonalMockMvc.perform(get("/api/zonals/{id}", zonal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zonal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingZonal() throws Exception {
        // Get the zonal
        restZonalMockMvc.perform(get("/api/zonals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZonal() throws Exception {
        // Initialize the database
        zonalRepository.saveAndFlush(zonal);

        int databaseSizeBeforeUpdate = zonalRepository.findAll().size();

        // Update the zonal
        Zonal updatedZonal = zonalRepository.findById(zonal.getId()).get();
        // Disconnect from session so that the updates on updatedZonal are not directly saved in db
        em.detach(updatedZonal);
        updatedZonal
            .name(UPDATED_NAME)
            .date(UPDATED_DATE);
        ZonalDTO zonalDTO = zonalMapper.toDto(updatedZonal);

        restZonalMockMvc.perform(put("/api/zonals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zonalDTO)))
            .andExpect(status().isOk());

        // Validate the Zonal in the database
        List<Zonal> zonalList = zonalRepository.findAll();
        assertThat(zonalList).hasSize(databaseSizeBeforeUpdate);
        Zonal testZonal = zonalList.get(zonalList.size() - 1);
        assertThat(testZonal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testZonal.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingZonal() throws Exception {
        int databaseSizeBeforeUpdate = zonalRepository.findAll().size();

        // Create the Zonal
        ZonalDTO zonalDTO = zonalMapper.toDto(zonal);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restZonalMockMvc.perform(put("/api/zonals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zonalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Zonal in the database
        List<Zonal> zonalList = zonalRepository.findAll();
        assertThat(zonalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZonal() throws Exception {
        // Initialize the database
        zonalRepository.saveAndFlush(zonal);

        int databaseSizeBeforeDelete = zonalRepository.findAll().size();

        // Get the zonal
        restZonalMockMvc.perform(delete("/api/zonals/{id}", zonal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Zonal> zonalList = zonalRepository.findAll();
        assertThat(zonalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zonal.class);
        Zonal zonal1 = new Zonal();
        zonal1.setId(1L);
        Zonal zonal2 = new Zonal();
        zonal2.setId(zonal1.getId());
        assertThat(zonal1).isEqualTo(zonal2);
        zonal2.setId(2L);
        assertThat(zonal1).isNotEqualTo(zonal2);
        zonal1.setId(null);
        assertThat(zonal1).isNotEqualTo(zonal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZonalDTO.class);
        ZonalDTO zonalDTO1 = new ZonalDTO();
        zonalDTO1.setId(1L);
        ZonalDTO zonalDTO2 = new ZonalDTO();
        assertThat(zonalDTO1).isNotEqualTo(zonalDTO2);
        zonalDTO2.setId(zonalDTO1.getId());
        assertThat(zonalDTO1).isEqualTo(zonalDTO2);
        zonalDTO2.setId(2L);
        assertThat(zonalDTO1).isNotEqualTo(zonalDTO2);
        zonalDTO1.setId(null);
        assertThat(zonalDTO1).isNotEqualTo(zonalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(zonalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(zonalMapper.fromId(null)).isNull();
    }
}
