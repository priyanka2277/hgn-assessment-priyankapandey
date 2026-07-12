package com.example.hgn.coordinator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatorRepository extends JpaRepository<CoordinatorEntity, String> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    @Query("""
                SELECT c
                FROM CoordinatorEntity c
                WHERE (:email IS NULL
                       OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%')))
            """)
    Page<CoordinatorEntity> findAllCoordinator(
            @Param("email") String email,
            Pageable pageable
    );
}
