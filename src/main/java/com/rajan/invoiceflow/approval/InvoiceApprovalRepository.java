package com.rajan.invoiceflow.approval;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for invoice approval decisions.
 *
 * Each approval connects:
 *
 * Invoice → Workflow Step → Approver
 *
 * The unique constraint on invoice + workflow step ensures that
 * an invoice cannot have duplicate approval records for the same step.
 */
public interface InvoiceApprovalRepository
        extends JpaRepository<InvoiceApproval, UUID> {

    List<InvoiceApproval> findAllByInvoiceIdOrderByWorkflowStepStepOrderAsc(
            UUID invoiceId
    );

    Optional<InvoiceApproval> findByInvoiceIdAndWorkflowStepId(
            UUID invoiceId,
            UUID workflowStepId
    );

    List<InvoiceApproval> findAllByApproverId(UUID approverId);

    boolean existsByInvoiceIdAndWorkflowStepId(
            UUID invoiceId,
            UUID workflowStepId
    );
}