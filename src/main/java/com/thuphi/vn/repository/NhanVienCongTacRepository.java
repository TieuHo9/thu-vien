package com.thuphi.vn.repository;

import com.thuphi.vn.domain.NhanVienCongTac;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NhanVienCongTac entity.
 */
@Repository
public interface NhanVienCongTacRepository extends JpaRepository<NhanVienCongTac, Long> {
    @Query(
        value = "select distinct nhanVienCongTac from NhanVienCongTac nhanVienCongTac left join fetch nhanVienCongTac.chuyencts",
        countQuery = "select count(distinct nhanVienCongTac) from NhanVienCongTac nhanVienCongTac"
    )
    Page<NhanVienCongTac> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct nhanVienCongTac from NhanVienCongTac nhanVienCongTac left join fetch nhanVienCongTac.chuyencts")
    List<NhanVienCongTac> findAllWithEagerRelationships();

    @Query(
        "select nhanVienCongTac from NhanVienCongTac nhanVienCongTac left join fetch nhanVienCongTac.chuyencts where nhanVienCongTac.id =:id"
    )
    Optional<NhanVienCongTac> findOneWithEagerRelationships(@Param("id") Long id);
}
