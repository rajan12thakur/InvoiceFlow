package com.rajan.invoiceflow.approval;

/**
 * Represents the decision state of an individual invoice approval.
 *
 * An InvoiceApproval belongs to one invoice and one workflow step.
 *
 * The approval lifecycle is:
 *
 * PENDING
 *    ↓
 * APPROVED
 *
 * or
 *
 * PENDING
 *    ↓
 * REJECTED
 *
 * or
 *
 * PENDING
 *    ↓
 * RETURNED
 *
 * The actual business rules controlling these transitions
 * will be implemented later in the approval/workflow service.
 */
public enum ApprovalStatus {

    /**
     * The approval is waiting for the assigned approver
     * to make a decision.
     */
    PENDING,

    /**
     * The approver accepted the invoice at this workflow step.
     */
    APPROVED,

    /**
     * The approver rejected the invoice.
     */
    REJECTED,

    /**
     * The approver returned the invoice to the creator
     * for correction.
     */
    RETURNED
}