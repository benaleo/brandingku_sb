package com.brandingku.web.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public class AbstractEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2119574796403647424L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "secure_id", nullable = false, unique = true, columnDefinition = "char(36)")
    private String secureId = UUID.randomUUID().toString();

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDelete = false;

    @Column(name = "created_at", columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
