package com.thuphi.vn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thuphi.vn.IntegrationTest;
import com.thuphi.vn.domain.DeXuatThanhToan;
import com.thuphi.vn.repository.DeXuatThanhToanRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link DeXuatThanhToanResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DeXuatThanhToanResourceIT {

    private static final String DEFAULT_MA_DE_XUAT = "AAAAAAAAAA";
    private static final String UPDATED_MA_DE_XUAT = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_DE_XUAT = "AAAAAAAAAA";
    private static final String UPDATED_TEN_DE_XUAT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TU_NGAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TU_NGAY = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DEN_NGAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEN_NGAY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TRANG_THAI_TRUONG_PHONG = "AAAAAAAAAA";
    private static final String UPDATED_TRANG_THAI_TRUONG_PHONG = "BBBBBBBBBB";

    private static final String DEFAULT_TRANG_THAI_PHONG_TAI_VU = "AAAAAAAAAA";
    private static final String UPDATED_TRANG_THAI_PHONG_TAI_VU = "BBBBBBBBBB";

    private static final String DEFAULT_TRANG_THAI_BAN_LANH_DAO = "AAAAAAAAAA";
    private static final String UPDATED_TRANG_THAI_BAN_LANH_DAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/de-xuat-thanh-toans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeXuatThanhToanRepository deXuatThanhToanRepository;

    @Mock
    private DeXuatThanhToanRepository deXuatThanhToanRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeXuatThanhToanMockMvc;

    private DeXuatThanhToan deXuatThanhToan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeXuatThanhToan createEntity(EntityManager em) {
        DeXuatThanhToan deXuatThanhToan = new DeXuatThanhToan()
            .maDeXuat(DEFAULT_MA_DE_XUAT)
            .tenDeXuat(DEFAULT_TEN_DE_XUAT)
            .tuNgay(DEFAULT_TU_NGAY)
            .denNgay(DEFAULT_DEN_NGAY)
            .trangThaiTruongPhong(DEFAULT_TRANG_THAI_TRUONG_PHONG)
            .trangThaiPhongTaiVu(DEFAULT_TRANG_THAI_PHONG_TAI_VU)
            .trangThaiBanLanhDao(DEFAULT_TRANG_THAI_BAN_LANH_DAO);
        return deXuatThanhToan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeXuatThanhToan createUpdatedEntity(EntityManager em) {
        DeXuatThanhToan deXuatThanhToan = new DeXuatThanhToan()
            .maDeXuat(UPDATED_MA_DE_XUAT)
            .tenDeXuat(UPDATED_TEN_DE_XUAT)
            .tuNgay(UPDATED_TU_NGAY)
            .denNgay(UPDATED_DEN_NGAY)
            .trangThaiTruongPhong(UPDATED_TRANG_THAI_TRUONG_PHONG)
            .trangThaiPhongTaiVu(UPDATED_TRANG_THAI_PHONG_TAI_VU)
            .trangThaiBanLanhDao(UPDATED_TRANG_THAI_BAN_LANH_DAO);
        return deXuatThanhToan;
    }

    @BeforeEach
    public void initTest() {
        deXuatThanhToan = createEntity(em);
    }

    @Test
    @Transactional
    void createDeXuatThanhToan() throws Exception {
        int databaseSizeBeforeCreate = deXuatThanhToanRepository.findAll().size();
        // Create the DeXuatThanhToan
        restDeXuatThanhToanMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deXuatThanhToan))
            )
            .andExpect(status().isCreated());

        // Validate the DeXuatThanhToan in the database
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeCreate + 1);
        DeXuatThanhToan testDeXuatThanhToan = deXuatThanhToanList.get(deXuatThanhToanList.size() - 1);
        assertThat(testDeXuatThanhToan.getMaDeXuat()).isEqualTo(DEFAULT_MA_DE_XUAT);
        assertThat(testDeXuatThanhToan.getTenDeXuat()).isEqualTo(DEFAULT_TEN_DE_XUAT);
        assertThat(testDeXuatThanhToan.getTuNgay()).isEqualTo(DEFAULT_TU_NGAY);
        assertThat(testDeXuatThanhToan.getDenNgay()).isEqualTo(DEFAULT_DEN_NGAY);
        assertThat(testDeXuatThanhToan.getTrangThaiTruongPhong()).isEqualTo(DEFAULT_TRANG_THAI_TRUONG_PHONG);
        assertThat(testDeXuatThanhToan.getTrangThaiPhongTaiVu()).isEqualTo(DEFAULT_TRANG_THAI_PHONG_TAI_VU);
        assertThat(testDeXuatThanhToan.getTrangThaiBanLanhDao()).isEqualTo(DEFAULT_TRANG_THAI_BAN_LANH_DAO);
    }

    @Test
    @Transactional
    void createDeXuatThanhToanWithExistingId() throws Exception {
        // Create the DeXuatThanhToan with an existing ID
        deXuatThanhToan.setId(1L);

        int databaseSizeBeforeCreate = deXuatThanhToanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeXuatThanhToanMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deXuatThanhToan))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeXuatThanhToan in the database
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeXuatThanhToans() throws Exception {
        // Initialize the database
        deXuatThanhToanRepository.saveAndFlush(deXuatThanhToan);

        // Get all the deXuatThanhToanList
        restDeXuatThanhToanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deXuatThanhToan.getId().intValue())))
            .andExpect(jsonPath("$.[*].maDeXuat").value(hasItem(DEFAULT_MA_DE_XUAT)))
            .andExpect(jsonPath("$.[*].tenDeXuat").value(hasItem(DEFAULT_TEN_DE_XUAT)))
            .andExpect(jsonPath("$.[*].tuNgay").value(hasItem(DEFAULT_TU_NGAY.toString())))
            .andExpect(jsonPath("$.[*].denNgay").value(hasItem(DEFAULT_DEN_NGAY.toString())))
            .andExpect(jsonPath("$.[*].trangThaiTruongPhong").value(hasItem(DEFAULT_TRANG_THAI_TRUONG_PHONG)))
            .andExpect(jsonPath("$.[*].trangThaiPhongTaiVu").value(hasItem(DEFAULT_TRANG_THAI_PHONG_TAI_VU)))
            .andExpect(jsonPath("$.[*].trangThaiBanLanhDao").value(hasItem(DEFAULT_TRANG_THAI_BAN_LANH_DAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDeXuatThanhToansWithEagerRelationshipsIsEnabled() throws Exception {
        when(deXuatThanhToanRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeXuatThanhToanMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(deXuatThanhToanRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDeXuatThanhToansWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(deXuatThanhToanRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeXuatThanhToanMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(deXuatThanhToanRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDeXuatThanhToan() throws Exception {
        // Initialize the database
        deXuatThanhToanRepository.saveAndFlush(deXuatThanhToan);

        // Get the deXuatThanhToan
        restDeXuatThanhToanMockMvc
            .perform(get(ENTITY_API_URL_ID, deXuatThanhToan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deXuatThanhToan.getId().intValue()))
            .andExpect(jsonPath("$.maDeXuat").value(DEFAULT_MA_DE_XUAT))
            .andExpect(jsonPath("$.tenDeXuat").value(DEFAULT_TEN_DE_XUAT))
            .andExpect(jsonPath("$.tuNgay").value(DEFAULT_TU_NGAY.toString()))
            .andExpect(jsonPath("$.denNgay").value(DEFAULT_DEN_NGAY.toString()))
            .andExpect(jsonPath("$.trangThaiTruongPhong").value(DEFAULT_TRANG_THAI_TRUONG_PHONG))
            .andExpect(jsonPath("$.trangThaiPhongTaiVu").value(DEFAULT_TRANG_THAI_PHONG_TAI_VU))
            .andExpect(jsonPath("$.trangThaiBanLanhDao").value(DEFAULT_TRANG_THAI_BAN_LANH_DAO));
    }

    @Test
    @Transactional
    void getNonExistingDeXuatThanhToan() throws Exception {
        // Get the deXuatThanhToan
        restDeXuatThanhToanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeXuatThanhToan() throws Exception {
        // Initialize the database
        deXuatThanhToanRepository.saveAndFlush(deXuatThanhToan);

        int databaseSizeBeforeUpdate = deXuatThanhToanRepository.findAll().size();

        // Update the deXuatThanhToan
        //        DeXuatThanhToan updatedDeXuatThanhToan = deXuatThanhToanRepository.findById(deXuatThanhToan.getId()).get();
        // Disconnect from session so that the updates on updatedDeXuatThanhToan are not directly saved in db
        //        em.detach(updatedDeXuatThanhToan);
        //        updatedDeXuatThanhToan
        //            .maDeXuat(UPDATED_MA_DE_XUAT)
        //            .tenDeXuat(UPDATED_TEN_DE_XUAT)
        //            .tuNgay(UPDATED_TU_NGAY)
        //            .denNgay(UPDATED_DEN_NGAY)
        //            .trangThaiTruongPhong(UPDATED_TRANG_THAI_TRUONG_PHONG)
        //            .trangThaiPhongTaiVu(UPDATED_TRANG_THAI_PHONG_TAI_VU)
        //            .trangThaiBanLanhDao(UPDATED_TRANG_THAI_BAN_LANH_DAO);

        //        restDeXuatThanhToanMockMvc
        //            .perform(
        //                put(ENTITY_API_URL_ID, updatedDeXuatThanhToan.getId())
        //                    .contentType(MediaType.APPLICATION_JSON)
        //                    .content(TestUtil.convertObjectToJsonBytes(updatedDeXuatThanhToan))
        //            )
        //            .andExpect(status().isOk());

        // Validate the DeXuatThanhToan in the database
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeUpdate);
        DeXuatThanhToan testDeXuatThanhToan = deXuatThanhToanList.get(deXuatThanhToanList.size() - 1);
        assertThat(testDeXuatThanhToan.getMaDeXuat()).isEqualTo(UPDATED_MA_DE_XUAT);
        assertThat(testDeXuatThanhToan.getTenDeXuat()).isEqualTo(UPDATED_TEN_DE_XUAT);
        assertThat(testDeXuatThanhToan.getTuNgay()).isEqualTo(UPDATED_TU_NGAY);
        assertThat(testDeXuatThanhToan.getDenNgay()).isEqualTo(UPDATED_DEN_NGAY);
        assertThat(testDeXuatThanhToan.getTrangThaiTruongPhong()).isEqualTo(UPDATED_TRANG_THAI_TRUONG_PHONG);
        assertThat(testDeXuatThanhToan.getTrangThaiPhongTaiVu()).isEqualTo(UPDATED_TRANG_THAI_PHONG_TAI_VU);
        assertThat(testDeXuatThanhToan.getTrangThaiBanLanhDao()).isEqualTo(UPDATED_TRANG_THAI_BAN_LANH_DAO);
    }

    @Test
    @Transactional
    void putNonExistingDeXuatThanhToan() throws Exception {
        int databaseSizeBeforeUpdate = deXuatThanhToanRepository.findAll().size();
        deXuatThanhToan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeXuatThanhToanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deXuatThanhToan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deXuatThanhToan))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeXuatThanhToan in the database
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeXuatThanhToan() throws Exception {
        int databaseSizeBeforeUpdate = deXuatThanhToanRepository.findAll().size();
        deXuatThanhToan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeXuatThanhToanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deXuatThanhToan))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeXuatThanhToan in the database
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeXuatThanhToan() throws Exception {
        int databaseSizeBeforeUpdate = deXuatThanhToanRepository.findAll().size();
        deXuatThanhToan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeXuatThanhToanMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deXuatThanhToan))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeXuatThanhToan in the database
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeXuatThanhToanWithPatch() throws Exception {
        // Initialize the database
        deXuatThanhToanRepository.saveAndFlush(deXuatThanhToan);

        int databaseSizeBeforeUpdate = deXuatThanhToanRepository.findAll().size();

        // Update the deXuatThanhToan using partial update
        DeXuatThanhToan partialUpdatedDeXuatThanhToan = new DeXuatThanhToan();
        partialUpdatedDeXuatThanhToan.setId(deXuatThanhToan.getId());

        partialUpdatedDeXuatThanhToan
            .maDeXuat(UPDATED_MA_DE_XUAT)
            .tenDeXuat(UPDATED_TEN_DE_XUAT)
            .tuNgay(UPDATED_TU_NGAY)
            .trangThaiPhongTaiVu(UPDATED_TRANG_THAI_PHONG_TAI_VU)
            .trangThaiBanLanhDao(UPDATED_TRANG_THAI_BAN_LANH_DAO);

        restDeXuatThanhToanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeXuatThanhToan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeXuatThanhToan))
            )
            .andExpect(status().isOk());

        // Validate the DeXuatThanhToan in the database
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeUpdate);
        DeXuatThanhToan testDeXuatThanhToan = deXuatThanhToanList.get(deXuatThanhToanList.size() - 1);
        assertThat(testDeXuatThanhToan.getMaDeXuat()).isEqualTo(UPDATED_MA_DE_XUAT);
        assertThat(testDeXuatThanhToan.getTenDeXuat()).isEqualTo(UPDATED_TEN_DE_XUAT);
        assertThat(testDeXuatThanhToan.getTuNgay()).isEqualTo(UPDATED_TU_NGAY);
        assertThat(testDeXuatThanhToan.getDenNgay()).isEqualTo(DEFAULT_DEN_NGAY);
        assertThat(testDeXuatThanhToan.getTrangThaiTruongPhong()).isEqualTo(DEFAULT_TRANG_THAI_TRUONG_PHONG);
        assertThat(testDeXuatThanhToan.getTrangThaiPhongTaiVu()).isEqualTo(UPDATED_TRANG_THAI_PHONG_TAI_VU);
        assertThat(testDeXuatThanhToan.getTrangThaiBanLanhDao()).isEqualTo(UPDATED_TRANG_THAI_BAN_LANH_DAO);
    }

    @Test
    @Transactional
    void fullUpdateDeXuatThanhToanWithPatch() throws Exception {
        // Initialize the database
        deXuatThanhToanRepository.saveAndFlush(deXuatThanhToan);

        int databaseSizeBeforeUpdate = deXuatThanhToanRepository.findAll().size();

        // Update the deXuatThanhToan using partial update
        DeXuatThanhToan partialUpdatedDeXuatThanhToan = new DeXuatThanhToan();
        partialUpdatedDeXuatThanhToan.setId(deXuatThanhToan.getId());

        partialUpdatedDeXuatThanhToan
            .maDeXuat(UPDATED_MA_DE_XUAT)
            .tenDeXuat(UPDATED_TEN_DE_XUAT)
            .tuNgay(UPDATED_TU_NGAY)
            .denNgay(UPDATED_DEN_NGAY)
            .trangThaiTruongPhong(UPDATED_TRANG_THAI_TRUONG_PHONG)
            .trangThaiPhongTaiVu(UPDATED_TRANG_THAI_PHONG_TAI_VU)
            .trangThaiBanLanhDao(UPDATED_TRANG_THAI_BAN_LANH_DAO);

        restDeXuatThanhToanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeXuatThanhToan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeXuatThanhToan))
            )
            .andExpect(status().isOk());

        // Validate the DeXuatThanhToan in the database
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeUpdate);
        DeXuatThanhToan testDeXuatThanhToan = deXuatThanhToanList.get(deXuatThanhToanList.size() - 1);
        assertThat(testDeXuatThanhToan.getMaDeXuat()).isEqualTo(UPDATED_MA_DE_XUAT);
        assertThat(testDeXuatThanhToan.getTenDeXuat()).isEqualTo(UPDATED_TEN_DE_XUAT);
        assertThat(testDeXuatThanhToan.getTuNgay()).isEqualTo(UPDATED_TU_NGAY);
        assertThat(testDeXuatThanhToan.getDenNgay()).isEqualTo(UPDATED_DEN_NGAY);
        assertThat(testDeXuatThanhToan.getTrangThaiTruongPhong()).isEqualTo(UPDATED_TRANG_THAI_TRUONG_PHONG);
        assertThat(testDeXuatThanhToan.getTrangThaiPhongTaiVu()).isEqualTo(UPDATED_TRANG_THAI_PHONG_TAI_VU);
        assertThat(testDeXuatThanhToan.getTrangThaiBanLanhDao()).isEqualTo(UPDATED_TRANG_THAI_BAN_LANH_DAO);
    }

    @Test
    @Transactional
    void patchNonExistingDeXuatThanhToan() throws Exception {
        int databaseSizeBeforeUpdate = deXuatThanhToanRepository.findAll().size();
        deXuatThanhToan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeXuatThanhToanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deXuatThanhToan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deXuatThanhToan))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeXuatThanhToan in the database
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeXuatThanhToan() throws Exception {
        int databaseSizeBeforeUpdate = deXuatThanhToanRepository.findAll().size();
        deXuatThanhToan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeXuatThanhToanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deXuatThanhToan))
            )
            .andExpect(status().isBadRequest());

        // Validate the DeXuatThanhToan in the database
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeXuatThanhToan() throws Exception {
        int databaseSizeBeforeUpdate = deXuatThanhToanRepository.findAll().size();
        deXuatThanhToan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeXuatThanhToanMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deXuatThanhToan))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DeXuatThanhToan in the database
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeXuatThanhToan() throws Exception {
        // Initialize the database
        deXuatThanhToanRepository.saveAndFlush(deXuatThanhToan);

        int databaseSizeBeforeDelete = deXuatThanhToanRepository.findAll().size();

        // Delete the deXuatThanhToan
        restDeXuatThanhToanMockMvc
            .perform(delete(ENTITY_API_URL_ID, deXuatThanhToan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeXuatThanhToan> deXuatThanhToanList = deXuatThanhToanRepository.findAll();
        assertThat(deXuatThanhToanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
