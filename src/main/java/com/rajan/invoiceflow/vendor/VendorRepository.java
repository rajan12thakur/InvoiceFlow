package com.rajan.invoiceflow.vendor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for vendor persistence.
 *
 * Vendors are tenant-owned business data, therefore queries should
 * normally include the tenant identifier.
 */
public interface VendorRepository extends JpaRepository<Vendor, UUID> {

    List<Vendor> findAllByTenantId(UUID tenantId);

    Optional<Vendor> findByTenantIdAndCode(UUID tenantId, String code);

    boolean existsByTenantIdAndCode(UUID tenantId, String code);
}