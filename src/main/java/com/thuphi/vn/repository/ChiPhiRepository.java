package com.thuphi.vn.repository;

import com.thuphi.vn.domain.ChiPhi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChiPhi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChiPhiRepository extends JpaRepository<ChiPhi, Long> {}
