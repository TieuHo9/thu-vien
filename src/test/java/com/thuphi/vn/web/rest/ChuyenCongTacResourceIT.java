package com.thuphi.vn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.thuphi.vn.IntegrationTest;
import com.thuphi.vn.domain.ChuyenCongTac;
import com.thuphi.vn.repository.ChuyenCongTacRepository;
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
 * Integration tests for the {@link ChuyenCongTacResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ChuyenCongTacResourceIT {

    private static final String DEFAULT_MA_CHUYEN_DI = "AAAAAAAAAA";
    private static final String UPDATED_MA_CHUYEN_DI = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_CHUYEN_DI = "AAAAAAAAAA";
    private static final String UPDATED_TEN_CHUYEN_DI = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_THOI_GIAN_TU = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THOI_GIAN_TU = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THOI_GIAN_DEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THOI_GIAN_DEN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MA_NHAN_VIEN = "AAAAAAAAAA";
    private static final String UPDATED_MA_NHAN_VIEN = "BBBBBBBBBB";

    private static final String DEFAULT_TEN_NHAN_VIEN = "AAAAAAAAAA";
    private static final String UPDATED_TEN_NHAN_VIEN = "BBBBBBBBBB";

    private static final String DEFAULT_SO_DIEN_THOAI = "AAAAAAAAAA";
    private static final String UPDATED_SO_DIEN_THOAI = "BBBBBBBBBB";

    private static final String DEFAULT_DIA_DIEM = "AAAAAAAAAA";
    private static final String UPDATED_DIA_DIEM = "BBBBBBBBBB";

    private static final String DEFAULT_SO_TIEN = "AAAAAAAAAA";
    private static final String UPDATED_SO_TIEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/chuyen-cong-tacs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChuyenCongTacRepository chuyenCongTacRepository;

    @Mock
    private ChuyenCongTacRepository chuyenCongTacRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChuyenCongTacMockMvc;

    private ChuyenCongTac chuyenCongTac;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChuyenCongTac createEntity(EntityManager em) {
        ChuyenCongTac chuyenCongTac = new ChuyenCongTac()
            .maChuyenDi(DEFAULT_MA_CHUYEN_DI)
            .tenChuyenDi(DEFAULT_TEN_CHUYEN_DI)
            .thoiGianTu(DEFAULT_THOI_GIAN_TU)
            .thoiGianDen(DEFAULT_THOI_GIAN_DEN)
            .maNhanVien(DEFAULT_MA_NHAN_VIEN)
            .tenNhanVien(DEFAULT_TEN_NHAN_VIEN)
            .soDienThoai(DEFAULT_SO_DIEN_THOAI)
            .diaDiem(DEFAULT_DIA_DIEM)
            .soTien(DEFAULT_SO_TIEN);
        return chuyenCongTac;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChuyenCongTac createUpdatedEntity(EntityManager em) {
        ChuyenCongTac chuyenCongTac = new ChuyenCongTac()
            .maChuyenDi(UPDATED_MA_CHUYEN_DI)
            .tenChuyenDi(UPDATED_TEN_CHUYEN_DI)
            .thoiGianTu(UPDATED_THOI_GIAN_TU)
            .thoiGianDen(UPDATED_THOI_GIAN_DEN)
            .maNhanVien(UPDATED_MA_NHAN_VIEN)
            .tenNhanVien(UPDATED_TEN_NHAN_VIEN)
            .soDienThoai(UPDATED_SO_DIEN_THOAI)
            .diaDiem(UPDATED_DIA_DIEM)
            .soTien(UPDATED_SO_TIEN);
        return chuyenCongTac;
    }

    @BeforeEach
    public void initTest() {
        chuyenCongTac = createEntity(em);
    }

    @Test
    @Transactional
    void createChuyenCongTac() throws Exception {
        int databaseSizeBeforeCreate = chuyenCongTacRepository.findAll().size();
        // Create the ChuyenCongTac
        restChuyenCongTacMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chuyenCongTac)))
            .andExpect(status().isCreated());

        // Validate the ChuyenCongTac in the database
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeCreate + 1);
        ChuyenCongTac testChuyenCongTac = chuyenCongTacList.get(chuyenCongTacList.size() - 1);
        assertThat(testChuyenCongTac.getMaChuyenDi()).isEqualTo(DEFAULT_MA_CHUYEN_DI);
        assertThat(testChuyenCongTac.getTenChuyenDi()).isEqualTo(DEFAULT_TEN_CHUYEN_DI);
        assertThat(testChuyenCongTac.getThoiGianTu()).isEqualTo(DEFAULT_THOI_GIAN_TU);
        assertThat(testChuyenCongTac.getThoiGianDen()).isEqualTo(DEFAULT_THOI_GIAN_DEN);
        assertThat(testChuyenCongTac.getMaNhanVien()).isEqualTo(DEFAULT_MA_NHAN_VIEN);
        assertThat(testChuyenCongTac.getTenNhanVien()).isEqualTo(DEFAULT_TEN_NHAN_VIEN);
        assertThat(testChuyenCongTac.getSoDienThoai()).isEqualTo(DEFAULT_SO_DIEN_THOAI);
        assertThat(testChuyenCongTac.getDiaDiem()).isEqualTo(DEFAULT_DIA_DIEM);
        assertThat(testChuyenCongTac.getSoTien()).isEqualTo(DEFAULT_SO_TIEN);
    }

    @Test
    @Transactional
    void createChuyenCongTacWithExistingId() throws Exception {
        // Create the ChuyenCongTac with an existing ID
        chuyenCongTac.setId(1L);

        int databaseSizeBeforeCreate = chuyenCongTacRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChuyenCongTacMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chuyenCongTac)))
            .andExpect(status().isBadRequest());

        // Validate the ChuyenCongTac in the database
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChuyenCongTacs() throws Exception {
        // Initialize the database
        chuyenCongTacRepository.saveAndFlush(chuyenCongTac);

        // Get all the chuyenCongTacList
        restChuyenCongTacMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chuyenCongTac.getId().intValue())))
            .andExpect(jsonPath("$.[*].maChuyenDi").value(hasItem(DEFAULT_MA_CHUYEN_DI)))
            .andExpect(jsonPath("$.[*].tenChuyenDi").value(hasItem(DEFAULT_TEN_CHUYEN_DI)))
            .andExpect(jsonPath("$.[*].thoiGianTu").value(hasItem(DEFAULT_THOI_GIAN_TU.toString())))
            .andExpect(jsonPath("$.[*].thoiGianDen").value(hasItem(DEFAULT_THOI_GIAN_DEN.toString())))
            .andExpect(jsonPath("$.[*].maNhanVien").value(hasItem(DEFAULT_MA_NHAN_VIEN)))
            .andExpect(jsonPath("$.[*].tenNhanVien").value(hasItem(DEFAULT_TEN_NHAN_VIEN)))
            .andExpect(jsonPath("$.[*].soDienThoai").value(hasItem(DEFAULT_SO_DIEN_THOAI)))
            .andExpect(jsonPath("$.[*].diaDiem").value(hasItem(DEFAULT_DIA_DIEM)))
            .andExpect(jsonPath("$.[*].soTien").value(hasItem(DEFAULT_SO_TIEN)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllChuyenCongTacsWithEagerRelationshipsIsEnabled() throws Exception {
        when(chuyenCongTacRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChuyenCongTacMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(chuyenCongTacRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllChuyenCongTacsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(chuyenCongTacRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChuyenCongTacMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(chuyenCongTacRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getChuyenCongTac() throws Exception {
        // Initialize the database
        chuyenCongTacRepository.saveAndFlush(chuyenCongTac);

        // Get the chuyenCongTac
        restChuyenCongTacMockMvc
            .perform(get(ENTITY_API_URL_ID, chuyenCongTac.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chuyenCongTac.getId().intValue()))
            .andExpect(jsonPath("$.maChuyenDi").value(DEFAULT_MA_CHUYEN_DI))
            .andExpect(jsonPath("$.tenChuyenDi").value(DEFAULT_TEN_CHUYEN_DI))
            .andExpect(jsonPath("$.thoiGianTu").value(DEFAULT_THOI_GIAN_TU.toString()))
            .andExpect(jsonPath("$.thoiGianDen").value(DEFAULT_THOI_GIAN_DEN.toString()))
            .andExpect(jsonPath("$.maNhanVien").value(DEFAULT_MA_NHAN_VIEN))
            .andExpect(jsonPath("$.tenNhanVien").value(DEFAULT_TEN_NHAN_VIEN))
            .andExpect(jsonPath("$.soDienThoai").value(DEFAULT_SO_DIEN_THOAI))
            .andExpect(jsonPath("$.diaDiem").value(DEFAULT_DIA_DIEM))
            .andExpect(jsonPath("$.soTien").value(DEFAULT_SO_TIEN));
    }

    @Test
    @Transactional
    void getNonExistingChuyenCongTac() throws Exception {
        // Get the chuyenCongTac
        restChuyenCongTacMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChuyenCongTac() throws Exception {
        // Initialize the database
        chuyenCongTacRepository.saveAndFlush(chuyenCongTac);

        int databaseSizeBeforeUpdate = chuyenCongTacRepository.findAll().size();

        // Update the chuyenCongTac
        ChuyenCongTac updatedChuyenCongTac = chuyenCongTacRepository.findById(chuyenCongTac.getId()).get();
        // Disconnect from session so that the updates on updatedChuyenCongTac are not directly saved in db
        em.detach(updatedChuyenCongTac);
        updatedChuyenCongTac
            .maChuyenDi(UPDATED_MA_CHUYEN_DI)
            .tenChuyenDi(UPDATED_TEN_CHUYEN_DI)
            .thoiGianTu(UPDATED_THOI_GIAN_TU)
            .thoiGianDen(UPDATED_THOI_GIAN_DEN)
            .maNhanVien(UPDATED_MA_NHAN_VIEN)
            .tenNhanVien(UPDATED_TEN_NHAN_VIEN)
            .soDienThoai(UPDATED_SO_DIEN_THOAI)
            .diaDiem(UPDATED_DIA_DIEM)
            .soTien(UPDATED_SO_TIEN);

        restChuyenCongTacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChuyenCongTac.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChuyenCongTac))
            )
            .andExpect(status().isOk());

        // Validate the ChuyenCongTac in the database
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeUpdate);
        ChuyenCongTac testChuyenCongTac = chuyenCongTacList.get(chuyenCongTacList.size() - 1);
        assertThat(testChuyenCongTac.getMaChuyenDi()).isEqualTo(UPDATED_MA_CHUYEN_DI);
        assertThat(testChuyenCongTac.getTenChuyenDi()).isEqualTo(UPDATED_TEN_CHUYEN_DI);
        assertThat(testChuyenCongTac.getThoiGianTu()).isEqualTo(UPDATED_THOI_GIAN_TU);
        assertThat(testChuyenCongTac.getThoiGianDen()).isEqualTo(UPDATED_THOI_GIAN_DEN);
        assertThat(testChuyenCongTac.getMaNhanVien()).isEqualTo(UPDATED_MA_NHAN_VIEN);
        assertThat(testChuyenCongTac.getTenNhanVien()).isEqualTo(UPDATED_TEN_NHAN_VIEN);
        assertThat(testChuyenCongTac.getSoDienThoai()).isEqualTo(UPDATED_SO_DIEN_THOAI);
        assertThat(testChuyenCongTac.getDiaDiem()).isEqualTo(UPDATED_DIA_DIEM);
        assertThat(testChuyenCongTac.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
    }

    @Test
    @Transactional
    void putNonExistingChuyenCongTac() throws Exception {
        int databaseSizeBeforeUpdate = chuyenCongTacRepository.findAll().size();
        chuyenCongTac.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChuyenCongTacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chuyenCongTac.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chuyenCongTac))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenCongTac in the database
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChuyenCongTac() throws Exception {
        int databaseSizeBeforeUpdate = chuyenCongTacRepository.findAll().size();
        chuyenCongTac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenCongTacMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chuyenCongTac))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenCongTac in the database
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChuyenCongTac() throws Exception {
        int databaseSizeBeforeUpdate = chuyenCongTacRepository.findAll().size();
        chuyenCongTac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenCongTacMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chuyenCongTac)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChuyenCongTac in the database
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChuyenCongTacWithPatch() throws Exception {
        // Initialize the database
        chuyenCongTacRepository.saveAndFlush(chuyenCongTac);

        int databaseSizeBeforeUpdate = chuyenCongTacRepository.findAll().size();

        // Update the chuyenCongTac using partial update
        ChuyenCongTac partialUpdatedChuyenCongTac = new ChuyenCongTac();
        partialUpdatedChuyenCongTac.setId(chuyenCongTac.getId());

        partialUpdatedChuyenCongTac
            .maChuyenDi(UPDATED_MA_CHUYEN_DI)
            .tenChuyenDi(UPDATED_TEN_CHUYEN_DI)
            .thoiGianTu(UPDATED_THOI_GIAN_TU)
            .thoiGianDen(UPDATED_THOI_GIAN_DEN)
            .maNhanVien(UPDATED_MA_NHAN_VIEN)
            .tenNhanVien(UPDATED_TEN_NHAN_VIEN)
            .soDienThoai(UPDATED_SO_DIEN_THOAI)
            .soTien(UPDATED_SO_TIEN);

        restChuyenCongTacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChuyenCongTac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChuyenCongTac))
            )
            .andExpect(status().isOk());

        // Validate the ChuyenCongTac in the database
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeUpdate);
        ChuyenCongTac testChuyenCongTac = chuyenCongTacList.get(chuyenCongTacList.size() - 1);
        assertThat(testChuyenCongTac.getMaChuyenDi()).isEqualTo(UPDATED_MA_CHUYEN_DI);
        assertThat(testChuyenCongTac.getTenChuyenDi()).isEqualTo(UPDATED_TEN_CHUYEN_DI);
        assertThat(testChuyenCongTac.getThoiGianTu()).isEqualTo(UPDATED_THOI_GIAN_TU);
        assertThat(testChuyenCongTac.getThoiGianDen()).isEqualTo(UPDATED_THOI_GIAN_DEN);
        assertThat(testChuyenCongTac.getMaNhanVien()).isEqualTo(UPDATED_MA_NHAN_VIEN);
        assertThat(testChuyenCongTac.getTenNhanVien()).isEqualTo(UPDATED_TEN_NHAN_VIEN);
        assertThat(testChuyenCongTac.getSoDienThoai()).isEqualTo(UPDATED_SO_DIEN_THOAI);
        assertThat(testChuyenCongTac.getDiaDiem()).isEqualTo(DEFAULT_DIA_DIEM);
        assertThat(testChuyenCongTac.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
    }

    @Test
    @Transactional
    void fullUpdateChuyenCongTacWithPatch() throws Exception {
        // Initialize the database
        chuyenCongTacRepository.saveAndFlush(chuyenCongTac);

        int databaseSizeBeforeUpdate = chuyenCongTacRepository.findAll().size();

        // Update the chuyenCongTac using partial update
        ChuyenCongTac partialUpdatedChuyenCongTac = new ChuyenCongTac();
        partialUpdatedChuyenCongTac.setId(chuyenCongTac.getId());

        partialUpdatedChuyenCongTac
            .maChuyenDi(UPDATED_MA_CHUYEN_DI)
            .tenChuyenDi(UPDATED_TEN_CHUYEN_DI)
            .thoiGianTu(UPDATED_THOI_GIAN_TU)
            .thoiGianDen(UPDATED_THOI_GIAN_DEN)
            .maNhanVien(UPDATED_MA_NHAN_VIEN)
            .tenNhanVien(UPDATED_TEN_NHAN_VIEN)
            .soDienThoai(UPDATED_SO_DIEN_THOAI)
            .diaDiem(UPDATED_DIA_DIEM)
            .soTien(UPDATED_SO_TIEN);

        restChuyenCongTacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChuyenCongTac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChuyenCongTac))
            )
            .andExpect(status().isOk());

        // Validate the ChuyenCongTac in the database
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeUpdate);
        ChuyenCongTac testChuyenCongTac = chuyenCongTacList.get(chuyenCongTacList.size() - 1);
        assertThat(testChuyenCongTac.getMaChuyenDi()).isEqualTo(UPDATED_MA_CHUYEN_DI);
        assertThat(testChuyenCongTac.getTenChuyenDi()).isEqualTo(UPDATED_TEN_CHUYEN_DI);
        assertThat(testChuyenCongTac.getThoiGianTu()).isEqualTo(UPDATED_THOI_GIAN_TU);
        assertThat(testChuyenCongTac.getThoiGianDen()).isEqualTo(UPDATED_THOI_GIAN_DEN);
        assertThat(testChuyenCongTac.getMaNhanVien()).isEqualTo(UPDATED_MA_NHAN_VIEN);
        assertThat(testChuyenCongTac.getTenNhanVien()).isEqualTo(UPDATED_TEN_NHAN_VIEN);
        assertThat(testChuyenCongTac.getSoDienThoai()).isEqualTo(UPDATED_SO_DIEN_THOAI);
        assertThat(testChuyenCongTac.getDiaDiem()).isEqualTo(UPDATED_DIA_DIEM);
        assertThat(testChuyenCongTac.getSoTien()).isEqualTo(UPDATED_SO_TIEN);
    }

    @Test
    @Transactional
    void patchNonExistingChuyenCongTac() throws Exception {
        int databaseSizeBeforeUpdate = chuyenCongTacRepository.findAll().size();
        chuyenCongTac.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChuyenCongTacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chuyenCongTac.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chuyenCongTac))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenCongTac in the database
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChuyenCongTac() throws Exception {
        int databaseSizeBeforeUpdate = chuyenCongTacRepository.findAll().size();
        chuyenCongTac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenCongTacMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chuyenCongTac))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenCongTac in the database
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChuyenCongTac() throws Exception {
        int databaseSizeBeforeUpdate = chuyenCongTacRepository.findAll().size();
        chuyenCongTac.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenCongTacMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chuyenCongTac))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChuyenCongTac in the database
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChuyenCongTac() throws Exception {
        // Initialize the database
        chuyenCongTacRepository.saveAndFlush(chuyenCongTac);

        int databaseSizeBeforeDelete = chuyenCongTacRepository.findAll().size();

        // Delete the chuyenCongTac
        restChuyenCongTacMockMvc
            .perform(delete(ENTITY_API_URL_ID, chuyenCongTac.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChuyenCongTac> chuyenCongTacList = chuyenCongTacRepository.findAll();
        assertThat(chuyenCongTacList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
