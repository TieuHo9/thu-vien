package com.thuphi.vn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thuphi.vn.IntegrationTest;
import com.thuphi.vn.domain.CapBac;
import com.thuphi.vn.repository.CapBacRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CapBacResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CapBacResourceIT {

    private static final String DEFAULT_TEN_CAP = "AAAAAAAAAA";
    private static final String UPDATED_TEN_CAP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cap-bacs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CapBacRepository capBacRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCapBacMockMvc;

    private CapBac capBac;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CapBac createEntity(EntityManager em) {
        CapBac capBac = new CapBac().tenCap(DEFAULT_TEN_CAP);
        return capBac;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CapBac createUpdatedEntity(EntityManager em) {
        CapBac capBac = new CapBac().tenCap(UPDATED_TEN_CAP);
        return capBac;
    }

    @BeforeEach
    public void initTest() {
        capBac = createEntity(em);
    }

    @Test
    @Transactional
    void createCapBac() throws Exception {
        int databaseSizeBeforeCreate = capBacRepository.findAll().size();
        // Create the CapBac
        restCapBacMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(capBac)))
            .andExpect(status().isCreated());

        // Validate the CapBac in the database
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeCreate + 1);
        CapBac testCapBac = capBacList.get(capBacList.size() - 1);
        assertThat(testCapBac.getTenCap()).isEqualTo(DEFAULT_TEN_CAP);
    }

    @Test
    @Transactional
    void createCapBacWithExistingId() throws Exception {
        // Create the CapBac with an existing ID
        capBac.setId(1L);

        int databaseSizeBeforeCreate = capBacRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapBacMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(capBac)))
            .andExpect(status().isBadRequest());

        // Validate the CapBac in the database
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCapBacs() throws Exception {
        // Initialize the database
        capBacRepository.saveAndFlush(capBac);

        // Get all the capBacList
        restCapBacMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capBac.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenCap").value(hasItem(DEFAULT_TEN_CAP)));
    }

    @Test
    @Transactional
    void getCapBac() throws Exception {
        // Initialize the database
        capBacRepository.saveAndFlush(capBac);

        // Get the capBac
        restCapBacMockMvc
            .perform(get(ENTITY_API_URL_ID, capBac.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(capBac.getId().intValue()))
            .andExpect(jsonPath("$.tenCap").value(DEFAULT_TEN_CAP));
    }

    @Test
    @Transactional
    void getNonExistingCapBac() throws Exception {
        // Get the capBac
        restCapBacMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCapBac() throws Exception {
        // Initialize the database
        capBacRepository.saveAndFlush(capBac);

        int databaseSizeBeforeUpdate = capBacRepository.findAll().size();

        // Update the capBac
        CapBac updatedCapBac = capBacRepository.findById(capBac.getId()).get();
        // Disconnect from session so that the updates on updatedCapBac are not directly saved in db
        em.detach(updatedCapBac);
        updatedCapBac.tenCap(UPDATED_TEN_CAP);

        restCapBacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCapBac.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCapBac))
            )
            .andExpect(status().isOk());

        // Validate the CapBac in the database
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeUpdate);
        CapBac testCapBac = capBacList.get(capBacList.size() - 1);
        assertThat(testCapBac.getTenCap()).isEqualTo(UPDATED_TEN_CAP);
    }

    @Test
    @Transactional
    void putNonExistingCapBac() throws Exception {
        int databaseSizeBeforeUpdate = capBacRepository.findAll().size();
        capBac.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapBacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, capBac.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(capBac))
            )
            .andExpect(status().isBadRequest());

        // Validate the CapBac in the database
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCapBac() throws Exception {
        int databaseSizeBeforeUpdate = capBacRepository.findAll().size();
        capBac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapBacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(capBac))
            )
            .andExpect(status().isBadRequest());

        // Validate the CapBac in the database
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCapBac() throws Exception {
        int databaseSizeBeforeUpdate = capBacRepository.findAll().size();
        capBac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapBacMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(capBac)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CapBac in the database
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCapBacWithPatch() throws Exception {
        // Initialize the database
        capBacRepository.saveAndFlush(capBac);

        int databaseSizeBeforeUpdate = capBacRepository.findAll().size();

        // Update the capBac using partial update
        CapBac partialUpdatedCapBac = new CapBac();
        partialUpdatedCapBac.setId(capBac.getId());

        partialUpdatedCapBac.tenCap(UPDATED_TEN_CAP);

        restCapBacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCapBac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCapBac))
            )
            .andExpect(status().isOk());

        // Validate the CapBac in the database
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeUpdate);
        CapBac testCapBac = capBacList.get(capBacList.size() - 1);
        assertThat(testCapBac.getTenCap()).isEqualTo(UPDATED_TEN_CAP);
    }

    @Test
    @Transactional
    void fullUpdateCapBacWithPatch() throws Exception {
        // Initialize the database
        capBacRepository.saveAndFlush(capBac);

        int databaseSizeBeforeUpdate = capBacRepository.findAll().size();

        // Update the capBac using partial update
        CapBac partialUpdatedCapBac = new CapBac();
        partialUpdatedCapBac.setId(capBac.getId());

        partialUpdatedCapBac.tenCap(UPDATED_TEN_CAP);

        restCapBacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCapBac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCapBac))
            )
            .andExpect(status().isOk());

        // Validate the CapBac in the database
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeUpdate);
        CapBac testCapBac = capBacList.get(capBacList.size() - 1);
        assertThat(testCapBac.getTenCap()).isEqualTo(UPDATED_TEN_CAP);
    }

    @Test
    @Transactional
    void patchNonExistingCapBac() throws Exception {
        int databaseSizeBeforeUpdate = capBacRepository.findAll().size();
        capBac.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapBacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, capBac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(capBac))
            )
            .andExpect(status().isBadRequest());

        // Validate the CapBac in the database
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCapBac() throws Exception {
        int databaseSizeBeforeUpdate = capBacRepository.findAll().size();
        capBac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapBacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(capBac))
            )
            .andExpect(status().isBadRequest());

        // Validate the CapBac in the database
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCapBac() throws Exception {
        int databaseSizeBeforeUpdate = capBacRepository.findAll().size();
        capBac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapBacMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(capBac)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CapBac in the database
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCapBac() throws Exception {
        // Initialize the database
        capBacRepository.saveAndFlush(capBac);

        int databaseSizeBeforeDelete = capBacRepository.findAll().size();

        // Delete the capBac
        restCapBacMockMvc
            .perform(delete(ENTITY_API_URL_ID, capBac.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CapBac> capBacList = capBacRepository.findAll();
        assertThat(capBacList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
