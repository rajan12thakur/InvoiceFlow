package com.rajan.invoiceflow.invoice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a single line item inside an invoice.
 *
 * Example:
 *
 * Invoice: INV-2026-001
 *
 * Item 1:
 * Product = Laptop
 * Quantity = 2
 * Unit Price = 70000
 * Line Total = 140000
 *
 * An invoice can contain multiple InvoiceItem records.
 */
@Entity
@Table(name = "invoice_item")
public class InvoiceItem {

    /**
     * Primary key of the invoice item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Invoice to which this item belongs.
     *
     * Many invoice items can belong to one invoice.
     *
     * Example:
     *
     * Invoice INV-001
     *     ├── Laptop
     *     ├── Monitor
     *     └── Keyboard
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    /**
     * Description/name of the product or service.
     */
    @Column(nullable = false, length = 255)
    private String description;

    /**
     * Quantity of the product/service.
     *
     * BigDecimal is used instead of int because some
     * business scenarios can involve fractional quantities.
     *
     * Example:
     *
     * 2 laptops
     * 5.5 kilograms
     * 10.25 hours
     */
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal quantity;

    /**
     * Price of one unit.
     *
     * BigDecimal is used because this is a financial value.
     */
    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    /**
     * Total amount for this particular line item.
     *
     * Typically:
     *
     * lineTotal = quantity × unitPrice
     *
     * Example:
     *
     * quantity = 2
     * unitPrice = 70000
     *
     * lineTotal = 140000
     */
    @Column(name = "line_total", nullable = false, precision = 19, scale = 2)
    private BigDecimal lineTotal;

    /**
     * JPA requires a no-argument constructor.
     */
    protected InvoiceItem() {
    }

    /**
     * Creates a new invoice line item.
     *
     * The line total is passed explicitly for now.
     *
     * Later, the service layer will be responsible for
     * calculating and validating this value.
     */
    public InvoiceItem(
            Invoice invoice,
            String description,
            BigDecimal quantity,
            BigDecimal unitPrice,
            BigDecimal lineTotal
    ) {
        this.invoice = invoice;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    // ---------------------------------------------------------
    // Getters
    // ---------------------------------------------------------

    public UUID getId() {
        return id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    // ---------------------------------------------------------
    // Setters
    // ---------------------------------------------------------

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }
}