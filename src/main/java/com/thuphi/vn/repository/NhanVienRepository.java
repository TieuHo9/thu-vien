package com.thuphi.vn.repository;

import com.thuphi.vn.domain.NhanVien;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NhanVien entity.
 */
@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {
    @Query(
        value = "select distinct nhanVien from NhanVien nhanVien left join fetch nhanVien.phongbans left join fetch nhanVien.capbacs",
        countQuery = "select count(distinct nhanVien) from NhanVien nhanVien"
    )
    Page<NhanVien> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct nhanVien from NhanVien nhanVien left join fetch nhanVien.phongbans left join fetch nhanVien.capbacs")
    List<NhanVien> findAllWithEagerRelationships();

    @Query(
        "select nhanVien from NhanVien nhanVien left join fetch nhanVien.phongbans left join fetch nhanVien.capbacs where nhanVien.id =:id"
    )
    Optional<NhanVien> findOneWithEagerRelationships(@Param("id") Long id);
}
