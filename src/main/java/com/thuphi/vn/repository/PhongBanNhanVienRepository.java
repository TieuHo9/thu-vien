package com.thuphi.vn.repository;

import com.thuphi.vn.domain.PhongBanNhanVien;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PhongBanNhanVien entity.
 */
@Repository
public interface PhongBanNhanVienRepository extends JpaRepository<PhongBanNhanVien, Long> {
    @Query(
        value = "select distinct phongBanNhanVien from PhongBanNhanVien phongBanNhanVien left join fetch phongBanNhanVien.nviens",
        countQuery = "select count(distinct phongBanNhanVien) from PhongBanNhanVien phongBanNhanVien"
    )
    Page<PhongBanNhanVien> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct phongBanNhanVien from PhongBanNhanVien phongBanNhanVien left join fetch phongBanNhanVien.nviens")
    List<PhongBanNhanVien> findAllWithEagerRelationships();

    @Query(
        "select phongBanNhanVien from PhongBanNhanVien phongBanNhanVien left join fetch phongBanNhanVien.nviens where phongBanNhanVien.id =:id"
    )
    Optional<PhongBanNhanVien> findOneWithEagerRelationships(@Param("id") Long id);
}
