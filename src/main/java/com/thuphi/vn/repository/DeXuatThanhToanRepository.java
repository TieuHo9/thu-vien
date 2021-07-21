package com.thuphi.vn.repository;

import com.thuphi.vn.domain.DeXuatThanhToan;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DeXuatThanhToan entity.
 */
@Repository
public interface DeXuatThanhToanRepository extends JpaRepository<DeXuatThanhToan, Long> {
    @Query(
        value = "select distinct deXuatThanhToan from DeXuatThanhToan deXuatThanhToan left join fetch deXuatThanhToan.chiphis left join fetch deXuatThanhToan.dmucs",
        countQuery = "select count(distinct deXuatThanhToan) from DeXuatThanhToan deXuatThanhToan"
    )
    Page<DeXuatThanhToan> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct deXuatThanhToan from DeXuatThanhToan deXuatThanhToan left join fetch deXuatThanhToan.chiphis left join fetch deXuatThanhToan.dmucs"
    )
    List<DeXuatThanhToan> findAllWithEagerRelationships();

    @Query(
        "select deXuatThanhToan from DeXuatThanhToan deXuatThanhToan left join fetch deXuatThanhToan.chiphis left join fetch deXuatThanhToan.dmucs where deXuatThanhToan.id =:id"
    )
    Optional<DeXuatThanhToan> findOneWithEagerRelationships(@Param("id") Long id);

    List<DeXuatThanhToan> findByTenDeXuat(String tenDeXuat);

    List<DeXuatThanhToan> findByThanhToan(String thanhtoan);
}
