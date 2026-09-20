package com.rajan.invoiceflow.role;

import com.rajan.invoiceflow.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for UserRole persistence operations.
 *
 * UserRole represents the assignment of one or more roles to a user.
 *
 * Example:
 *
 * User: Rajan
 *   ├── EMPLOYEE
 *   └── MANAGER
 *
 * The repository provides methods for retrieving the roles assigned
 * to a particular user and checking whether a specific role has
 * already been assigned.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

    /**
     * Retrieves all role assignments belonging to a specific user.
     *
     * This is useful when building the authenticated user's
     * authorities for Spring Security.
     */
    List<UserRole> findAllByUser(User user);

    /**
     * Retrieves all role assignments using the user's identifier.
     *
     * This form is convenient when the service layer only has
     * the user's UUID rather than the complete User entity.
     */
    List<UserRole> findAllByUserId(UUID userId);

    /**
     * Checks whether a specific role is already assigned to a user.
     *
     * This prevents duplicate role assignments before creating
     * a new UserRole record.
     */
    boolean existsByUserIdAndRoleId(UUID userId, UUID roleId);

    /**
     * Deletes a specific role assignment from a user.
     *
     * The method is intentionally scoped to both identifiers so
     * that only the requested user-role relationship is removed.
     */
    void deleteByUserIdAndRoleId(UUID userId, UUID roleId);
}