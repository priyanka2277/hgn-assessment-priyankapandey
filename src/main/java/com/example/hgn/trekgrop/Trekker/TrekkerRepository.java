package com.example.hgn.trekgrop.Trekker;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrekkerRepository extends JpaRepository<TrekkerEntity, String> {
    boolean existsByPhoneNumber(String phoneNumber);

    @Query("""
                    select t from TrekkerEntity  t 
                    where (:firstName IS NULL OR LOWER(t.firstName) LIKE LOWER(CONCAT('%',:firstName,'%') ))
                    AND (:lastName IS NULL OR LOWER(t.lastName) LIKE LOWER(concat('%', :lastName, '%') ) )
                    AND(:phoneNumber IS NULL OR t.phoneNumber LIKE CONCAT('%', :phoneNumber, '%') )
            """)
    Page<TrekkerEntity> fetchAllTrekkers(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("phoneNumber") String phoneNumber,
            Pageable pageable
    );
}
