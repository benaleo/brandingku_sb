package com.brandingku.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "promotions", indexes = {
        @Index(name = "idx_promotions_secure_id", columnList = "secure_id", unique = true)
})
public class Promotion extends AbstractEntity{

    @Column(name = "name", nullable = false)
    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(columnDefinition = "text")
    private String urlFile;

    @Column(columnDefinition = "text")
    private String urlDirect;


    @Override
    public Long getId() {return super.getId();}

    @Override
    public String getSecureId() {return super.getSecureId();}

    @Override
    public Boolean getIsActive() {return super.getIsActive();}
}
