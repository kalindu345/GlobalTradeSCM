package com.globaltrade.scm.exception;

public class ShipmentNotFoundException
        extends RuntimeException {

    public ShipmentNotFoundException(
            Long shipmentId
    ) {

        super(
                "Shipment not found with ID: "
                        + shipmentId
        );
    }
}
