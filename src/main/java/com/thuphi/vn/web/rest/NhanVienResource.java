package com.thuphi.vn.web.rest;

import com.thuphi.vn.domain.NhanVien;
import com.thuphi.vn.repository.NhanVienRepository;
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
 * REST controller for managing {@link com.thuphi.vn.domain.NhanVien}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NhanVienResource {

    private final Logger log = LoggerFactory.getLogger(NhanVienResource.class);

    private static final String ENTITY_NAME = "nhanVien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NhanVienRepository nhanVienRepository;

    public NhanVienResource(NhanVienRepository nhanVienRepository) {
        this.nhanVienRepository = nhanVienRepository;
    }

    /**
     * {@code POST  /nhan-viens} : Create a new nhanVien.
     *
     * @param nhanVien the nhanVien to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nhanVien, or with status {@code 400 (Bad Request)} if the nhanVien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nhan-viens")
    public ResponseEntity<NhanVien> createNhanVien(@RequestBody NhanVien nhanVien) throws URISyntaxException {
        log.debug("REST request to save NhanVien : {}", nhanVien);
        if (nhanVien.getId() != null) {
            throw new BadRequestAlertException("A new nhanVien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhanVien result = nhanVienRepository.save(nhanVien);
        return ResponseEntity
            .created(new URI("/api/nhan-viens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nhan-viens/:id} : Updates an existing nhanVien.
     *
     * @param id the id of the nhanVien to save.
     * @param nhanVien the nhanVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhanVien,
     * or with status {@code 400 (Bad Request)} if the nhanVien is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nhanVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nhan-viens/{id}")
    public ResponseEntity<NhanVien> updateNhanVien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NhanVien nhanVien
    ) throws URISyntaxException {
        log.debug("REST request to update NhanVien : {}, {}", id, nhanVien);
        if (nhanVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhanVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhanVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NhanVien result = nhanVienRepository.save(nhanVien);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nhanVien.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nhan-viens/:id} : Partial updates given fields of an existing nhanVien, field will ignore if it is null
     *
     * @param id the id of the nhanVien to save.
     * @param nhanVien the nhanVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhanVien,
     * or with status {@code 400 (Bad Request)} if the nhanVien is not valid,
     * or with status {@code 404 (Not Found)} if the nhanVien is not found,
     * or with status {@code 500 (Internal Server Error)} if the nhanVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nhan-viens/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<NhanVien> partialUpdateNhanVien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NhanVien nhanVien
    ) throws URISyntaxException {
        log.debug("REST request to partial update NhanVien partially : {}, {}", id, nhanVien);
        if (nhanVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhanVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhanVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NhanVien> result = nhanVienRepository
            .findById(nhanVien.getId())
            .map(
                existingNhanVien -> {
                    if (nhanVien.getMaNhanVien() != null) {
                        existingNhanVien.setMaNhanVien(nhanVien.getMaNhanVien());
                    }
                    if (nhanVien.getTenNhanVien() != null) {
                        existingNhanVien.setTenNhanVien(nhanVien.getTenNhanVien());
                    }
                    if (nhanVien.getPhongBan() != null) {
                        existingNhanVien.setPhongBan(nhanVien.getPhongBan());
                    }
                    if (nhanVien.getCapBac() != null) {
                        existingNhanVien.setCapBac(nhanVien.getCapBac());
                    }

                    return existingNhanVien;
                }
            )
            .map(nhanVienRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nhanVien.getId().toString())
        );
    }

    /**
     * {@code GET  /nhan-viens} : get all the nhanViens.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nhanViens in body.
     */
    @GetMapping("/nhan-viens")
    public List<NhanVien> getAllNhanViens(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all NhanViens");
        return nhanVienRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /nhan-viens/:id} : get the "id" nhanVien.
     *
     * @param id the id of the nhanVien to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nhanVien, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nhan-viens/{id}")
    public ResponseEntity<NhanVien> getNhanVien(@PathVariable Long id) {
        log.debug("REST request to get NhanVien : {}", id);
        Optional<NhanVien> nhanVien = nhanVienRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(nhanVien);
    }

    /**
     * {@code DELETE  /nhan-viens/:id} : delete the "id" nhanVien.
     *
     * @param id the id of the nhanVien to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nhan-viens/{id}")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable Long id) {
        log.debug("REST request to delete NhanVien : {}", id);
        nhanVienRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
