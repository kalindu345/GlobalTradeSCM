package com.globaltrade.scm.entity;

import com.globaltrade.scm.enums.ShipmentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "tracking_number",
            nullable = false,
            unique = true,
            length = 100
    )
    private String trackingNumber;

    @Column(
            nullable = false,
            length = 100
    )
    private String origin;

    @Column(
            nullable = false,
            length = 100
    )
    private String destination;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    @Column(name = "expected_delivery")
    private LocalDateTime expectedDelivery;

    @Column(name = "actual_delivery")
    private LocalDateTime actualDelivery;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "vendor_id",
            nullable = false
    )
    private Vendor vendor;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @PrePersist
    public void beforeInsert() {

        LocalDateTime now =
                LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        if (status == null) {
            status = ShipmentStatus.CREATED;
        }
    }

    @PreUpdate
    public void beforeUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(
            String trackingNumber
    ) {
        this.trackingNumber = trackingNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(
            String destination
    ) {
        this.destination = destination;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(
            ShipmentStatus status
    ) {
        this.status = status;
    }

    public LocalDateTime getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(
            LocalDateTime expectedDelivery
    ) {
        this.expectedDelivery =
                expectedDelivery;
    }

    public LocalDateTime getActualDelivery() {
        return actualDelivery;
    }

    public void setActualDelivery(
            LocalDateTime actualDelivery
    ) {
        this.actualDelivery =
                actualDelivery;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getVersion() {
        return version;
    }
}
