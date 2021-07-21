package com.thuphi.vn.repository;

import com.thuphi.vn.domain.DinhMuc;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DinhMuc entity.
 */
@Repository
public interface DinhMucRepository extends JpaRepository<DinhMuc, Long> {
    @Query(
        value = "select distinct dinhMuc from DinhMuc dinhMuc left join fetch dinhMuc.cbacs",
        countQuery = "select count(distinct dinhMuc) from DinhMuc dinhMuc"
    )
    Page<DinhMuc> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct dinhMuc from DinhMuc dinhMuc left join fetch dinhMuc.cbacs")
    List<DinhMuc> findAllWithEagerRelationships();

    @Query("select dinhMuc from DinhMuc dinhMuc left join fetch dinhMuc.cbacs where dinhMuc.id =:id")
    Optional<DinhMuc> findOneWithEagerRelationships(@Param("id") Long id);
}
