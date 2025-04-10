package com.brandingku.web.repository;

import com.brandingku.web.entity.Roles;
import com.brandingku.web.model.projection.CastIdSecureIdProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(String name);

    @Query("""
            SELECT new com.brandingku.web.model.projection.CastIdSecureIdProjection(r.id, r.secureId)
            FROM Roles r
            WHERE r.secureId = :secureId
            """)
    Optional<CastIdSecureIdProjection> findIdBySecureId(String secureId);

    Page<Roles> findByNameLikeIgnoreCase(String keyword, Pageable pageable);
}