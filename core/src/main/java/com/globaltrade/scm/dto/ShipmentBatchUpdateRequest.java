package com.globaltrade.scm.dto;

import com.globaltrade.scm.enums.ShipmentStatus;

import java.util.List;

public class ShipmentBatchUpdateRequest {

    private List<Long> shipmentIds;

    private ShipmentStatus newStatus;

    public ShipmentBatchUpdateRequest() {
    }

    public List<Long> getShipmentIds() {
        return shipmentIds;
    }

    public void setShipmentIds(
            List<Long> shipmentIds
    ) {
        this.shipmentIds =
                shipmentIds;
    }

    public ShipmentStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(
            ShipmentStatus newStatus
    ) {
        this.newStatus =
                newStatus;
    }
}