package com.thuphi.vn.web.rest;

import com.thuphi.vn.domain.ChuyenCongTac;
import com.thuphi.vn.repository.ChuyenCongTacRepository;
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
 * REST controller for managing {@link com.thuphi.vn.domain.ChuyenCongTac}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChuyenCongTacResource {

    private final Logger log = LoggerFactory.getLogger(ChuyenCongTacResource.class);

    private static final String ENTITY_NAME = "chuyenCongTac";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChuyenCongTacRepository chuyenCongTacRepository;

    public ChuyenCongTacResource(ChuyenCongTacRepository chuyenCongTacRepository) {
        this.chuyenCongTacRepository = chuyenCongTacRepository;
    }

    /**
     * {@code POST  /chuyen-cong-tacs} : Create a new chuyenCongTac.
     *
     * @param chuyenCongTac the chuyenCongTac to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chuyenCongTac, or with status {@code 400 (Bad Request)} if the chuyenCongTac has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chuyen-cong-tacs")
    public ResponseEntity<ChuyenCongTac> createChuyenCongTac(@RequestBody ChuyenCongTac chuyenCongTac) throws URISyntaxException {
        log.debug("REST request to save ChuyenCongTac : {}", chuyenCongTac);
        if (chuyenCongTac.getId() != null) {
            throw new BadRequestAlertException("A new chuyenCongTac cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChuyenCongTac result = chuyenCongTacRepository.save(chuyenCongTac);
        return ResponseEntity
            .created(new URI("/api/chuyen-cong-tacs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chuyen-cong-tacs/:id} : Updates an existing chuyenCongTac.
     *
     * @param id the id of the chuyenCongTac to save.
     * @param chuyenCongTac the chuyenCongTac to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chuyenCongTac,
     * or with status {@code 400 (Bad Request)} if the chuyenCongTac is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chuyenCongTac couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chuyen-cong-tacs/{id}")
    public ResponseEntity<ChuyenCongTac> updateChuyenCongTac(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChuyenCongTac chuyenCongTac
    ) throws URISyntaxException {
        log.debug("REST request to update ChuyenCongTac : {}, {}", id, chuyenCongTac);
        if (chuyenCongTac.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chuyenCongTac.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chuyenCongTacRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChuyenCongTac result = chuyenCongTacRepository.save(chuyenCongTac);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chuyenCongTac.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chuyen-cong-tacs/:id} : Partial updates given fields of an existing chuyenCongTac, field will ignore if it is null
     *
     * @param id the id of the chuyenCongTac to save.
     * @param chuyenCongTac the chuyenCongTac to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chuyenCongTac,
     * or with status {@code 400 (Bad Request)} if the chuyenCongTac is not valid,
     * or with status {@code 404 (Not Found)} if the chuyenCongTac is not found,
     * or with status {@code 500 (Internal Server Error)} if the chuyenCongTac couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chuyen-cong-tacs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ChuyenCongTac> partialUpdateChuyenCongTac(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChuyenCongTac chuyenCongTac
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChuyenCongTac partially : {}, {}", id, chuyenCongTac);
        if (chuyenCongTac.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chuyenCongTac.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chuyenCongTacRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChuyenCongTac> result = chuyenCongTacRepository
            .findById(chuyenCongTac.getId())
            .map(
                existingChuyenCongTac -> {
                    if (chuyenCongTac.getMaChuyenDi() != null) {
                        existingChuyenCongTac.setMaChuyenDi(chuyenCongTac.getMaChuyenDi());
                    }
                    if (chuyenCongTac.getTenChuyenDi() != null) {
                        existingChuyenCongTac.setTenChuyenDi(chuyenCongTac.getTenChuyenDi());
                    }
                    if (chuyenCongTac.getThoiGianTu() != null) {
                        existingChuyenCongTac.setThoiGianTu(chuyenCongTac.getThoiGianTu());
                    }
                    if (chuyenCongTac.getThoiGianDen() != null) {
                        existingChuyenCongTac.setThoiGianDen(chuyenCongTac.getThoiGianDen());
                    }
                    if (chuyenCongTac.getMaNhanVien() != null) {
                        existingChuyenCongTac.setMaNhanVien(chuyenCongTac.getMaNhanVien());
                    }
                    if (chuyenCongTac.getTenNhanVien() != null) {
                        existingChuyenCongTac.setTenNhanVien(chuyenCongTac.getTenNhanVien());
                    }
                    if (chuyenCongTac.getSoDienThoai() != null) {
                        existingChuyenCongTac.setSoDienThoai(chuyenCongTac.getSoDienThoai());
                    }
                    if (chuyenCongTac.getDiaDiem() != null) {
                        existingChuyenCongTac.setDiaDiem(chuyenCongTac.getDiaDiem());
                    }
                    if (chuyenCongTac.getSoTien() != null) {
                        existingChuyenCongTac.setSoTien(chuyenCongTac.getSoTien());
                    }

                    return existingChuyenCongTac;
                }
            )
            .map(chuyenCongTacRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chuyenCongTac.getId().toString())
        );
    }

    /**
     * {@code GET  /chuyen-cong-tacs} : get all the chuyenCongTacs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chuyenCongTacs in body.
     */
    @GetMapping("/chuyen-cong-tacs")
    public List<ChuyenCongTac> getAllChuyenCongTacs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ChuyenCongTacs");
        return chuyenCongTacRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /chuyen-cong-tacs/:id} : get the "id" chuyenCongTac.
     *
     * @param id the id of the chuyenCongTac to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chuyenCongTac, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chuyen-cong-tacs/{id}")
    public ResponseEntity<ChuyenCongTac> getChuyenCongTac(@PathVariable Long id) {
        log.debug("REST request to get ChuyenCongTac : {}", id);
        Optional<ChuyenCongTac> chuyenCongTac = chuyenCongTacRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(chuyenCongTac);
    }

    /**
     * {@code DELETE  /chuyen-cong-tacs/:id} : delete the "id" chuyenCongTac.
     *
     * @param id the id of the chuyenCongTac to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chuyen-cong-tacs/{id}")
    public ResponseEntity<Void> deleteChuyenCongTac(@PathVariable Long id) {
        log.debug("REST request to delete ChuyenCongTac : {}", id);
        chuyenCongTacRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
