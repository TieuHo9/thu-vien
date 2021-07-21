package com.thuphi.vn.web.rest;

import com.thuphi.vn.domain.DeXuatThanhToan;
import com.thuphi.vn.repository.DeXuatThanhToanRepository;
import com.thuphi.vn.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.thuphi.vn.domain.DeXuatThanhToan}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DeXuatThanhToanResource {

    private final Logger log = LoggerFactory.getLogger(DeXuatThanhToanResource.class);

    private static final String ENTITY_NAME = "deXuatThanhToan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeXuatThanhToanRepository deXuatThanhToanRepository;

    public DeXuatThanhToanResource(DeXuatThanhToanRepository deXuatThanhToanRepository) {
        this.deXuatThanhToanRepository = deXuatThanhToanRepository;
    }

    /**
     * {@code POST  /de-xuat-thanh-toans} : Create a new deXuatThanhToan.
     *
     * @param deXuatThanhToan the deXuatThanhToan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deXuatThanhToan, or with status {@code 400 (Bad Request)} if the deXuatThanhToan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/de-xuat-thanh-toans")
    public ResponseEntity<DeXuatThanhToan> createDeXuatThanhToan(@RequestBody DeXuatThanhToan deXuatThanhToan) throws URISyntaxException {
        log.debug("REST request to save DeXuatThanhToan : {}", deXuatThanhToan);
        if (deXuatThanhToan.getId() != null) {
            throw new BadRequestAlertException("A new deXuatThanhToan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeXuatThanhToan result = deXuatThanhToanRepository.save(deXuatThanhToan);
        return ResponseEntity
            .created(new URI("/api/de-xuat-thanh-toans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /de-xuat-thanh-toans/:id} : Updates an existing deXuatThanhToan.
     *
     * @param id the id of the deXuatThanhToan to save.
     * @param deXuatThanhToan the deXuatThanhToan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deXuatThanhToan,
     * or with status {@code 400 (Bad Request)} if the deXuatThanhToan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deXuatThanhToan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/de-xuat-thanh-toans/{id}")
    public ResponseEntity<DeXuatThanhToan> updateDeXuatThanhToan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeXuatThanhToan deXuatThanhToan
    ) throws URISyntaxException {
        log.debug("REST request to update DeXuatThanhToan : {}, {}", id, deXuatThanhToan);
        if (deXuatThanhToan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deXuatThanhToan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deXuatThanhToanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DeXuatThanhToan result = deXuatThanhToanRepository.save(deXuatThanhToan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deXuatThanhToan.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /de-xuat-thanh-toans/:id} : Partial updates given fields of an existing deXuatThanhToan, field will ignore if it is null
     *
     * @param id the id of the deXuatThanhToan to save.
     * @param deXuatThanhToan the deXuatThanhToan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deXuatThanhToan,
     * or with status {@code 400 (Bad Request)} if the deXuatThanhToan is not valid,
     * or with status {@code 404 (Not Found)} if the deXuatThanhToan is not found,
     * or with status {@code 500 (Internal Server Error)} if the deXuatThanhToan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/de-xuat-thanh-toans/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DeXuatThanhToan> partialUpdateDeXuatThanhToan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeXuatThanhToan deXuatThanhToan
    ) throws URISyntaxException {
        log.debug("REST request to partial update DeXuatThanhToan partially : {}, {}", id, deXuatThanhToan);
        if (deXuatThanhToan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deXuatThanhToan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deXuatThanhToanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeXuatThanhToan> result = deXuatThanhToanRepository
            .findById(deXuatThanhToan.getId())
            .map(
                existingDeXuatThanhToan -> {
                    if (deXuatThanhToan.getMaDeXuat() != null) {
                        existingDeXuatThanhToan.setMaDeXuat(deXuatThanhToan.getMaDeXuat());
                    }
                    if (deXuatThanhToan.getTenDeXuat() != null) {
                        existingDeXuatThanhToan.setTenDeXuat(deXuatThanhToan.getTenDeXuat());
                    }
                    if (deXuatThanhToan.getTuNgay() != null) {
                        existingDeXuatThanhToan.setTuNgay(deXuatThanhToan.getTuNgay());
                    }
                    if (deXuatThanhToan.getDenNgay() != null) {
                        existingDeXuatThanhToan.setDenNgay(deXuatThanhToan.getDenNgay());
                    }
                    if (deXuatThanhToan.getTrangThaiTruongPhong() != null) {
                        existingDeXuatThanhToan.setTrangThaiTruongPhong(deXuatThanhToan.getTrangThaiTruongPhong());
                    }
                    if (deXuatThanhToan.getTrangThaiPhongTaiVu() != null) {
                        existingDeXuatThanhToan.setTrangThaiPhongTaiVu(deXuatThanhToan.getTrangThaiPhongTaiVu());
                    }
                    if (deXuatThanhToan.getTrangThaiBanLanhDao() != null) {
                        existingDeXuatThanhToan.setTrangThaiBanLanhDao(deXuatThanhToan.getTrangThaiBanLanhDao());
                    }
                    if (deXuatThanhToan.getThanhToan() != null) {
                        existingDeXuatThanhToan.setThanhToan(deXuatThanhToan.getThanhToan());
                    }

                    return existingDeXuatThanhToan;
                }
            )
            .map(deXuatThanhToanRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deXuatThanhToan.getId().toString())
        );
    }

    /**
     * {@code GET  /de-xuat-thanh-toans} : get all the deXuatThanhToans.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deXuatThanhToans in body.
     */
    @GetMapping("/de-xuat-thanh-toans")
    public List<DeXuatThanhToan> getAllDeXuatThanhToans(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all DeXuatThanhToans");
        return deXuatThanhToanRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /de-xuat-thanh-toans/:id} : get the "id" deXuatThanhToan.
     *
     * @param id the id of the deXuatThanhToan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deXuatThanhToan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/de-xuat-thanh-toans/{id}")
    public ResponseEntity<DeXuatThanhToan> getDeXuatThanhToan(@PathVariable Long id) {
        log.debug("REST request to get DeXuatThanhToan : {}", id);
        Optional<DeXuatThanhToan> deXuatThanhToan = deXuatThanhToanRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(deXuatThanhToan);
    }

    /**
     * {@code DELETE  /de-xuat-thanh-toans/:id} : delete the "id" deXuatThanhToan.
     *
     * @param id the id of the deXuatThanhToan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/de-xuat-thanh-toans/{id}")
    public ResponseEntity<Void> deleteDeXuatThanhToan(@PathVariable Long id) {
        log.debug("REST request to delete DeXuatThanhToan : {}", id);
        deXuatThanhToanRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/de-xuat-thanh-toans/name/{tenDeXuat}")
    public List<DeXuatThanhToan> getDeXuatThanhToan(@PathVariable String tenDeXuat) {
        log.info("REST request to find By DeXuatThanhToan : {} {}", tenDeXuat, deXuatThanhToanRepository.findByTenDeXuat(tenDeXuat));
        return deXuatThanhToanRepository.findByTenDeXuat(tenDeXuat);
    }

    @GetMapping("/de-xuat-thanh-toans/thanhtoan/")
    public List<DeXuatThanhToan> getDeXuatThanhToanByThanhToan() {
        return deXuatThanhToanRepository.findByThanhToan("Da thanh toan");
    }
}
