package com.thuphi.vn.repository;

import com.thuphi.vn.domain.PhongBan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PhongBan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhongBanRepository extends JpaRepository<PhongBan, Long> {}
