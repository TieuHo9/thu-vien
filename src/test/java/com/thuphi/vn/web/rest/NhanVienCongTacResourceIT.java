package com.thuphi.vn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thuphi.vn.IntegrationTest;
import com.thuphi.vn.domain.NhanVienCongTac;
import com.thuphi.vn.repository.NhanVienCongTacRepository;
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
 * Integration tests for the {@link NhanVienCongTacResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NhanVienCongTacResourceIT {

    private static final String DEFAULT_MA_NHAN_VIEN = "AAAAAAAAAA";
    private static final String UPDATED_MA_NHAN_VIEN = "BBBBBBBBBB";

    private static final String DEFAULT_MA_CHUYEN_DI = "AAAAAAAAAA";
    private static final String UPDATED_MA_CHUYEN_DI = "BBBBBBBBBB";

    private static final String DEFAULT_SO_TIEN = "AAAAAAAAAA";
    private static final String UPDATED_SO_TIEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nhan-vien-cong-tacs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NhanVienCongTacRepository nhanVienCongTacRepository;

    @Mock
    private NhanVienCongTacRepository nhanVienCongTacRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNhanVienCongTacMockMvc;

    private NhanVienCongTac nhanVienCongTac;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhanVienCongTac createEntity(EntityManager em) {
        NhanVienCongTac nhanVienCongTac = new NhanVienCongTac()
            .maNhanVien(DEFAULT_MA_NHAN_VIEN)
            .maChuyenDi(DEFAULT_MA_CHUYEN_DI)
            .soTien(DEFAULT_SO_TIEN);
        return nhanVienCongTac;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NhanVienCongTac createUpdatedEntity(EntityManager em) {
        NhanVienCongTac nhanVienCongTac = new NhanVienCongTac()
            .maNhanVien(UPDATED_MA_NHAN_VIEN)
            .maChuyenDi(UPDATED_MA_CHUYEN_DI)
            .soTien(UPDATED_SO_TIEN);
        return nhanVienCongTac;
    }

    @BeforeEach
    public void initTest() {
        nhanVienCongTac = createEntity(em);
    }

    @Test
    @Transactional
    void createNhanVienCongTac() throws Exception {
        int databaseSizeBeforeCreate = nhanVienCongTacRepository.findAll().size();
        // Create the NhanVienCongTac
        restNhanVienCongTacMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVienCongTac))
            )
            .andExpect(status().isCreated());

        // Validate the NhanVienCongTac in the database
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeCreate + 1);
        NhanVienCongTac testNhanVienCongTac = nhanVienCongTacList.get(nhanVienCongTacList.size() - 1);
        assertThat(testNhanVienCongTac.getMaNhanVien()).isEqualTo(DEFAULT_MA_NHAN_VIEN);
        assertThat(testNhanVienCongTac.getMaChuyenDi()).isEqualTo(DEFAULT_MA_CHUYEN_DI);
        assertThat(testNhanVienCongTac.getSoTien()).isEqualTo(DEFAULT_SO_TIEN);
    }

    @Test
    @Transactional
    void createNhanVienCongTacWithExistingId() throws Exception {
        // Create the NhanVienCongTac with an existing ID
        nhanVienCongTac.setId(1L);

        int databaseSizeBeforeCreate = nhanVienCongTacRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhanVienCongTacMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVienCongTac))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVienCongTac in the database
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNhanVienCongTacs() throws Exception {
        // Initialize the database
        nhanVienCongTacRepository.saveAndFlush(nhanVienCongTac);

        // Get all the nhanVienCongTacList
        restNhanVienCongTacMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhanVienCongTac.getId().intValue())))
            .andExpect(jsonPath("$.[*].maNhanVien").value(hasItem(DEFAULT_MA_NHAN_VIEN)))
            .andExpect(jsonPath("$.[*].maChuyenDi").value(hasItem(DEFAULT_MA_CHUYEN_DI)))
            .andExpect(jsonPath("$.[*].soTien").value(hasItem(DEFAULT_SO_TIEN)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNhanVienCongTacsWithEagerRelationshipsIsEnabled() throws Exception {
        when(nhanVienCongTacRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNhanVienCongTacMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(nhanVienCongTacRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNhanVienCongTacsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(nhanVienCongTacRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNhanVienCongTacMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(nhanVienCongTacRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getNhanVienCongTac() throws Exception {
        // Initialize the database
        nhanVienCongTacRepository.saveAndFlush(nhanVienCongTac);

        // Get the nhanVienCongTac
        restNhanVienCongTacMockMvc
            .perform(get(ENTITY_API_URL_ID, nhanVienCongTac.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nhanVienCongTac.getId().intValue()))
            .andExpect(jsonPath("$.maNhanVien").value(DEFAULT_MA_NHAN_VIEN))
            .andExpect(jsonPath("$.maChuyenDi").value(DEFAULT_MA_CHUYEN_DI))
            .andExpect(jsonPath("$.soTien").value(DEFAULT_SO_TIEN));
    }

    @Test
    @Transactional
    void getNonExistingNhanVienCongTac() throws Exception {
        // Get the nhanVienCongTac
        restNhanVienCongTacMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNhanVienCongTac() throws Exception {
        // Initialize the database
        nhanVienCongTacRepository.saveAndFlush(nhanVienCongTac);

        int databaseSizeBeforeUpdate = nhanVienCongTacRepository.findAll().size();

        // Update the nhanVienCongTac
        NhanVienCongTac updatedNhanVienCongTac = nhanVienCongTacRepository.findById(nhanVienCongTac.getId()).get();
        // Disconnect from session so that the updates on updatedNhanVienCongTac are not directly saved in db
        em.detach(updatedNhanVienCongTac);
        updatedNhanVienCongTac.maNhanVien(UPDATED_MA_NHAN_VIEN).maChuyenDi(UPDATED_MA_CHUYEN_DI).soTien(UPDATED_SO_TIEN);

        restNhanVienCongTacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNhanVienCongTac.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNhanVienCongTac))
            )
            .andExpect(status().isOk());

        // Validate the NhanVienCongTac in the database
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeUpdate);
        NhanVienCongTac testNhanVienCongTac = nhanVienCongTacList.get(nhanVienCongTacList.size() - 1);
        assertThat(testNhanVienCongTac.getMaNhanVien()).isEqualTo(UPDATED_MA_NHAN_VIEN);
        assertThat(testNhanVienCongTac.getMaChuyenDi()).isEqualTo(UPDATED_MA_CHUYEN_DI);
        assertThat(testNhanVienCongTac.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
    }

    @Test
    @Transactional
    void putNonExistingNhanVienCongTac() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienCongTacRepository.findAll().size();
        nhanVienCongTac.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhanVienCongTacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhanVienCongTac.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhanVienCongTac))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVienCongTac in the database
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNhanVienCongTac() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienCongTacRepository.findAll().size();
        nhanVienCongTac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienCongTacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhanVienCongTac))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVienCongTac in the database
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNhanVienCongTac() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienCongTacRepository.findAll().size();
        nhanVienCongTac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienCongTacMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhanVienCongTac))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhanVienCongTac in the database
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNhanVienCongTacWithPatch() throws Exception {
        // Initialize the database
        nhanVienCongTacRepository.saveAndFlush(nhanVienCongTac);

        int databaseSizeBeforeUpdate = nhanVienCongTacRepository.findAll().size();

        // Update the nhanVienCongTac using partial update
        NhanVienCongTac partialUpdatedNhanVienCongTac = new NhanVienCongTac();
        partialUpdatedNhanVienCongTac.setId(nhanVienCongTac.getId());

        partialUpdatedNhanVienCongTac.maChuyenDi(UPDATED_MA_CHUYEN_DI);

        restNhanVienCongTacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhanVienCongTac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhanVienCongTac))
            )
            .andExpect(status().isOk());

        // Validate the NhanVienCongTac in the database
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeUpdate);
        NhanVienCongTac testNhanVienCongTac = nhanVienCongTacList.get(nhanVienCongTacList.size() - 1);
        assertThat(testNhanVienCongTac.getMaNhanVien()).isEqualTo(DEFAULT_MA_NHAN_VIEN);
        assertThat(testNhanVienCongTac.getMaChuyenDi()).isEqualTo(UPDATED_MA_CHUYEN_DI);
        assertThat(testNhanVienCongTac.getSoTien()).isEqualTo(DEFAULT_SO_TIEN);
    }

    @Test
    @Transactional
    void fullUpdateNhanVienCongTacWithPatch() throws Exception {
        // Initialize the database
        nhanVienCongTacRepository.saveAndFlush(nhanVienCongTac);

        int databaseSizeBeforeUpdate = nhanVienCongTacRepository.findAll().size();

        // Update the nhanVienCongTac using partial update
        NhanVienCongTac partialUpdatedNhanVienCongTac = new NhanVienCongTac();
        partialUpdatedNhanVienCongTac.setId(nhanVienCongTac.getId());

        partialUpdatedNhanVienCongTac.maNhanVien(UPDATED_MA_NHAN_VIEN).maChuyenDi(UPDATED_MA_CHUYEN_DI).soTien(UPDATED_SO_TIEN);

        restNhanVienCongTacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhanVienCongTac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhanVienCongTac))
            )
            .andExpect(status().isOk());

        // Validate the NhanVienCongTac in the database
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeUpdate);
        NhanVienCongTac testNhanVienCongTac = nhanVienCongTacList.get(nhanVienCongTacList.size() - 1);
        assertThat(testNhanVienCongTac.getMaNhanVien()).isEqualTo(UPDATED_MA_NHAN_VIEN);
        assertThat(testNhanVienCongTac.getMaChuyenDi()).isEqualTo(UPDATED_MA_CHUYEN_DI);
        assertThat(testNhanVienCongTac.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
    }

    @Test
    @Transactional
    void patchNonExistingNhanVienCongTac() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienCongTacRepository.findAll().size();
        nhanVienCongTac.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhanVienCongTacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nhanVienCongTac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhanVienCongTac))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVienCongTac in the database
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNhanVienCongTac() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienCongTacRepository.findAll().size();
        nhanVienCongTac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienCongTacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhanVienCongTac))
            )
            .andExpect(status().isBadRequest());

        // Validate the NhanVienCongTac in the database
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNhanVienCongTac() throws Exception {
        int databaseSizeBeforeUpdate = nhanVienCongTacRepository.findAll().size();
        nhanVienCongTac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhanVienCongTacMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhanVienCongTac))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NhanVienCongTac in the database
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNhanVienCongTac() throws Exception {
        // Initialize the database
        nhanVienCongTacRepository.saveAndFlush(nhanVienCongTac);

        int databaseSizeBeforeDelete = nhanVienCongTacRepository.findAll().size();

        // Delete the nhanVienCongTac
        restNhanVienCongTacMockMvc
            .perform(delete(ENTITY_API_URL_ID, nhanVienCongTac.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NhanVienCongTac> nhanVienCongTacList = nhanVienCongTacRepository.findAll();
        assertThat(nhanVienCongTacList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
