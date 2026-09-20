package com.rajan.invoiceflow.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository for audit trail persistence.
 *
 * Audit logs provide a historical record of important business
 * and security actions performed within InvoiceFlow.
 */
public interface AuditLogRepository
        extends JpaRepository<AuditLog, UUID> {

    Page<AuditLog> findAllByTenantIdOrderByCreatedAtDesc(
            UUID tenantId,
            Pageable pageable
    );

    Page<AuditLog> findAllByTenantIdAndEntityTypeOrderByCreatedAtDesc(
            UUID tenantId,
            String entityType,
            Pageable pageable
    );

    Page<AuditLog> findAllByTenantIdAndEntityIdOrderByCreatedAtDesc(
            UUID tenantId,
            UUID entityId,
            Pageable pageable
    );
}