package com.rajan.invoiceflow.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for User persistence operations.
 *
 * Users belong to a specific tenant and are later used by the
 * authentication, authorization, approval, and audit modules.
 *
 * Tenant-aware query methods will be added where business operations
 * require explicit tenant filtering.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user using the globally unique email address.
     *
     * This lookup will primarily be used during authentication.
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks whether an email address is already registered.
     *
     * Used during user creation to prevent duplicate accounts.
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by email within a specific tenant.
     *
     * This method becomes important once tenant isolation is enforced
     * throughout the application.
     */
    Optional<User> findByTenantIdAndEmail(UUID tenantId, String email);
}