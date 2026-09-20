package com.rajan.invoiceflow.approval;

import com.rajan.invoiceflow.invoice.Invoice;
import com.rajan.invoiceflow.user.User;
import com.rajan.invoiceflow.workflow.WorkflowStep;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents the execution of one workflow step for a specific invoice.
 *
 * WorkflowStep = configuration
 *
 * InvoiceApproval = actual approval instance
 *
 * Example:
 *
 * Workflow:
 *
 *     Step 1 → Manager Approval
 *     Step 2 → Finance Approval
 *
 * Invoice:
 *
 *     INV-2026-001
 *
 * Runtime approvals:
 *
 *     InvoiceApproval #1
 *         Invoice       → INV-2026-001
 *         WorkflowStep  → Manager Approval
 *         Approver      → Manager User
 *         Status        → APPROVED
 *
 *     InvoiceApproval #2
 *         Invoice       → INV-2026-001
 *         WorkflowStep  → Finance Approval
 *         Approver      → Finance User
 *         Status        → PENDING
 *
 * This entity therefore records the actual execution state
 * of the approval workflow.
 */
@Entity
@Table(
        name = "invoice_approval",
        uniqueConstraints = {
                /**
                 * An invoice should have only one approval record
                 * for a particular workflow step.
                 *
                 * Example:
                 *
                 * Invoice A + Step 1 → one InvoiceApproval
                 *
                 * This prevents accidental duplicate approval
                 * records for the same workflow execution.
                 */
                @UniqueConstraint(
                        name = "uk_invoice_approval_step",
                        columnNames = {"invoice_id", "workflow_step_id"}
                )
        }
)
public class InvoiceApproval {

    /**
     * Primary key of the approval record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Invoice that is being approved.
     *
     * Multiple approval records can belong to the same invoice
     * because an invoice may pass through multiple workflow steps.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    /**
     * Workflow step represented by this approval record.
     *
     * This connects the runtime approval back to the workflow
     * configuration that created it.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workflow_step_id", nullable = false)
    private WorkflowStep workflowStep;

    /**
     * User responsible for making the approval decision.
     *
     * The workflow step defines the required role, while this
     * field identifies the actual user assigned to perform it.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "approver_id", nullable = false)
    private User approver;

    /**
     * Current decision state of this approval.
     *
     * New approval records start as PENDING.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ApprovalStatus status;

    /**
     * Optional comment provided by the approver.
     *
     * This can contain:
     *
     * - rejection reason
     * - return instructions
     * - approval comments
     */
    @Column(length = 1000)
    private String comment;

    /**
     * Timestamp when the approval decision was made.
     *
     * This remains null while the approval is PENDING.
     */
    @Column(name = "decided_at")
    private LocalDateTime decidedAt;

    /**
     * JPA requires a no-argument constructor.
     *
     * Protected prevents normal application code from
     * accidentally creating an incomplete approval record.
     */
    protected InvoiceApproval() {
    }

    /**
     * Creates a new pending approval.
     *
     * A newly created approval always starts in PENDING state.
     *
     * The workflow/approval service will later change the status
     * after the approver performs an action.
     */
    public InvoiceApproval(
            Invoice invoice,
            WorkflowStep workflowStep,
            User approver
    ) {
        this.invoice = invoice;
        this.workflowStep = workflowStep;
        this.approver = approver;
        this.status = ApprovalStatus.PENDING;
    }

    public UUID getId() {
        return id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public WorkflowStep getWorkflowStep() {
        return workflowStep;
    }

    public User getApprover() {
        return approver;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getDecidedAt() {
        return decidedAt;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setWorkflowStep(WorkflowStep workflowStep) {
        this.workflowStep = workflowStep;
    }

    public void setApprover(User approver) {
        this.approver = approver;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDecidedAt(LocalDateTime decidedAt) {
        this.decidedAt = decidedAt;
    }
}