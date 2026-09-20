package com.rajan.invoiceflow.workflow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for approval workflow definitions.
 *
 * Workflows belong to tenants and define how invoices move through
 * the approval process.
 */
public interface ApprovalWorkflowRepository
        extends JpaRepository<ApprovalWorkflow, UUID> {

    List<ApprovalWorkflow> findAllByTenantId(UUID tenantId);

    List<ApprovalWorkflow> findAllByTenantIdAndActiveTrue(UUID tenantId);

    Optional<ApprovalWorkflow> findByIdAndTenantId(
            UUID id,
            UUID tenantId
    );

    boolean existsByTenantIdAndName(
            UUID tenantId,
            String name
    );
}