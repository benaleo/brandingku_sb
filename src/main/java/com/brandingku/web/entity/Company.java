package com.brandingku.web.entity;

import com.brandingku.web.entity.impl.SecureIdentifiable;
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
@Table(name = "company", indexes = {
        @Index(name = "idx_company_secure_id", columnList = "secure_id", unique = true)
})
public class Company extends AbstractEntity implements SecureIdentifiable {

    private String name;
    private String address;
    private String city;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "secure_id")
    private Company parent;

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String getSecureId() {
        return super.getSecureId();
    }

    @Override
    public Boolean getIsActive() {
        return super.getIsActive();
    }

}
