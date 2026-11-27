package com.globaltrade.scm.api.mapper;

import com.globaltrade.scm.api.response.ErrorResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper
        implements ExceptionMapper<
        IllegalArgumentException> {

    @Override
    public Response toResponse(
            IllegalArgumentException exception
    ) {

        ErrorResponse error =
                new ErrorResponse(
                        "VALIDATION_ERROR",
                        exception.getMessage()
                );

        return Response
                .status(
                        Response.Status.BAD_REQUEST
                )
                .entity(error)
                .build();
    }
}
