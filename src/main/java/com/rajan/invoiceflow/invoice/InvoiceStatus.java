package com.rajan.invoiceflow.invoice;

/**
 * Represents the lifecycle state of an invoice.
 *
 * The invoice status will later be controlled by
 * the workflow/approval engine.
 *
 * Basic lifecycle:
 *
 * DRAFT
 *   ↓
 * SUBMITTED
 *   ↓
 * APPROVED
 *   ↓
 * PAID
 *
 * Alternative paths:
 *
 * SUBMITTED → REJECTED
 *
 * SUBMITTED → RETURNED → DRAFT
 */
public enum InvoiceStatus {

    /**
     * Invoice is being created or edited.
     */
    DRAFT,

    /**
     * Invoice has been submitted for approval.
     */
    SUBMITTED,

    /**
     * Invoice has been approved by the required approvers.
     */
    APPROVED,

    /**
     * Invoice has been rejected.
     */
    REJECTED,

    /**
     * Invoice has been returned to the creator
     * for corrections.
     */
    RETURNED,

    /**
     * Invoice has been paid.
     */
    PAID
}