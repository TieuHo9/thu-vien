package com.thuphi.vn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thuphi.vn.IntegrationTest;
import com.thuphi.vn.domain.NhanVien;
import com.thuphi.vn.repository.NhanVienRepository;
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
 * Integration tests for the {@link NhanVienResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NhanVienResourceIT {

    private static final String DEFAULT_MA_NHAN_VIEN = "AAAAAAAAAA";
    private static final String UPDATED_MA_NHAN_VIEN = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_NHAN_VIEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN_NHAN_VIEN = "BBBBBBBBBB";

    private static final String DEFAULT_PHONG_BAN = "AAAAAAAAAA";
    private static final String UPDATED_PHONG_BAN = "BBBBBBBBBB";

    private static final String DEFAULT_CAP_BAC = "AAAAAAAAAA";
    private static final String UPDATED_CAP_BAC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nhan-viens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Mock
    private NhanVienRepository nhanVienRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNhanVienMockMvc;

    private NhanVien nhanVien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhanVien createEntity(EntityManager em) {
        NhanVien nhanVien = new NhanVien()
            .maNhanVien(DEFAULT_MA_NHAN_VIEN)
            .tenNhanVien(DEFAULT_TEN_NHAN_VIEN)
            .phongBan(DEFAULT_PHONG_BAN)
            .capBac(DEFAULT_CAP_BAC);
        return nhanVien;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhanVien createUpdatedEntity(EntityManager em) {
        NhanVien nhanVien = new NhanVien()
            .maNhanVien(UPDATED_MA_NHAN_VIEN)
            .tenNhanVien(UPDATED_TEN_NHAN_VIEN)
            .phongBan(UPDATED_PHONG_BAN)
            .capBac(UPDATED_CAP_BAC);
        return nhanVien;
    }

    @BeforeEach
    public void initTest() {
        nhanVien = createEntity(em);
    }

    @Test
    @Transactional
    void createNhanVien() throws Exception {
        int databaseSizeBeforeCreate = nhanVienRepository.findAll().size();
        // Create the NhanVien
        restNhanVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isCreated());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeCreate + 1);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getMaNhanVien()).isEqualTo(DEFAULT_MA_NHAN_VIEN);
        assertThat(testNhanVien.getTenNhanVien()).isEqualTo(DEFAULT_TEN_NHAN_VIEN);
        assertThat(testNhanVien.getPhongBan()).isEqualTo(DEFAULT_PHONG_BAN);
        assertThat(testNhanVien.getCapBac()).isEqualTo(DEFAULT_CAP_BAC);
    }

    @Test
    @Transactional
    void createNhanVienWithExistingId() throws Exception {
        // Create the NhanVien with an existing ID
        nhanVien.setId(1L);

        int databaseSizeBeforeCreate = nhanVienRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhanVienMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNhanViens() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get all the nhanVienList
        restNhanVienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhanVien.getId().intValue())))
            .andExpect(jsonPath("$.[*].maNhanVien").value(hasItem(DEFAULT_MA_NHAN_VIEN)))
            .andExpect(jsonPath("$.[*].tenNhanVien").value(hasItem(DEFAULT_TEN_NHAN_VIEN)))
            .andExpect(jsonPath("$.[*].phongBan").value(hasItem(DEFAULT_PHONG_BAN)))
            .andExpect(jsonPath("$.[*].capBac").value(hasItem(DEFAULT_CAP_BAC)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNhanViensWithEagerRelationshipsIsEnabled() throws Exception {
        when(nhanVienRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNhanVienMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(nhanVienRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNhanViensWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(nhanVienRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNhanVienMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(nhanVienRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        // Get the nhanVien
        restNhanVienMockMvc
            .perform(get(ENTITY_API_URL_ID, nhanVien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nhanVien.getId().intValue()))
            .andExpect(jsonPath("$.maNhanVien").value(DEFAULT_MA_NHAN_VIEN))
            .andExpect(jsonPath("$.tenNhanVien").value(DEFAULT_TEN_NHAN_VIEN))
            .andExpect(jsonPath("$.phongBan").value(DEFAULT_PHONG_BAN))
            .andExpect(jsonPath("$.capBac").value(DEFAULT_CAP_BAC));
    }

    @Test
    @Transactional
    void getNonExistingNhanVien() throws Exception {
        // Get the nhanVien
        restNhanVienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();

        // Update the nhanVien
        NhanVien updatedNhanVien = nhanVienRepository.findById(nhanVien.getId()).get();
        // Disconnect from session so that the updates on updatedNhanVien are not directly saved in db
        em.detach(updatedNhanVien);
        updatedNhanVien
            .maNhanVien(UPDATED_MA_NHAN_VIEN)
            .tenNhanVien(UPDATED_TEN_NHAN_VIEN)
            .phongBan(UPDATED_PHONG_BAN)
            .capBac(UPDATED_CAP_BAC);

        restNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNhanVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNhanVien))
            )
            .andExpect(status().isOk());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getMaNhanVien()).isEqualTo(UPDATED_MA_NHAN_VIEN);
        assertThat(testNhanVien.getTenNhanVien()).isEqualTo(UPDATED_TEN_NHAN_VIEN);
        assertThat(testNhanVien.getPhongBan()).isEqualTo(UPDATED_PHONG_BAN);
        assertThat(testNhanVien.getCapBac()).isEqualTo(UPDATED_CAP_BAC);
    }

    @Test
    @Transactional
    void putNonExistingNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhanVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNhanVienWithPatch() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();

        // Update the nhanVien using partial update
        NhanVien partialUpdatedNhanVien = new NhanVien();
        partialUpdatedNhanVien.setId(nhanVien.getId());

        partialUpdatedNhanVien.tenNhanVien(UPDATED_TEN_NHAN_VIEN).capBac(UPDATED_CAP_BAC);

        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhanVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhanVien))
            )
            .andExpect(status().isOk());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getMaNhanVien()).isEqualTo(DEFAULT_MA_NHAN_VIEN);
        assertThat(testNhanVien.getTenNhanVien()).isEqualTo(UPDATED_TEN_NHAN_VIEN);
        assertThat(testNhanVien.getPhongBan()).isEqualTo(DEFAULT_PHONG_BAN);
        assertThat(testNhanVien.getCapBac()).isEqualTo(UPDATED_CAP_BAC);
    }

    @Test
    @Transactional
    void fullUpdateNhanVienWithPatch() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();

        // Update the nhanVien using partial update
        NhanVien partialUpdatedNhanVien = new NhanVien();
        partialUpdatedNhanVien.setId(nhanVien.getId());

        partialUpdatedNhanVien
            .maNhanVien(UPDATED_MA_NHAN_VIEN)
            .tenNhanVien(UPDATED_TEN_NHAN_VIEN)
            .phongBan(UPDATED_PHONG_BAN)
            .capBac(UPDATED_CAP_BAC);

        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhanVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhanVien))
            )
            .andExpect(status().isOk());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
        NhanVien testNhanVien = nhanVienList.get(nhanVienList.size() - 1);
        assertThat(testNhanVien.getMaNhanVien()).isEqualTo(UPDATED_MA_NHAN_VIEN);
        assertThat(testNhanVien.getTenNhanVien()).isEqualTo(UPDATED_TEN_NHAN_VIEN);
        assertThat(testNhanVien.getPhongBan()).isEqualTo(UPDATED_PHONG_BAN);
        assertThat(testNhanVien.getCapBac()).isEqualTo(UPDATED_CAP_BAC);
    }

    @Test
    @Transactional
    void patchNonExistingNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nhanVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhanVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNhanVien() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienRepository.findAll().size();
        nhanVien.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nhanVien)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhanVien in the database
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNhanVien() throws Exception {
        // Initialize the database
        nhanVienRepository.saveAndFlush(nhanVien);

        int databaseSizeBeforeDelete = nhanVienRepository.findAll().size();

        // Delete the nhanVien
        restNhanVienMockMvc
            .perform(delete(ENTITY_API_URL_ID, nhanVien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
        assertThat(nhanVienList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
