package com.rajan.invoiceflow.audit;

/**
 * Represents an action recorded in the InvoiceFlow audit trail.
 *
 * Audit actions describe important events performed by users
 * or by the application itself.
 *
 * The audit trail will later help us answer questions such as:
 *
 * - Who created this invoice?
 * - Who submitted it?
 * - Who approved or rejected it?
 * - Who returned it for correction?
 * - When did the action happen?
 *
 * These values are stored as strings in the database rather than
 * ordinal numbers, which keeps the database readable and prevents
 * enum reordering from changing the meaning of existing records.
 */
public enum AuditAction {

    /**
     * A new entity was created.
     */
    CREATE,

    /**
     * An existing entity was modified.
     */
    UPDATE,

    /**
     * An entity was deleted.
     */
    DELETE,

    /**
     * An invoice was submitted into the approval workflow.
     */
    SUBMIT,

    /**
     * An invoice approval step was approved.
     */
    APPROVE,

    /**
     * An invoice or approval step was rejected.
     */
    REJECT,

    /**
     * An invoice was returned to its creator for correction.
     */
    RETURN,

    /**
     * An invoice was marked as paid.
     */
    PAY,

    /**
     * A user successfully authenticated.
     */
    LOGIN,

    /**
     * A user logged out of the application.
     */
    LOGOUT
}