package com.thuphi.vn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thuphi.vn.IntegrationTest;
import com.thuphi.vn.domain.DinhMuc;
import com.thuphi.vn.repository.DinhMucRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DinhMucResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DinhMucResourceIT {

    private static final String DEFAULT_MA_MUC = "AAAAAAAAAA";
    private static final String UPDATED_MA_MUC = "BBBBBBBBBB";

    private static final String DEFAULT_LOAI_PHI = "AAAAAAAAAA";
    private static final String UPDATED_LOAI_PHI = "BBBBBBBBBB";

    private static final String DEFAULT_SO_TIEN = "AAAAAAAAAA";
    private static final String UPDATED_SO_TIEN = "BBBBBBBBBB";

    private static final String DEFAULT_CAP_BAC = "AAAAAAAAAA";
    private static final String UPDATED_CAP_BAC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dinh-mucs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DinhMucRepository dinhMucRepository;

    @Mock
    private DinhMucRepository dinhMucRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDinhMucMockMvc;

    private DinhMuc dinhMuc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DinhMuc createEntity(EntityManager em) {
        DinhMuc dinhMuc = new DinhMuc().maMuc(DEFAULT_MA_MUC).loaiPhi(DEFAULT_LOAI_PHI).capBac(DEFAULT_CAP_BAC);
        return dinhMuc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DinhMuc createUpdatedEntity(EntityManager em) {
        DinhMuc dinhMuc = new DinhMuc().maMuc(UPDATED_MA_MUC).loaiPhi(UPDATED_LOAI_PHI).capBac(UPDATED_CAP_BAC);
        return dinhMuc;
    }

    @BeforeEach
    public void initTest() {
        dinhMuc = createEntity(em);
    }

    @Test
    @Transactional
    void createDinhMuc() throws Exception {
        int databaseSizeBeforeCreate = dinhMucRepository.findAll().size();
        // Create the DinhMuc
        restDinhMucMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dinhMuc)))
            .andExpect(status().isCreated());

        // Validate the DinhMuc in the database
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeCreate + 1);
        DinhMuc testDinhMuc = dinhMucList.get(dinhMucList.size() - 1);
        assertThat(testDinhMuc.getMaMuc()).isEqualTo(DEFAULT_MA_MUC);
        assertThat(testDinhMuc.getLoaiPhi()).isEqualTo(DEFAULT_LOAI_PHI);
        assertThat(testDinhMuc.getCapBac()).isEqualTo(DEFAULT_CAP_BAC);
    }

    @Test
    @Transactional
    void createDinhMucWithExistingId() throws Exception {
        // Create the DinhMuc with an existing ID
        dinhMuc.setId(1L);

        int databaseSizeBeforeCreate = dinhMucRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDinhMucMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dinhMuc)))
            .andExpect(status().isBadRequest());

        // Validate the DinhMuc in the database
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDinhMucs() throws Exception {
        // Initialize the database
        dinhMucRepository.saveAndFlush(dinhMuc);

        // Get all the dinhMucList
        restDinhMucMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dinhMuc.getId().intValue())))
            .andExpect(jsonPath("$.[*].maMuc").value(hasItem(DEFAULT_MA_MUC)))
            .andExpect(jsonPath("$.[*].loaiPhi").value(hasItem(DEFAULT_LOAI_PHI)))
            .andExpect(jsonPath("$.[*].soTien").value(hasItem(DEFAULT_SO_TIEN)))
            .andExpect(jsonPath("$.[*].capBac").value(hasItem(DEFAULT_CAP_BAC)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDinhMucsWithEagerRelationshipsIsEnabled() throws Exception {
        when(dinhMucRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDinhMucMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dinhMucRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDinhMucsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dinhMucRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDinhMucMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dinhMucRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDinhMuc() throws Exception {
        // Initialize the database
        dinhMucRepository.saveAndFlush(dinhMuc);

        // Get the dinhMuc
        restDinhMucMockMvc
            .perform(get(ENTITY_API_URL_ID, dinhMuc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dinhMuc.getId().intValue()))
            .andExpect(jsonPath("$.maMuc").value(DEFAULT_MA_MUC))
            .andExpect(jsonPath("$.loaiPhi").value(DEFAULT_LOAI_PHI))
            .andExpect(jsonPath("$.soTien").value(DEFAULT_SO_TIEN))
            .andExpect(jsonPath("$.capBac").value(DEFAULT_CAP_BAC));
    }

    @Test
    @Transactional
    void getNonExistingDinhMuc() throws Exception {
        // Get the dinhMuc
        restDinhMucMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDinhMuc() throws Exception {
        // Initialize the database
        dinhMucRepository.saveAndFlush(dinhMuc);

        int databaseSizeBeforeUpdate = dinhMucRepository.findAll().size();

        // Update the dinhMuc
        DinhMuc updatedDinhMuc = dinhMucRepository.findById(dinhMuc.getId()).get();
        // Disconnect from session so that the updates on updatedDinhMuc are not directly saved in db
        em.detach(updatedDinhMuc);
        updatedDinhMuc.maMuc(UPDATED_MA_MUC).loaiPhi(UPDATED_LOAI_PHI).capBac(UPDATED_CAP_BAC);

        restDinhMucMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDinhMuc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDinhMuc))
            )
            .andExpect(status().isOk());

        // Validate the DinhMuc in the database
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeUpdate);
        DinhMuc testDinhMuc = dinhMucList.get(dinhMucList.size() - 1);
        assertThat(testDinhMuc.getMaMuc()).isEqualTo(UPDATED_MA_MUC);
        assertThat(testDinhMuc.getLoaiPhi()).isEqualTo(UPDATED_LOAI_PHI);
        assertThat(testDinhMuc.getCapBac()).isEqualTo(UPDATED_CAP_BAC);
    }

    @Test
    @Transactional
    void putNonExistingDinhMuc() throws Exception {
        int databaseSizeBeforeUpdate = dinhMucRepository.findAll().size();
        dinhMuc.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDinhMucMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dinhMuc.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dinhMuc))
            )
            .andExpect(status().isBadRequest());

        // Validate the DinhMuc in the database
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDinhMuc() throws Exception {
        int databaseSizeBeforeUpdate = dinhMucRepository.findAll().size();
        dinhMuc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDinhMucMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dinhMuc))
            )
            .andExpect(status().isBadRequest());

        // Validate the DinhMuc in the database
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDinhMuc() throws Exception {
        int databaseSizeBeforeUpdate = dinhMucRepository.findAll().size();
        dinhMuc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDinhMucMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dinhMuc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DinhMuc in the database
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDinhMucWithPatch() throws Exception {
        // Initialize the database
        dinhMucRepository.saveAndFlush(dinhMuc);

        int databaseSizeBeforeUpdate = dinhMucRepository.findAll().size();

        // Update the dinhMuc using partial update
        DinhMuc partialUpdatedDinhMuc = new DinhMuc();
        partialUpdatedDinhMuc.setId(dinhMuc.getId());

        partialUpdatedDinhMuc.maMuc(UPDATED_MA_MUC).loaiPhi(UPDATED_LOAI_PHI);

        restDinhMucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDinhMuc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDinhMuc))
            )
            .andExpect(status().isOk());

        // Validate the DinhMuc in the database
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeUpdate);
        DinhMuc testDinhMuc = dinhMucList.get(dinhMucList.size() - 1);
        assertThat(testDinhMuc.getMaMuc()).isEqualTo(UPDATED_MA_MUC);
        assertThat(testDinhMuc.getLoaiPhi()).isEqualTo(UPDATED_LOAI_PHI);
        assertThat(testDinhMuc.getCapBac()).isEqualTo(DEFAULT_CAP_BAC);
    }

    @Test
    @Transactional
    void fullUpdateDinhMucWithPatch() throws Exception {
        // Initialize the database
        dinhMucRepository.saveAndFlush(dinhMuc);

        int databaseSizeBeforeUpdate = dinhMucRepository.findAll().size();

        // Update the dinhMuc using partial update
        DinhMuc partialUpdatedDinhMuc = new DinhMuc();
        partialUpdatedDinhMuc.setId(dinhMuc.getId());

        partialUpdatedDinhMuc.maMuc(UPDATED_MA_MUC).loaiPhi(UPDATED_LOAI_PHI).capBac(UPDATED_CAP_BAC);

        restDinhMucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDinhMuc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDinhMuc))
            )
            .andExpect(status().isOk());

        // Validate the DinhMuc in the database
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeUpdate);
        DinhMuc testDinhMuc = dinhMucList.get(dinhMucList.size() - 1);
        assertThat(testDinhMuc.getMaMuc()).isEqualTo(UPDATED_MA_MUC);
        assertThat(testDinhMuc.getLoaiPhi()).isEqualTo(UPDATED_LOAI_PHI);
        assertThat(testDinhMuc.getCapBac()).isEqualTo(UPDATED_CAP_BAC);
    }

    @Test
    @Transactional
    void patchNonExistingDinhMuc() throws Exception {
        int databaseSizeBeforeUpdate = dinhMucRepository.findAll().size();
        dinhMuc.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDinhMucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dinhMuc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dinhMuc))
            )
            .andExpect(status().isBadRequest());

        // Validate the DinhMuc in the database
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDinhMuc() throws Exception {
        int databaseSizeBeforeUpdate = dinhMucRepository.findAll().size();
        dinhMuc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDinhMucMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dinhMuc))
            )
            .andExpect(status().isBadRequest());

        // Validate the DinhMuc in the database
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDinhMuc() throws Exception {
        int databaseSizeBeforeUpdate = dinhMucRepository.findAll().size();
        dinhMuc.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDinhMucMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dinhMuc)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DinhMuc in the database
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDinhMuc() throws Exception {
        // Initialize the database
        dinhMucRepository.saveAndFlush(dinhMuc);

        int databaseSizeBeforeDelete = dinhMucRepository.findAll().size();

        // Delete the dinhMuc
        restDinhMucMockMvc
            .perform(delete(ENTITY_API_URL_ID, dinhMuc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DinhMuc> dinhMucList = dinhMucRepository.findAll();
        assertThat(dinhMucList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
