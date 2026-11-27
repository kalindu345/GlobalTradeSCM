package com.globaltrade.scm.dto;

import java.time.LocalDateTime;

public class ShipmentProcessRequest {

    private String trackingNumber;

    private Long vendorId;

    private Long inventoryItemId;

    private Integer requestedQuantity;

    private String origin;

    private String destination;

    private LocalDateTime expectedDelivery;

    private String customsDocumentType;

    private String customsReferenceNumber;

    private LocalDateTime customsDeadline;

    public ShipmentProcessRequest() {
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(
            String trackingNumber
    ) {
        this.trackingNumber =
                trackingNumber;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(
            Long vendorId
    ) {
        this.vendorId = vendorId;
    }

    public Long getInventoryItemId() {
        return inventoryItemId;
    }

    public void setInventoryItemId(
            Long inventoryItemId
    ) {
        this.inventoryItemId =
                inventoryItemId;
    }

    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(
            Integer requestedQuantity
    ) {
        this.requestedQuantity =
                requestedQuantity;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(
            String origin
    ) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(
            String destination
    ) {
        this.destination =
                destination;
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

    public String getCustomsDocumentType() {
        return customsDocumentType;
    }

    public void setCustomsDocumentType(
            String customsDocumentType
    ) {
        this.customsDocumentType =
                customsDocumentType;
    }

    public String getCustomsReferenceNumber() {
        return customsReferenceNumber;
    }

    public void setCustomsReferenceNumber(
            String customsReferenceNumber
    ) {
        this.customsReferenceNumber =
                customsReferenceNumber;
    }

    public LocalDateTime getCustomsDeadline() {
        return customsDeadline;
    }

    public void setCustomsDeadline(
            LocalDateTime customsDeadline
    ) {
        this.customsDeadline =
                customsDeadline;
    }
}
