package com.thuphi.vn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thuphi.vn.IntegrationTest;
import com.thuphi.vn.domain.ChiPhi;
import com.thuphi.vn.repository.ChiPhiRepository;
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
 * Integration tests for the {@link ChiPhiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChiPhiResourceIT {

    private static final String DEFAULT_LOAI_CHI_PHI = "AAAAAAAAAA";
    private static final String UPDATED_LOAI_CHI_PHI = "BBBBBBBBBB";

    private static final String DEFAULT_SO_TIEN = "AAAAAAAAAA";
    private static final String UPDATED_SO_TIEN = "BBBBBBBBBB";

    private static final String DEFAULT_DON_VI_TIEN_TE = "AAAAAAAAAA";
    private static final String UPDATED_DON_VI_TIEN_TE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/chi-phis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChiPhiRepository chiPhiRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChiPhiMockMvc;

    private ChiPhi chiPhi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiPhi createEntity(EntityManager em) {
        ChiPhi chiPhi = new ChiPhi().loaiChiPhi(DEFAULT_LOAI_CHI_PHI).soTien(DEFAULT_SO_TIEN).donViTienTe(DEFAULT_DON_VI_TIEN_TE);
        return chiPhi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChiPhi createUpdatedEntity(EntityManager em) {
        ChiPhi chiPhi = new ChiPhi().loaiChiPhi(UPDATED_LOAI_CHI_PHI).soTien(UPDATED_SO_TIEN).donViTienTe(UPDATED_DON_VI_TIEN_TE);
        return chiPhi;
    }

    @BeforeEach
    public void initTest() {
        chiPhi = createEntity(em);
    }

    @Test
    @Transactional
    void createChiPhi() throws Exception {
        int databaseSizeBeforeCreate = chiPhiRepository.findAll().size();
        // Create the ChiPhi
        restChiPhiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiPhi)))
            .andExpect(status().isCreated());

        // Validate the ChiPhi in the database
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeCreate + 1);
        ChiPhi testChiPhi = chiPhiList.get(chiPhiList.size() - 1);
        assertThat(testChiPhi.getLoaiChiPhi()).isEqualTo(DEFAULT_LOAI_CHI_PHI);
        assertThat(testChiPhi.getSoTien()).isEqualTo(DEFAULT_SO_TIEN);
        assertThat(testChiPhi.getDonViTienTe()).isEqualTo(DEFAULT_DON_VI_TIEN_TE);
    }

    @Test
    @Transactional
    void createChiPhiWithExistingId() throws Exception {
        // Create the ChiPhi with an existing ID
        chiPhi.setId(1L);

        int databaseSizeBeforeCreate = chiPhiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChiPhiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiPhi)))
            .andExpect(status().isBadRequest());

        // Validate the ChiPhi in the database
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChiPhis() throws Exception {
        // Initialize the database
        chiPhiRepository.saveAndFlush(chiPhi);

        // Get all the chiPhiList
        restChiPhiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chiPhi.getId().intValue())))
            .andExpect(jsonPath("$.[*].loaiChiPhi").value(hasItem(DEFAULT_LOAI_CHI_PHI)))
            .andExpect(jsonPath("$.[*].soTien").value(hasItem(DEFAULT_SO_TIEN)))
            .andExpect(jsonPath("$.[*].donViTienTe").value(hasItem(DEFAULT_DON_VI_TIEN_TE)));
    }

    @Test
    @Transactional
    void getChiPhi() throws Exception {
        // Initialize the database
        chiPhiRepository.saveAndFlush(chiPhi);

        // Get the chiPhi
        restChiPhiMockMvc
            .perform(get(ENTITY_API_URL_ID, chiPhi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chiPhi.getId().intValue()))
            .andExpect(jsonPath("$.loaiChiPhi").value(DEFAULT_LOAI_CHI_PHI))
            .andExpect(jsonPath("$.soTien").value(DEFAULT_SO_TIEN))
            .andExpect(jsonPath("$.donViTienTe").value(DEFAULT_DON_VI_TIEN_TE));
    }

    @Test
    @Transactional
    void getNonExistingChiPhi() throws Exception {
        // Get the chiPhi
        restChiPhiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChiPhi() throws Exception {
        // Initialize the database
        chiPhiRepository.saveAndFlush(chiPhi);

        int databaseSizeBeforeUpdate = chiPhiRepository.findAll().size();

        // Update the chiPhi
        ChiPhi updatedChiPhi = chiPhiRepository.findById(chiPhi.getId()).get();
        // Disconnect from session so that the updates on updatedChiPhi are not directly saved in db
        em.detach(updatedChiPhi);
        updatedChiPhi.loaiChiPhi(UPDATED_LOAI_CHI_PHI).soTien(UPDATED_SO_TIEN).donViTienTe(UPDATED_DON_VI_TIEN_TE);

        restChiPhiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChiPhi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChiPhi))
            )
            .andExpect(status().isOk());

        // Validate the ChiPhi in the database
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeUpdate);
        ChiPhi testChiPhi = chiPhiList.get(chiPhiList.size() - 1);
        assertThat(testChiPhi.getLoaiChiPhi()).isEqualTo(UPDATED_LOAI_CHI_PHI);
        assertThat(testChiPhi.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
        assertThat(testChiPhi.getDonViTienTe()).isEqualTo(UPDATED_DON_VI_TIEN_TE);
    }

    @Test
    @Transactional
    void putNonExistingChiPhi() throws Exception {
        int databaseSizeBeforeUpdate = chiPhiRepository.findAll().size();
        chiPhi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiPhiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chiPhi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiPhi))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiPhi in the database
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChiPhi() throws Exception {
        int databaseSizeBeforeUpdate = chiPhiRepository.findAll().size();
        chiPhi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiPhiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chiPhi))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiPhi in the database
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChiPhi() throws Exception {
        int databaseSizeBeforeUpdate = chiPhiRepository.findAll().size();
        chiPhi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiPhiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chiPhi)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiPhi in the database
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChiPhiWithPatch() throws Exception {
        // Initialize the database
        chiPhiRepository.saveAndFlush(chiPhi);

        int databaseSizeBeforeUpdate = chiPhiRepository.findAll().size();

        // Update the chiPhi using partial update
        ChiPhi partialUpdatedChiPhi = new ChiPhi();
        partialUpdatedChiPhi.setId(chiPhi.getId());

        partialUpdatedChiPhi.soTien(UPDATED_SO_TIEN).donViTienTe(UPDATED_DON_VI_TIEN_TE);

        restChiPhiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiPhi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiPhi))
            )
            .andExpect(status().isOk());

        // Validate the ChiPhi in the database
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeUpdate);
        ChiPhi testChiPhi = chiPhiList.get(chiPhiList.size() - 1);
        assertThat(testChiPhi.getLoaiChiPhi()).isEqualTo(DEFAULT_LOAI_CHI_PHI);
        assertThat(testChiPhi.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
        assertThat(testChiPhi.getDonViTienTe()).isEqualTo(UPDATED_DON_VI_TIEN_TE);
    }

    @Test
    @Transactional
    void fullUpdateChiPhiWithPatch() throws Exception {
        // Initialize the database
        chiPhiRepository.saveAndFlush(chiPhi);

        int databaseSizeBeforeUpdate = chiPhiRepository.findAll().size();

        // Update the chiPhi using partial update
        ChiPhi partialUpdatedChiPhi = new ChiPhi();
        partialUpdatedChiPhi.setId(chiPhi.getId());

        partialUpdatedChiPhi.loaiChiPhi(UPDATED_LOAI_CHI_PHI).soTien(UPDATED_SO_TIEN).donViTienTe(UPDATED_DON_VI_TIEN_TE);

        restChiPhiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChiPhi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChiPhi))
            )
            .andExpect(status().isOk());

        // Validate the ChiPhi in the database
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeUpdate);
        ChiPhi testChiPhi = chiPhiList.get(chiPhiList.size() - 1);
        assertThat(testChiPhi.getLoaiChiPhi()).isEqualTo(UPDATED_LOAI_CHI_PHI);
        assertThat(testChiPhi.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
        assertThat(testChiPhi.getDonViTienTe()).isEqualTo(UPDATED_DON_VI_TIEN_TE);
    }

    @Test
    @Transactional
    void patchNonExistingChiPhi() throws Exception {
        int databaseSizeBeforeUpdate = chiPhiRepository.findAll().size();
        chiPhi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChiPhiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chiPhi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiPhi))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiPhi in the database
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChiPhi() throws Exception {
        int databaseSizeBeforeUpdate = chiPhiRepository.findAll().size();
        chiPhi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiPhiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chiPhi))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChiPhi in the database
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChiPhi() throws Exception {
        int databaseSizeBeforeUpdate = chiPhiRepository.findAll().size();
        chiPhi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChiPhiMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chiPhi)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChiPhi in the database
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChiPhi() throws Exception {
        // Initialize the database
        chiPhiRepository.saveAndFlush(chiPhi);

        int databaseSizeBeforeDelete = chiPhiRepository.findAll().size();

        // Delete the chiPhi
        restChiPhiMockMvc
            .perform(delete(ENTITY_API_URL_ID, chiPhi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChiPhi> chiPhiList = chiPhiRepository.findAll();
        assertThat(chiPhiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
