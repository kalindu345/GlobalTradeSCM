package com.globaltrade.scm.api.mapper;

import com.globaltrade.scm.api.response.ErrorResponse;
import com.globaltrade.scm.exception.VendorNotFoundException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class VendorNotFoundExceptionMapper
        implements ExceptionMapper<
        VendorNotFoundException
        > {

    @Override
    public Response toResponse(
            VendorNotFoundException exception
    ) {

        ErrorResponse error =
                new ErrorResponse(
                        "VENDOR_NOT_FOUND",
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
