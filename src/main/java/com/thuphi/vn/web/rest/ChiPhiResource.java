package com.thuphi.vn.web.rest;

import com.thuphi.vn.domain.ChiPhi;
import com.thuphi.vn.repository.ChiPhiRepository;
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
 * REST controller for managing {@link com.thuphi.vn.domain.ChiPhi}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChiPhiResource {

    private final Logger log = LoggerFactory.getLogger(ChiPhiResource.class);

    private static final String ENTITY_NAME = "chiPhi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChiPhiRepository chiPhiRepository;

    public ChiPhiResource(ChiPhiRepository chiPhiRepository) {
        this.chiPhiRepository = chiPhiRepository;
    }

    /**
     * {@code POST  /chi-phis} : Create a new chiPhi.
     *
     * @param chiPhi the chiPhi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chiPhi, or with status {@code 400 (Bad Request)} if the chiPhi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chi-phis")
    public ResponseEntity<ChiPhi> createChiPhi(@RequestBody ChiPhi chiPhi) throws URISyntaxException {
        log.debug("REST request to save ChiPhi : {}", chiPhi);
        if (chiPhi.getId() != null) {
            throw new BadRequestAlertException("A new chiPhi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChiPhi result = chiPhiRepository.save(chiPhi);
        return ResponseEntity
            .created(new URI("/api/chi-phis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chi-phis/:id} : Updates an existing chiPhi.
     *
     * @param id the id of the chiPhi to save.
     * @param chiPhi the chiPhi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiPhi,
     * or with status {@code 400 (Bad Request)} if the chiPhi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chiPhi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chi-phis/{id}")
    public ResponseEntity<ChiPhi> updateChiPhi(@PathVariable(value = "id", required = false) final Long id, @RequestBody ChiPhi chiPhi)
        throws URISyntaxException {
        log.debug("REST request to update ChiPhi : {}, {}", id, chiPhi);
        if (chiPhi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiPhi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiPhiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChiPhi result = chiPhiRepository.save(chiPhi);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chiPhi.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chi-phis/:id} : Partial updates given fields of an existing chiPhi, field will ignore if it is null
     *
     * @param id the id of the chiPhi to save.
     * @param chiPhi the chiPhi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chiPhi,
     * or with status {@code 400 (Bad Request)} if the chiPhi is not valid,
     * or with status {@code 404 (Not Found)} if the chiPhi is not found,
     * or with status {@code 500 (Internal Server Error)} if the chiPhi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chi-phis/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ChiPhi> partialUpdateChiPhi(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChiPhi chiPhi
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChiPhi partially : {}, {}", id, chiPhi);
        if (chiPhi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chiPhi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chiPhiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChiPhi> result = chiPhiRepository
            .findById(chiPhi.getId())
            .map(
                existingChiPhi -> {
                    if (chiPhi.getLoaiChiPhi() != null) {
                        existingChiPhi.setLoaiChiPhi(chiPhi.getLoaiChiPhi());
                    }
                    if (chiPhi.getSoTien() != null) {
                        existingChiPhi.setSoTien(chiPhi.getSoTien());
                    }
                    if (chiPhi.getDonViTienTe() != null) {
                        existingChiPhi.setDonViTienTe(chiPhi.getDonViTienTe());
                    }

                    return existingChiPhi;
                }
            )
            .map(chiPhiRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chiPhi.getId().toString())
        );
    }

    /**
     * {@code GET  /chi-phis} : get all the chiPhis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chiPhis in body.
     */
    @GetMapping("/chi-phis")
    public List<ChiPhi> getAllChiPhis() {
        log.debug("REST request to get all ChiPhis");
        return chiPhiRepository.findAll();
    }

    /**
     * {@code GET  /chi-phis/:id} : get the "id" chiPhi.
     *
     * @param id the id of the chiPhi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chiPhi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chi-phis/{id}")
    public ResponseEntity<ChiPhi> getChiPhi(@PathVariable Long id) {
        log.debug("REST request to get ChiPhi : {}", id);
        Optional<ChiPhi> chiPhi = chiPhiRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chiPhi);
    }

    /**
     * {@code DELETE  /chi-phis/:id} : delete the "id" chiPhi.
     *
     * @param id the id of the chiPhi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chi-phis/{id}")
    public ResponseEntity<Void> deleteChiPhi(@PathVariable Long id) {
        log.debug("REST request to delete ChiPhi : {}", id);
        chiPhiRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
