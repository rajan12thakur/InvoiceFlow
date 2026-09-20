package com.rajan.invoiceflow.invoice;

import com.rajan.invoiceflow.tenant.Tenant;
import com.rajan.invoiceflow.user.User;
import com.rajan.invoiceflow.vendor.Vendor;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents an invoice submitted by a vendor to a tenant/company.
 *
 * InvoiceFlow is centered around the invoice approval lifecycle.
 *
 * Example lifecycle:
 *
 * DRAFT
 *   ↓
 * SUBMITTED
 *   ↓
 * MANAGER_APPROVAL
 *   ↓
 * FINANCE_APPROVAL
 *   ↓
 * APPROVED
 *   ↓
 * PAID
 *
 * An invoice can also be:
 *
 * SUBMITTED → REJECTED
 *
 * or
 *
 * SUBMITTED → RETURNED → DRAFT
 */
@Entity
@Table(
        name = "invoice",
        uniqueConstraints = {
                /**
                 * Invoice numbers only need to be unique
                 * inside a particular tenant.
                 *
                 * Example:
                 *
                 * Tenant A → INV-001 ✅
                 * Tenant B → INV-001 ✅
                 * Tenant A → INV-001 ❌
                 */
                @UniqueConstraint(
                        name = "uk_invoice_tenant_number",
                        columnNames = {"tenant_id", "invoice_number"}
                )
        }
)
public class Invoice {

    /**
     * Primary key of the invoice.
     *
     * Hibernate generates the UUID automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Company/tenant that owns this invoice.
     *
     * This relationship is important for multi-tenancy.
     *
     * Every invoice must belong to exactly one tenant.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    /**
     * Vendor who issued the invoice.
     *
     * Multiple invoices can belong to the same vendor.
     *
     * Example:
     *
     * Vendor: Microsoft
     *     ├── INV-001
     *     ├── INV-002
     *     └── INV-003
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    /**
     * User who created the invoice in InvoiceFlow.
     *
     * This allows us to know who submitted/created
     * the invoice originally.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    /**
     * Business invoice number.
     *
     * Example:
     *
     * INV-2026-0001
     * INV-10045
     */
    @Column(name = "invoice_number", nullable = false, length = 100)
    private String invoiceNumber;

    /**
     * Date printed/issued by the vendor.
     */
    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    /**
     * Date by which payment is expected.
     *
     * Example:
     *
     * Invoice Date: 2026-09-01
     * Due Date:     2026-09-30
     */
    @Column(name = "due_date")
    private LocalDate dueDate;

    /**
     * Currency used by the invoice.
     *
     * Example:
     *
     * INR
     * USD
     * EUR
     *
     * Keeping this as String for now keeps the entity simple.
     * We can introduce a proper currency/value-object strategy
     * later if the project requires it.
     */
    @Column(nullable = false, length = 3)
    private String currency;

    /**
     * Total amount of the invoice.
     *
     * BigDecimal is used instead of double/float because
     * financial values require precise decimal calculations.
     */
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    /**
     * Current status of the invoice.
     *
     * The status controls where the invoice is in the
     * approval workflow.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private InvoiceStatus status;

    /**
     * Optional description or notes associated with the invoice.
     */
    @Column(length = 1000)
    private String description;

    /**
     * JPA requires a no-argument constructor.
     *
     * Protected prevents normal application code from
     * accidentally creating an incomplete Invoice object.
     */
    protected Invoice() {
    }

    /**
     * Creates a new invoice.
     *
     * The ID and status are handled internally.
     *
     * New invoices start in DRAFT status.
     */
    public Invoice(
            Tenant tenant,
            Vendor vendor,
            User createdBy,
            String invoiceNumber,
            LocalDate invoiceDate,
            LocalDate dueDate,
            String currency,
            BigDecimal totalAmount,
            String description
    ) {
        this.tenant = tenant;
        this.vendor = vendor;
        this.createdBy = createdBy;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.currency = currency;
        this.totalAmount = totalAmount;
        this.description = description;

        // Every newly created invoice starts as a draft.
        this.status = InvoiceStatus.DRAFT;
    }

    // ---------------------------------------------------------
    // Getters
    // ---------------------------------------------------------

    public UUID getId() {
        return id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    // ---------------------------------------------------------
    // Setters
    // ---------------------------------------------------------

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}