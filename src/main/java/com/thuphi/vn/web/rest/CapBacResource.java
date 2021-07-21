package com.thuphi.vn.web.rest;

import com.thuphi.vn.domain.CapBac;
import com.thuphi.vn.repository.CapBacRepository;
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
 * REST controller for managing {@link com.thuphi.vn.domain.CapBac}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CapBacResource {

    private final Logger log = LoggerFactory.getLogger(CapBacResource.class);

    private static final String ENTITY_NAME = "capBac";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CapBacRepository capBacRepository;

    public CapBacResource(CapBacRepository capBacRepository) {
        this.capBacRepository = capBacRepository;
    }

    /**
     * {@code POST  /cap-bacs} : Create a new capBac.
     *
     * @param capBac the capBac to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new capBac, or with status {@code 400 (Bad Request)} if the capBac has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cap-bacs")
    public ResponseEntity<CapBac> createCapBac(@RequestBody CapBac capBac) throws URISyntaxException {
        log.debug("REST request to save CapBac : {}", capBac);
        if (capBac.getId() != null) {
            throw new BadRequestAlertException("A new capBac cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapBac result = capBacRepository.save(capBac);
        return ResponseEntity
            .created(new URI("/api/cap-bacs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cap-bacs/:id} : Updates an existing capBac.
     *
     * @param id the id of the capBac to save.
     * @param capBac the capBac to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated capBac,
     * or with status {@code 400 (Bad Request)} if the capBac is not valid,
     * or with status {@code 500 (Internal Server Error)} if the capBac couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cap-bacs/{id}")
    public ResponseEntity<CapBac> updateCapBac(@PathVariable(value = "id", required = false) final Long id, @RequestBody CapBac capBac)
        throws URISyntaxException {
        log.debug("REST request to update CapBac : {}, {}", id, capBac);
        if (capBac.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, capBac.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!capBacRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CapBac result = capBacRepository.save(capBac);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, capBac.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cap-bacs/:id} : Partial updates given fields of an existing capBac, field will ignore if it is null
     *
     * @param id the id of the capBac to save.
     * @param capBac the capBac to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated capBac,
     * or with status {@code 400 (Bad Request)} if the capBac is not valid,
     * or with status {@code 404 (Not Found)} if the capBac is not found,
     * or with status {@code 500 (Internal Server Error)} if the capBac couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cap-bacs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CapBac> partialUpdateCapBac(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CapBac capBac
    ) throws URISyntaxException {
        log.debug("REST request to partial update CapBac partially : {}, {}", id, capBac);
        if (capBac.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, capBac.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!capBacRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CapBac> result = capBacRepository
            .findById(capBac.getId())
            .map(
                existingCapBac -> {
                    if (capBac.getTenCap() != null) {
                        existingCapBac.setTenCap(capBac.getTenCap());
                    }

                    return existingCapBac;
                }
            )
            .map(capBacRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, capBac.getId().toString())
        );
    }

    /**
     * {@code GET  /cap-bacs} : get all the capBacs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of capBacs in body.
     */
    @GetMapping("/cap-bacs")
    public List<CapBac> getAllCapBacs() {
        log.debug("REST request to get all CapBacs");
        return capBacRepository.findAll();
    }

    /**
     * {@code GET  /cap-bacs/:id} : get the "id" capBac.
     *
     * @param id the id of the capBac to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the capBac, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cap-bacs/{id}")
    public ResponseEntity<CapBac> getCapBac(@PathVariable Long id) {
        log.debug("REST request to get CapBac : {}", id);
        Optional<CapBac> capBac = capBacRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(capBac);
    }

    /**
     * {@code DELETE  /cap-bacs/:id} : delete the "id" capBac.
     *
     * @param id the id of the capBac to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cap-bacs/{id}")
    public ResponseEntity<Void> deleteCapBac(@PathVariable Long id) {
        log.debug("REST request to delete CapBac : {}", id);
        capBacRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
