package com.thuphi.vn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thuphi.vn.IntegrationTest;
import com.thuphi.vn.domain.PhongBan;
import com.thuphi.vn.repository.PhongBanRepository;
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
 * Integration tests for the {@link PhongBanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhongBanResourceIT {

    private static final String DEFAULT_MA_PHONG = "AAAAAAAAAA";
    private static final String UPDATED_MA_PHONG = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_PHONG = "AAAAAAAAAA";
    private static final String UPDATED_TEN_PHONG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phong-bans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhongBanRepository phongBanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhongBanMockMvc;

    private PhongBan phongBan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhongBan createEntity(EntityManager em) {
        PhongBan phongBan = new PhongBan().maPhong(DEFAULT_MA_PHONG).tenPhong(DEFAULT_TEN_PHONG);
        return phongBan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhongBan createUpdatedEntity(EntityManager em) {
        PhongBan phongBan = new PhongBan().maPhong(UPDATED_MA_PHONG).tenPhong(UPDATED_TEN_PHONG);
        return phongBan;
    }

    @BeforeEach
    public void initTest() {
        phongBan = createEntity(em);
    }

    @Test
    @Transactional
    void createPhongBan() throws Exception {
        int databaseSizeBeforeCreate = phongBanRepository.findAll().size();
        // Create the PhongBan
        restPhongBanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongBan)))
            .andExpect(status().isCreated());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeCreate + 1);
        PhongBan testPhongBan = phongBanList.get(phongBanList.size() - 1);
        assertThat(testPhongBan.getMaPhong()).isEqualTo(DEFAULT_MA_PHONG);
        assertThat(testPhongBan.getTenPhong()).isEqualTo(DEFAULT_TEN_PHONG);
    }

    @Test
    @Transactional
    void createPhongBanWithExistingId() throws Exception {
        // Create the PhongBan with an existing ID
        phongBan.setId(1L);

        int databaseSizeBeforeCreate = phongBanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhongBanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongBan)))
            .andExpect(status().isBadRequest());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPhongBans() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get all the phongBanList
        restPhongBanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phongBan.getId().intValue())))
            .andExpect(jsonPath("$.[*].maPhong").value(hasItem(DEFAULT_MA_PHONG)))
            .andExpect(jsonPath("$.[*].tenPhong").value(hasItem(DEFAULT_TEN_PHONG)));
    }

    @Test
    @Transactional
    void getPhongBan() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        // Get the phongBan
        restPhongBanMockMvc
            .perform(get(ENTITY_API_URL_ID, phongBan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phongBan.getId().intValue()))
            .andExpect(jsonPath("$.maPhong").value(DEFAULT_MA_PHONG))
            .andExpect(jsonPath("$.tenPhong").value(DEFAULT_TEN_PHONG));
    }

    @Test
    @Transactional
    void getNonExistingPhongBan() throws Exception {
        // Get the phongBan
        restPhongBanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPhongBan() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();

        // Update the phongBan
        PhongBan updatedPhongBan = phongBanRepository.findById(phongBan.getId()).get();
        // Disconnect from session so that the updates on updatedPhongBan are not directly saved in db
        em.detach(updatedPhongBan);
        updatedPhongBan.maPhong(UPDATED_MA_PHONG).tenPhong(UPDATED_TEN_PHONG);

        restPhongBanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPhongBan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPhongBan))
            )
            .andExpect(status().isOk());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
        PhongBan testPhongBan = phongBanList.get(phongBanList.size() - 1);
        assertThat(testPhongBan.getMaPhong()).isEqualTo(UPDATED_MA_PHONG);
        assertThat(testPhongBan.getTenPhong()).isEqualTo(UPDATED_TEN_PHONG);
    }

    @Test
    @Transactional
    void putNonExistingPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phongBan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongBan))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongBan))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongBan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhongBanWithPatch() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();

        // Update the phongBan using partial update
        PhongBan partialUpdatedPhongBan = new PhongBan();
        partialUpdatedPhongBan.setId(phongBan.getId());

        partialUpdatedPhongBan.maPhong(UPDATED_MA_PHONG);

        restPhongBanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongBan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongBan))
            )
            .andExpect(status().isOk());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
        PhongBan testPhongBan = phongBanList.get(phongBanList.size() - 1);
        assertThat(testPhongBan.getMaPhong()).isEqualTo(UPDATED_MA_PHONG);
        assertThat(testPhongBan.getTenPhong()).isEqualTo(DEFAULT_TEN_PHONG);
    }

    @Test
    @Transactional
    void fullUpdatePhongBanWithPatch() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();

        // Update the phongBan using partial update
        PhongBan partialUpdatedPhongBan = new PhongBan();
        partialUpdatedPhongBan.setId(phongBan.getId());

        partialUpdatedPhongBan.maPhong(UPDATED_MA_PHONG).tenPhong(UPDATED_TEN_PHONG);

        restPhongBanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongBan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongBan))
            )
            .andExpect(status().isOk());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
        PhongBan testPhongBan = phongBanList.get(phongBanList.size() - 1);
        assertThat(testPhongBan.getMaPhong()).isEqualTo(UPDATED_MA_PHONG);
        assertThat(testPhongBan.getTenPhong()).isEqualTo(UPDATED_TEN_PHONG);
    }

    @Test
    @Transactional
    void patchNonExistingPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phongBan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongBan))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongBan))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhongBan() throws Exception {
        int databaseSizeBeforeUpdate = phongBanRepository.findAll().size();
        phongBan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(phongBan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhongBan in the database
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhongBan() throws Exception {
        // Initialize the database
        phongBanRepository.saveAndFlush(phongBan);

        int databaseSizeBeforeDelete = phongBanRepository.findAll().size();

        // Delete the phongBan
        restPhongBanMockMvc
            .perform(delete(ENTITY_API_URL_ID, phongBan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhongBan> phongBanList = phongBanRepository.findAll();
        assertThat(phongBanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
