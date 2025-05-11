package com.brandingku.web.repository;

import com.brandingku.web.entity.Setting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

    @Query("""
            SELECT s
            FROM Setting s
            WHERE
            (LOWER(s.name) LIKE LOWER(:keyword) OR
            LOWER(s.identity) = LOWER(:keyword))
            """)
    Page<Setting> findDataByKeyword(String keyword, Pageable pageable);

    Optional<Setting> findByIdentity(String identity);
}
