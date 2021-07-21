package com.thuphi.vn.repository;

import com.thuphi.vn.domain.ChuyenCongTac;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChuyenCongTac entity.
 */
@Repository
public interface ChuyenCongTacRepository extends JpaRepository<ChuyenCongTac, Long> {
    @Query(
        value = "select distinct chuyenCongTac from ChuyenCongTac chuyenCongTac left join fetch chuyenCongTac.nhanviens",
        countQuery = "select count(distinct chuyenCongTac) from ChuyenCongTac chuyenCongTac"
    )
    Page<ChuyenCongTac> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct chuyenCongTac from ChuyenCongTac chuyenCongTac left join fetch chuyenCongTac.nhanviens")
    List<ChuyenCongTac> findAllWithEagerRelationships();

    @Query("select chuyenCongTac from ChuyenCongTac chuyenCongTac left join fetch chuyenCongTac.nhanviens where chuyenCongTac.id =:id")
    Optional<ChuyenCongTac> findOneWithEagerRelationships(@Param("id") Long id);
}
