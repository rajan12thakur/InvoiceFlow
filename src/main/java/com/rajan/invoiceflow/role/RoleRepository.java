package com.rajan.invoiceflow.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Role persistence operations.
 *
 * Roles represent the application's authorization categories,
 * such as ADMIN, EMPLOYEE, MANAGER, and FINANCE.
 *
 * Role definitions are global in the current design because the
 * Role entity does not contain a tenant_id.
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {

    /**
     * Finds a role using its business name.
     *
     * Example:
     * RoleType.MANAGER
     */
    Optional<Role> findByName(RoleType name);

    /**
     * Checks whether a role with the given name already exists.
     *
     * Useful when initializing the application's default roles.
     */
    boolean existsByName(RoleType name);
}