package com.thuphi.vn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thuphi.vn.IntegrationTest;
import com.thuphi.vn.domain.PhongBanNhanVien;
import com.thuphi.vn.repository.PhongBanNhanVienRepository;
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
 * Integration tests for the {@link PhongBanNhanVienResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PhongBanNhanVienResourceIT {

    private static final String DEFAULT_MA_PHONG = "AAAAAAAAAA";
    private static final String UPDATED_MA_PHONG = "BBBBBBBBBB";

    private static final String DEFAULT_MA_NHAN_VIEN = "AAAAAAAAAA";
    private static final String UPDATED_MA_NHAN_VIEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phong-ban-nhan-viens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhongBanNhanVienRepository phongBanNhanVienRepository;

    @Mock
    private PhongBanNhanVienRepository phongBanNhanVienRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhongBanNhanVienMockMvc;

    private PhongBanNhanVien phongBanNhanVien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhongBanNhanVien createEntity(EntityManager em) {
        PhongBanNhanVien phongBanNhanVien = new PhongBanNhanVien().maPhong(DEFAULT_MA_PHONG).maNhanVien(DEFAULT_MA_NHAN_VIEN);
        return phongBanNhanVien;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhongBanNhanVien createUpdatedEntity(EntityManager em) {
        PhongBanNhanVien phongBanNhanVien = new PhongBanNhanVien().maPhong(UPDATED_MA_PHONG).maNhanVien(UPDATED_MA_NHAN_VIEN);
        return phongBanNhanVien;
    }

    @BeforeEach
    public void initTest() {
        phongBanNhanVien = createEntity(em);
    }

    @Test
    @Transactional
    void createPhongBanNhanVien() throws Exception {
        int databaseSizeBeforeCreate = phongBanNhanVienRepository.findAll().size();
        // Create the PhongBanNhanVien
        restPhongBanNhanVienMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongBanNhanVien))
            )
            .andExpect(status().isCreated());

        // Validate the PhongBanNhanVien in the database
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeCreate + 1);
        PhongBanNhanVien testPhongBanNhanVien = phongBanNhanVienList.get(phongBanNhanVienList.size() - 1);
        assertThat(testPhongBanNhanVien.getMaPhong()).isEqualTo(DEFAULT_MA_PHONG);
        assertThat(testPhongBanNhanVien.getMaNhanVien()).isEqualTo(DEFAULT_MA_NHAN_VIEN);
    }

    @Test
    @Transactional
    void createPhongBanNhanVienWithExistingId() throws Exception {
        // Create the PhongBanNhanVien with an existing ID
        phongBanNhanVien.setId(1L);

        int databaseSizeBeforeCreate = phongBanNhanVienRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhongBanNhanVienMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongBanNhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBanNhanVien in the database
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPhongBanNhanViens() throws Exception {
        // Initialize the database
        phongBanNhanVienRepository.saveAndFlush(phongBanNhanVien);

        // Get all the phongBanNhanVienList
        restPhongBanNhanVienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phongBanNhanVien.getId().intValue())))
            .andExpect(jsonPath("$.[*].maPhong").value(hasItem(DEFAULT_MA_PHONG)))
            .andExpect(jsonPath("$.[*].maNhanVien").value(hasItem(DEFAULT_MA_NHAN_VIEN)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPhongBanNhanViensWithEagerRelationshipsIsEnabled() throws Exception {
        when(phongBanNhanVienRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPhongBanNhanVienMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(phongBanNhanVienRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPhongBanNhanViensWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(phongBanNhanVienRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPhongBanNhanVienMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(phongBanNhanVienRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getPhongBanNhanVien() throws Exception {
        // Initialize the database
        phongBanNhanVienRepository.saveAndFlush(phongBanNhanVien);

        // Get the phongBanNhanVien
        restPhongBanNhanVienMockMvc
            .perform(get(ENTITY_API_URL_ID, phongBanNhanVien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phongBanNhanVien.getId().intValue()))
            .andExpect(jsonPath("$.maPhong").value(DEFAULT_MA_PHONG))
            .andExpect(jsonPath("$.maNhanVien").value(DEFAULT_MA_NHAN_VIEN));
    }

    @Test
    @Transactional
    void getNonExistingPhongBanNhanVien() throws Exception {
        // Get the phongBanNhanVien
        restPhongBanNhanVienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPhongBanNhanVien() throws Exception {
        // Initialize the database
        phongBanNhanVienRepository.saveAndFlush(phongBanNhanVien);

        int databaseSizeBeforeUpdate = phongBanNhanVienRepository.findAll().size();

        // Update the phongBanNhanVien
        PhongBanNhanVien updatedPhongBanNhanVien = phongBanNhanVienRepository.findById(phongBanNhanVien.getId()).get();
        // Disconnect from session so that the updates on updatedPhongBanNhanVien are not directly saved in db
        em.detach(updatedPhongBanNhanVien);
        updatedPhongBanNhanVien.maPhong(UPDATED_MA_PHONG).maNhanVien(UPDATED_MA_NHAN_VIEN);

        restPhongBanNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPhongBanNhanVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPhongBanNhanVien))
            )
            .andExpect(status().isOk());

        // Validate the PhongBanNhanVien in the database
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeUpdate);
        PhongBanNhanVien testPhongBanNhanVien = phongBanNhanVienList.get(phongBanNhanVienList.size() - 1);
        assertThat(testPhongBanNhanVien.getMaPhong()).isEqualTo(UPDATED_MA_PHONG);
        assertThat(testPhongBanNhanVien.getMaNhanVien()).isEqualTo(UPDATED_MA_NHAN_VIEN);
    }

    @Test
    @Transactional
    void putNonExistingPhongBanNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = phongBanNhanVienRepository.findAll().size();
        phongBanNhanVien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongBanNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phongBanNhanVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongBanNhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBanNhanVien in the database
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhongBanNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = phongBanNhanVienRepository.findAll().size();
        phongBanNhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongBanNhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBanNhanVien in the database
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhongBanNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = phongBanNhanVienRepository.findAll().size();
        phongBanNhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongBanNhanVien))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhongBanNhanVien in the database
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhongBanNhanVienWithPatch() throws Exception {
        // Initialize the database
        phongBanNhanVienRepository.saveAndFlush(phongBanNhanVien);

        int databaseSizeBeforeUpdate = phongBanNhanVienRepository.findAll().size();

        // Update the phongBanNhanVien using partial update
        PhongBanNhanVien partialUpdatedPhongBanNhanVien = new PhongBanNhanVien();
        partialUpdatedPhongBanNhanVien.setId(phongBanNhanVien.getId());

        restPhongBanNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongBanNhanVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongBanNhanVien))
            )
            .andExpect(status().isOk());

        // Validate the PhongBanNhanVien in the database
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeUpdate);
        PhongBanNhanVien testPhongBanNhanVien = phongBanNhanVienList.get(phongBanNhanVienList.size() - 1);
        assertThat(testPhongBanNhanVien.getMaPhong()).isEqualTo(DEFAULT_MA_PHONG);
        assertThat(testPhongBanNhanVien.getMaNhanVien()).isEqualTo(DEFAULT_MA_NHAN_VIEN);
    }

    @Test
    @Transactional
    void fullUpdatePhongBanNhanVienWithPatch() throws Exception {
        // Initialize the database
        phongBanNhanVienRepository.saveAndFlush(phongBanNhanVien);

        int databaseSizeBeforeUpdate = phongBanNhanVienRepository.findAll().size();

        // Update the phongBanNhanVien using partial update
        PhongBanNhanVien partialUpdatedPhongBanNhanVien = new PhongBanNhanVien();
        partialUpdatedPhongBanNhanVien.setId(phongBanNhanVien.getId());

        partialUpdatedPhongBanNhanVien.maPhong(UPDATED_MA_PHONG).maNhanVien(UPDATED_MA_NHAN_VIEN);

        restPhongBanNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongBanNhanVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongBanNhanVien))
            )
            .andExpect(status().isOk());

        // Validate the PhongBanNhanVien in the database
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeUpdate);
        PhongBanNhanVien testPhongBanNhanVien = phongBanNhanVienList.get(phongBanNhanVienList.size() - 1);
        assertThat(testPhongBanNhanVien.getMaPhong()).isEqualTo(UPDATED_MA_PHONG);
        assertThat(testPhongBanNhanVien.getMaNhanVien()).isEqualTo(UPDATED_MA_NHAN_VIEN);
    }

    @Test
    @Transactional
    void patchNonExistingPhongBanNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = phongBanNhanVienRepository.findAll().size();
        phongBanNhanVien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongBanNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phongBanNhanVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongBanNhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBanNhanVien in the database
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhongBanNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = phongBanNhanVienRepository.findAll().size();
        phongBanNhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongBanNhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongBanNhanVien in the database
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhongBanNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = phongBanNhanVienRepository.findAll().size();
        phongBanNhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongBanNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongBanNhanVien))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhongBanNhanVien in the database
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhongBanNhanVien() throws Exception {
        // Initialize the database
        phongBanNhanVienRepository.saveAndFlush(phongBanNhanVien);

        int databaseSizeBeforeDelete = phongBanNhanVienRepository.findAll().size();

        // Delete the phongBanNhanVien
        restPhongBanNhanVienMockMvc
            .perform(delete(ENTITY_API_URL_ID, phongBanNhanVien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhongBanNhanVien> phongBanNhanVienList = phongBanNhanVienRepository.findAll();
        assertThat(phongBanNhanVienList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
