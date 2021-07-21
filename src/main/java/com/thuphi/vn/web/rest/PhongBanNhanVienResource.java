package com.thuphi.vn.web.rest;

import com.thuphi.vn.domain.PhongBanNhanVien;
import com.thuphi.vn.repository.PhongBanNhanVienRepository;
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
 * REST controller for managing {@link com.thuphi.vn.domain.PhongBanNhanVien}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PhongBanNhanVienResource {

    private final Logger log = LoggerFactory.getLogger(PhongBanNhanVienResource.class);

    private static final String ENTITY_NAME = "phongBanNhanVien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhongBanNhanVienRepository phongBanNhanVienRepository;

    public PhongBanNhanVienResource(PhongBanNhanVienRepository phongBanNhanVienRepository) {
        this.phongBanNhanVienRepository = phongBanNhanVienRepository;
    }

    /**
     * {@code POST  /phong-ban-nhan-viens} : Create a new phongBanNhanVien.
     *
     * @param phongBanNhanVien the phongBanNhanVien to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phongBanNhanVien, or with status {@code 400 (Bad Request)} if the phongBanNhanVien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phong-ban-nhan-viens")
    public ResponseEntity<PhongBanNhanVien> createPhongBanNhanVien(@RequestBody PhongBanNhanVien phongBanNhanVien)
        throws URISyntaxException {
        log.debug("REST request to save PhongBanNhanVien : {}", phongBanNhanVien);
        if (phongBanNhanVien.getId() != null) {
            throw new BadRequestAlertException("A new phongBanNhanVien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhongBanNhanVien result = phongBanNhanVienRepository.save(phongBanNhanVien);
        return ResponseEntity
            .created(new URI("/api/phong-ban-nhan-viens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phong-ban-nhan-viens/:id} : Updates an existing phongBanNhanVien.
     *
     * @param id the id of the phongBanNhanVien to save.
     * @param phongBanNhanVien the phongBanNhanVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongBanNhanVien,
     * or with status {@code 400 (Bad Request)} if the phongBanNhanVien is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phongBanNhanVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phong-ban-nhan-viens/{id}")
    public ResponseEntity<PhongBanNhanVien> updatePhongBanNhanVien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PhongBanNhanVien phongBanNhanVien
    ) throws URISyntaxException {
        log.debug("REST request to update PhongBanNhanVien : {}, {}", id, phongBanNhanVien);
        if (phongBanNhanVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongBanNhanVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongBanNhanVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PhongBanNhanVien result = phongBanNhanVienRepository.save(phongBanNhanVien);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, phongBanNhanVien.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /phong-ban-nhan-viens/:id} : Partial updates given fields of an existing phongBanNhanVien, field will ignore if it is null
     *
     * @param id the id of the phongBanNhanVien to save.
     * @param phongBanNhanVien the phongBanNhanVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongBanNhanVien,
     * or with status {@code 400 (Bad Request)} if the phongBanNhanVien is not valid,
     * or with status {@code 404 (Not Found)} if the phongBanNhanVien is not found,
     * or with status {@code 500 (Internal Server Error)} if the phongBanNhanVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/phong-ban-nhan-viens/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PhongBanNhanVien> partialUpdatePhongBanNhanVien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PhongBanNhanVien phongBanNhanVien
    ) throws URISyntaxException {
        log.debug("REST request to partial update PhongBanNhanVien partially : {}, {}", id, phongBanNhanVien);
        if (phongBanNhanVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongBanNhanVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongBanNhanVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PhongBanNhanVien> result = phongBanNhanVienRepository
            .findById(phongBanNhanVien.getId())
            .map(
                existingPhongBanNhanVien -> {
                    if (phongBanNhanVien.getMaPhong() != null) {
                        existingPhongBanNhanVien.setMaPhong(phongBanNhanVien.getMaPhong());
                    }
                    if (phongBanNhanVien.getMaNhanVien() != null) {
                        existingPhongBanNhanVien.setMaNhanVien(phongBanNhanVien.getMaNhanVien());
                    }

                    return existingPhongBanNhanVien;
                }
            )
            .map(phongBanNhanVienRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, phongBanNhanVien.getId().toString())
        );
    }

    /**
     * {@code GET  /phong-ban-nhan-viens} : get all the phongBanNhanViens.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phongBanNhanViens in body.
     */
    @GetMapping("/phong-ban-nhan-viens")
    public List<PhongBanNhanVien> getAllPhongBanNhanViens(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all PhongBanNhanViens");
        return phongBanNhanVienRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /phong-ban-nhan-viens/:id} : get the "id" phongBanNhanVien.
     *
     * @param id the id of the phongBanNhanVien to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phongBanNhanVien, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phong-ban-nhan-viens/{id}")
    public ResponseEntity<PhongBanNhanVien> getPhongBanNhanVien(@PathVariable Long id) {
        log.debug("REST request to get PhongBanNhanVien : {}", id);
        Optional<PhongBanNhanVien> phongBanNhanVien = phongBanNhanVienRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(phongBanNhanVien);
    }

    /**
     * {@code DELETE  /phong-ban-nhan-viens/:id} : delete the "id" phongBanNhanVien.
     *
     * @param id the id of the phongBanNhanVien to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phong-ban-nhan-viens/{id}")
    public ResponseEntity<Void> deletePhongBanNhanVien(@PathVariable Long id) {
        log.debug("REST request to delete PhongBanNhanVien : {}", id);
        phongBanNhanVienRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
