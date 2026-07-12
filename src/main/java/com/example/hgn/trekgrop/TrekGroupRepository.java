package com.example.hgn.trekgrop;

import com.example.hgn.trekgrop.constant.TrekGroupType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface TrekGroupRepository extends JpaRepository<TrekGroupEntity, String> {
    boolean existsByOrderId(String orderId);

    Optional<TrekGroupEntity> findByOrderId(String orderId);

    @Query("""
                  select tg from TrekGroupEntity tg
                  where (:groupName IS NULL OR LOWER(tg.groupName) LIKE LOWER(CONCAT('%',:groupName,'%')))
                  AND (:trekGroupType IS NULL OR tg.trekGroupType =:trekGroupType)
            
            """)
    Page<TrekGroupEntity> findAllGroup(
            @Param("groupName") String groupName,
            @Param("trekGroupType") TrekGroupType trekGroupType,
            Pageable pageable
    );
}
