package com.globaltrade.scm.api.mapper;

import com.globaltrade.scm.api.response.ErrorResponse;
import com.globaltrade.scm.exception.ShipmentNotFoundException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ShipmentNotFoundExceptionMapper
        implements ExceptionMapper<
        ShipmentNotFoundException> {

    @Override
    public Response toResponse(
            ShipmentNotFoundException exception
    ) {

        ErrorResponse error =
                new ErrorResponse(
                        "SHIPMENT_NOT_FOUND",
                        exception.getMessage()
                );

        return Response
                .status(
                        Response.Status.NOT_FOUND
                )
                .entity(error)
                .build();
    }
}
