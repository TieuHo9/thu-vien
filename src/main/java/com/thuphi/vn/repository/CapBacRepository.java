package com.thuphi.vn.repository;

import com.thuphi.vn.domain.CapBac;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CapBac entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapBacRepository extends JpaRepository<CapBac, Long> {}
