package com.thuphi.vn.web.rest;

import com.thuphi.vn.domain.DinhMuc;
import com.thuphi.vn.repository.DinhMucRepository;
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
 * REST controller for managing {@link com.thuphi.vn.domain.DinhMuc}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DinhMucResource {

    private final Logger log = LoggerFactory.getLogger(DinhMucResource.class);

    private static final String ENTITY_NAME = "dinhMuc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DinhMucRepository dinhMucRepository;

    public DinhMucResource(DinhMucRepository dinhMucRepository) {
        this.dinhMucRepository = dinhMucRepository;
    }

    /**
     * {@code POST  /dinh-mucs} : Create a new dinhMuc.
     *
     * @param dinhMuc the dinhMuc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dinhMuc, or with status {@code 400 (Bad Request)} if the dinhMuc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dinh-mucs")
    public ResponseEntity<DinhMuc> createDinhMuc(@RequestBody DinhMuc dinhMuc) throws URISyntaxException {
        log.debug("REST request to save DinhMuc : {}", dinhMuc);
        if (dinhMuc.getId() != null) {
            throw new BadRequestAlertException("A new dinhMuc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DinhMuc result = dinhMucRepository.save(dinhMuc);
        return ResponseEntity
            .created(new URI("/api/dinh-mucs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dinh-mucs/:id} : Updates an existing dinhMuc.
     *
     * @param id the id of the dinhMuc to save.
     * @param dinhMuc the dinhMuc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dinhMuc,
     * or with status {@code 400 (Bad Request)} if the dinhMuc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dinhMuc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dinh-mucs/{id}")
    public ResponseEntity<DinhMuc> updateDinhMuc(@PathVariable(value = "id", required = false) final Long id, @RequestBody DinhMuc dinhMuc)
        throws URISyntaxException {
        log.debug("REST request to update DinhMuc : {}, {}", id, dinhMuc);
        if (dinhMuc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dinhMuc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dinhMucRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DinhMuc result = dinhMucRepository.save(dinhMuc);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dinhMuc.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dinh-mucs/:id} : Partial updates given fields of an existing dinhMuc, field will ignore if it is null
     *
     * @param id the id of the dinhMuc to save.
     * @param dinhMuc the dinhMuc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dinhMuc,
     * or with status {@code 400 (Bad Request)} if the dinhMuc is not valid,
     * or with status {@code 404 (Not Found)} if the dinhMuc is not found,
     * or with status {@code 500 (Internal Server Error)} if the dinhMuc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dinh-mucs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DinhMuc> partialUpdateDinhMuc(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DinhMuc dinhMuc
    ) throws URISyntaxException {
        log.debug("REST request to partial update DinhMuc partially : {}, {}", id, dinhMuc);
        if (dinhMuc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dinhMuc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dinhMucRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DinhMuc> result = dinhMucRepository
            .findById(dinhMuc.getId())
            .map(
                existingDinhMuc -> {
                    if (dinhMuc.getMaMuc() != null) {
                        existingDinhMuc.setMaMuc(dinhMuc.getMaMuc());
                    }
                    if (dinhMuc.getLoaiPhi() != null) {
                        existingDinhMuc.setLoaiPhi(dinhMuc.getLoaiPhi());
                    }
                    //                    if (dinhMuc.getSoTien() != null) {
                    //                        existingDinhMuc.setSoTien(dinhMuc.getSoTien());
                    //                    }
                    if (dinhMuc.getCapBac() != null) {
                        existingDinhMuc.setCapBac(dinhMuc.getCapBac());
                    }

                    return existingDinhMuc;
                }
            )
            .map(dinhMucRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dinhMuc.getId().toString())
        );
    }

    /**
     * {@code GET  /dinh-mucs} : get all the dinhMucs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dinhMucs in body.
     */
    @GetMapping("/dinh-mucs")
    public List<DinhMuc> getAllDinhMucs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all DinhMucs");
        return dinhMucRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /dinh-mucs/:id} : get the "id" dinhMuc.
     *
     * @param id the id of the dinhMuc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dinhMuc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dinh-mucs/{id}")
    public ResponseEntity<DinhMuc> getDinhMuc(@PathVariable Long id) {
        log.debug("REST request to get DinhMuc : {}", id);
        Optional<DinhMuc> dinhMuc = dinhMucRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(dinhMuc);
    }

    /**
     * {@code DELETE  /dinh-mucs/:id} : delete the "id" dinhMuc.
     *
     * @param id the id of the dinhMuc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dinh-mucs/{id}")
    public ResponseEntity<Void> deleteDinhMuc(@PathVariable Long id) {
        log.debug("REST request to delete DinhMuc : {}", id);
        dinhMucRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
