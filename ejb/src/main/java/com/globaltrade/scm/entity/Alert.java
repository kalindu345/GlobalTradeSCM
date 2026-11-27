package com.globaltrade.scm.entity;

import com.globaltrade.scm.enums.AlertSeverity;
import com.globaltrade.scm.enums.AlertType;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertSeverity severity;

    @Column(
            nullable = false,
            length = 1000
    )
    private String message;

    @Column(nullable = false)
    private Boolean resolved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @PrePersist
    public void beforeInsert() {

        if (resolved == null) {
            resolved = false;
        }

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(
            AlertType type
    ) {
        this.type = type;
    }

    public AlertSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(
            AlertSeverity severity
    ) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(
            String message
    ) {
        this.message = message;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(
            Boolean resolved
    ) {
        this.resolved = resolved;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(
            Shipment shipment
    ) {
        this.shipment = shipment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
