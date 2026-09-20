package com.rajan.invoiceflow.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for invoice persistence.
 *
 * Invoice is the central business entity of InvoiceFlow.
 * Tenant-scoped queries are used to maintain data isolation.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    Page<Invoice> findAllByTenantId(UUID tenantId, Pageable pageable);

    Optional<Invoice> findByIdAndTenantId(UUID id, UUID tenantId);

    Optional<Invoice> findByTenantIdAndInvoiceNumber(
            UUID tenantId,
            String invoiceNumber
    );

    Page<Invoice> findAllByTenantIdAndStatus(
            UUID tenantId,
            InvoiceStatus status,
            Pageable pageable
    );

    boolean existsByTenantIdAndInvoiceNumber(
            UUID tenantId,
            String invoiceNumber
    );
}