package com.rajan.invoiceflow.audit;

import com.rajan.invoiceflow.tenant.Tenant;
import com.rajan.invoiceflow.user.User;
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

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an immutable audit event in InvoiceFlow.
 *
 * The audit log records important actions performed within
 * the application.
 *
 * Example:
 *
 * User: Rajan
 * Action: SUBMIT
 * Entity: INVOICE
 * Entity ID: 550e8400-e29b-41d4-a716-446655440000
 * Description: Invoice submitted for approval
 * Created At: 2026-09-20 10:30
 *
 * AuditLog is intentionally generic.
 *
 * It does not contain a foreign key directly to Invoice,
 * Vendor, User, etc. because an audit event may refer to
 * different types of entities.
 *
 * Instead:
 *
 *     entityType → identifies the entity type
 *     entityId   → identifies the specific entity
 *
 * This allows the same audit table to record events for
 * invoices, users, vendors, workflows, and other entities.
 */
@Entity
@Table(name = "audit_log")
public class AuditLog {

    /**
     * Primary key of the audit record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Tenant that owns the audit event.
     *
     * Keeping tenant information directly on the audit record
     * is important for multi-tenancy.
     *
     * It allows audit records to be filtered by tenant without
     * having to determine the tenant through the referenced entity.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    /**
     * User responsible for the action.
     *
     * This can later be extended for system-generated events,
     * where a null user may be allowed if required.
     *
     * For the initial domain model, the actor is required.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Type of action that occurred.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AuditAction action;

    /**
     * Type of entity affected by the action.
     *
     * Examples:
     *
     * INVOICE
     * USER
     * VENDOR
     * WORKFLOW
     */
    @Column(name = "entity_type", nullable = false, length = 50)
    private String entityType;

    /**
     * UUID of the entity affected by the action.
     *
     * This is deliberately not a foreign key because entityId
     * may refer to different domain tables depending on entityType.
     */
    @Column(name = "entity_id", nullable = false)
    private UUID entityId;

    /**
     * Human-readable explanation of what happened.
     *
     * Example:
     *
     * "Invoice INV-2026-001 submitted for approval"
     */
    @Column(length = 1000)
    private String description;

    /**
     * Time at which the audit event was created.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * JPA requires a no-argument constructor.
     *
     * Protected prevents accidental creation of incomplete
     * audit records from unrelated application code.
     */
    protected AuditLog() {
    }

    /**
     * Creates a new audit record.
     *
     * The timestamp is generated when the audit event is created.
     */
    public AuditLog(
            Tenant tenant,
            User user,
            AuditAction action,
            String entityType,
            UUID entityId,
            String description
    ) {
        this.tenant = tenant;
        this.user = user;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public User getUser() {
        return user;
    }

    public AuditAction getAction() {
        return action;
    }

    public String getEntityType() {
        return entityType;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}