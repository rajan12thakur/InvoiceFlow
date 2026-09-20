package com.rajan.invoiceflow.tenant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Tenant persistence operations.
 *
 * Tenant is the root entity for InvoiceFlow's multi-tenant model.
 *
 * The repository provides basic CRUD operations through JpaRepository
 * and exposes specific lookup methods required by the application.
 */
public interface TenantRepository extends JpaRepository<Tenant, UUID> {

    /**
     * Finds a tenant using its unique business code.
     *
     * Example:
     * "ACME"
     */
    Optional<Tenant> findByCode(String code);

    /**
     * Checks whether a tenant with the given code already exists.
     *
     * Useful during tenant creation to prevent duplicate codes.
     */
    boolean existsByCode(String code);
}