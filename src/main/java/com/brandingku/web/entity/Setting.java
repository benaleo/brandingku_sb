package com.brandingku.web.entity;

import com.brandingku.web.enums.SettingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "settings", indexes = {
        @Index(name = "idx_settings_secure_id", columnList = "secure_id", unique = true)
})
public class Setting extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "identity", nullable = false, updatable = false, unique = true)
    private String identity;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SettingType type;

    @Column(name = "value", nullable = false)
    private String value;
}
