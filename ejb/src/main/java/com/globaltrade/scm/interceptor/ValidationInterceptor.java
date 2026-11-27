package com.globaltrade.scm.interceptor;

import com.globaltrade.scm.dto.ShipmentProcessRequest;
import com.globaltrade.scm.interceptor.binding.Validated;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Validated
@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 10)
public class ValidationInterceptor {

    @AroundInvoke
    public Object validate(
            InvocationContext context
    ) throws Exception {

        Object[] parameters =
                context.getParameters();

        for (Object parameter : parameters) {

            if (parameter
                    instanceof ShipmentProcessRequest request) {

                validateShipmentRequest(
                        request
                );
            }
        }

        return context.proceed();
    }

    private void validateShipmentRequest(
            ShipmentProcessRequest request
    ) {

        if (request == null) {

            throw new IllegalArgumentException(
                    "Shipment request cannot be null"
            );
        }

        if (request.getTrackingNumber() == null
                || request.getTrackingNumber()
                .isBlank()) {

            throw new IllegalArgumentException(
                    "Tracking number is required"
            );
        }

        if (request.getVendorId() == null) {

            throw new IllegalArgumentException(
                    "Vendor ID is required"
            );
        }

        if (request.getInventoryItemId() == null) {

            throw new IllegalArgumentException(
                    "Inventory item ID is required"
            );
        }

        if (request.getRequestedQuantity() == null
                || request.getRequestedQuantity() <= 0) {

            throw new IllegalArgumentException(
                    "Requested quantity must be greater than zero"
            );
        }

        if (request.getOrigin() == null
                || request.getOrigin().isBlank()) {

            throw new IllegalArgumentException(
                    "Shipment origin is required"
            );
        }

        if (request.getDestination() == null
                || request.getDestination().isBlank()) {

            throw new IllegalArgumentException(
                    "Shipment destination is required"
            );
        }

        if (request.getExpectedDelivery() == null) {

            throw new IllegalArgumentException(
                    "Expected delivery date is required"
            );
        }

        if (request.getCustomsDeadline() == null) {

            throw new IllegalArgumentException(
                    "Customs deadline is required"
            );
        }
    }
}