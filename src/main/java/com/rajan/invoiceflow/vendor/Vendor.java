package com.rajan.invoiceflow.vendor;

import com.rajan.invoiceflow.tenant.Tenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.UUID;

@Entity
@Table(
        name = "vendor",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_vendor_tenant_code",
                        columnNames = {"tenant_id", "code"}
                )
        }
)
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(length = 255)
    private String email;

    @Column(length = 30)
    private String phone;

    @Column(length = 500)
    private String address;

    @Column(length = 100)
    private String taxIdentifier;

    @Column(nullable = false)
    private boolean active = true;

    protected Vendor() {
    }

    public Vendor(
            Tenant tenant,
            String name,
            String code,
            String email,
            String phone,
            String address,
            String taxIdentifier
    ) {
        this.tenant = tenant;
        this.name = name;
        this.code = code;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.taxIdentifier = taxIdentifier;
    }

    public UUID getId() {
        return id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getTaxIdentifier() {
        return taxIdentifier;
    }

    public boolean isActive() {
        return active;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTaxIdentifier(String taxIdentifier) {
        this.taxIdentifier = taxIdentifier;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}