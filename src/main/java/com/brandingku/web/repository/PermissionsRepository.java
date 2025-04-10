package com.brandingku.web.repository;

import com.brandingku.web.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {

    Optional<Permissions> findByName(String permissionName);

    boolean existsByName(String name);
}