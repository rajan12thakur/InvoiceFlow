package com.rajan.invoiceflow.workflow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for workflow steps.
 *
 * WorkflowStep defines an individual approval stage such as:
 *
 * Manager Approval
 * Finance Approval
 *
 * Steps are ordered using stepOrder.
 */
public interface WorkflowStepRepository
        extends JpaRepository<WorkflowStep, UUID> {

    List<WorkflowStep> findAllByWorkflowIdOrderByStepOrderAsc(
            UUID workflowId
    );

    Optional<WorkflowStep> findByWorkflowIdAndStepOrder(
            UUID workflowId,
            Integer stepOrder
    );

    boolean existsByWorkflowIdAndStepOrder(
            UUID workflowId,
            Integer stepOrder
    );
}