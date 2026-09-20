package com.rajan.invoiceflow.invoice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for invoice line-item persistence.
 *
 * Invoice items belong to a parent invoice and are normally
 * accessed through that invoice.
 */
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, UUID> {

    List<InvoiceItem> findAllByInvoiceId(UUID invoiceId);

    void deleteAllByInvoiceId(UUID invoiceId);
}