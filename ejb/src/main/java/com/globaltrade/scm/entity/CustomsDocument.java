package com.globaltrade.scm.entity;

import com.globaltrade.scm.enums.CustomsStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customs_documents")
public class CustomsDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "shipment_id",
            nullable = false
    )
    private Shipment shipment;

    @Column(
            name = "document_type",
            nullable = false,
            length = 100
    )
    private String documentType;

    @Column(
            name = "reference_number",
            unique = true,
            length = 150
    )
    private String referenceNumber;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomsStatus status;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @PrePersist
    public void beforeInsert() {

        if (status == null) {
            status = CustomsStatus.PENDING;
        }
    }

    public Long getId() {
        return id;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(
            Shipment shipment
    ) {
        this.shipment = shipment;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(
            String documentType
    ) {
        this.documentType = documentType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(
            String referenceNumber
    ) {
        this.referenceNumber =
                referenceNumber;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(
            LocalDateTime deadline
    ) {
        this.deadline = deadline;
    }

    public CustomsStatus getStatus() {
        return status;
    }

    public void setStatus(
            CustomsStatus status
    ) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(
            LocalDateTime submittedAt
    ) {
        this.submittedAt =
                submittedAt;
    }
}
