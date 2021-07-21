package com.thuphi.vn.web.rest;

import com.thuphi.vn.domain.NhanVienCongTac;
import com.thuphi.vn.repository.NhanVienCongTacRepository;
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
 * REST controller for managing {@link com.thuphi.vn.domain.NhanVienCongTac}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NhanVienCongTacResource {

    private final Logger log = LoggerFactory.getLogger(NhanVienCongTacResource.class);

    private static final String ENTITY_NAME = "nhanVienCongTac";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NhanVienCongTacRepository nhanVienCongTacRepository;

    public NhanVienCongTacResource(NhanVienCongTacRepository nhanVienCongTacRepository) {
        this.nhanVienCongTacRepository = nhanVienCongTacRepository;
    }

    /**
     * {@code POST  /nhan-vien-cong-tacs} : Create a new nhanVienCongTac.
     *
     * @param nhanVienCongTac the nhanVienCongTac to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nhanVienCongTac, or with status {@code 400 (Bad Request)} if the nhanVienCongTac has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nhan-vien-cong-tacs")
    public ResponseEntity<NhanVienCongTac> createNhanVienCongTac(@RequestBody NhanVienCongTac nhanVienCongTac) throws URISyntaxException {
        log.debug("REST request to save NhanVienCongTac : {}", nhanVienCongTac);
        if (nhanVienCongTac.getId() != null) {
            throw new BadRequestAlertException("A new nhanVienCongTac cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhanVienCongTac result = nhanVienCongTacRepository.save(nhanVienCongTac);
        return ResponseEntity
            .created(new URI("/api/nhan-vien-cong-tacs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nhan-vien-cong-tacs/:id} : Updates an existing nhanVienCongTac.
     *
     * @param id the id of the nhanVienCongTac to save.
     * @param nhanVienCongTac the nhanVienCongTac to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhanVienCongTac,
     * or with status {@code 400 (Bad Request)} if the nhanVienCongTac is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nhanVienCongTac couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nhan-vien-cong-tacs/{id}")
    public ResponseEntity<NhanVienCongTac> updateNhanVienCongTac(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NhanVienCongTac nhanVienCongTac
    ) throws URISyntaxException {
        log.debug("REST request to update NhanVienCongTac : {}, {}", id, nhanVienCongTac);
        if (nhanVienCongTac.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhanVienCongTac.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhanVienCongTacRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NhanVienCongTac result = nhanVienCongTacRepository.save(nhanVienCongTac);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nhanVienCongTac.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nhan-vien-cong-tacs/:id} : Partial updates given fields of an existing nhanVienCongTac, field will ignore if it is null
     *
     * @param id the id of the nhanVienCongTac to save.
     * @param nhanVienCongTac the nhanVienCongTac to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhanVienCongTac,
     * or with status {@code 400 (Bad Request)} if the nhanVienCongTac is not valid,
     * or with status {@code 404 (Not Found)} if the nhanVienCongTac is not found,
     * or with status {@code 500 (Internal Server Error)} if the nhanVienCongTac couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nhan-vien-cong-tacs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<NhanVienCongTac> partialUpdateNhanVienCongTac(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NhanVienCongTac nhanVienCongTac
    ) throws URISyntaxException {
        log.debug("REST request to partial update NhanVienCongTac partially : {}, {}", id, nhanVienCongTac);
        if (nhanVienCongTac.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhanVienCongTac.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhanVienCongTacRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NhanVienCongTac> result = nhanVienCongTacRepository
            .findById(nhanVienCongTac.getId())
            .map(
                existingNhanVienCongTac -> {
                    if (nhanVienCongTac.getMaNhanVien() != null) {
                        existingNhanVienCongTac.setMaNhanVien(nhanVienCongTac.getMaNhanVien());
                    }
                    if (nhanVienCongTac.getMaChuyenDi() != null) {
                        existingNhanVienCongTac.setMaChuyenDi(nhanVienCongTac.getMaChuyenDi());
                    }
                    if (nhanVienCongTac.getSoTien() != null) {
                        existingNhanVienCongTac.setSoTien(nhanVienCongTac.getSoTien());
                    }

                    return existingNhanVienCongTac;
                }
            )
            .map(nhanVienCongTacRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nhanVienCongTac.getId().toString())
        );
    }

    /**
     * {@code GET  /nhan-vien-cong-tacs} : get all the nhanVienCongTacs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nhanVienCongTacs in body.
     */
    @GetMapping("/nhan-vien-cong-tacs")
    public List<NhanVienCongTac> getAllNhanVienCongTacs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all NhanVienCongTacs");
        return nhanVienCongTacRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /nhan-vien-cong-tacs/:id} : get the "id" nhanVienCongTac.
     *
     * @param id the id of the nhanVienCongTac to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nhanVienCongTac, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nhan-vien-cong-tacs/{id}")
    public ResponseEntity<NhanVienCongTac> getNhanVienCongTac(@PathVariable Long id) {
        log.debug("REST request to get NhanVienCongTac : {}", id);
        Optional<NhanVienCongTac> nhanVienCongTac = nhanVienCongTacRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(nhanVienCongTac);
    }

    /**
     * {@code DELETE  /nhan-vien-cong-tacs/:id} : delete the "id" nhanVienCongTac.
     *
     * @param id the id of the nhanVienCongTac to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nhan-vien-cong-tacs/{id}")
    public ResponseEntity<Void> deleteNhanVienCongTac(@PathVariable Long id) {
        log.debug("REST request to delete NhanVienCongTac : {}", id);
        nhanVienCongTacRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
