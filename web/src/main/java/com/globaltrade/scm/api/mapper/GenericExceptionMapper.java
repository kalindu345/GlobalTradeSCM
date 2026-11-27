package com.globaltrade.scm.api.mapper;

import com.globaltrade.scm.api.response.ErrorResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class GenericExceptionMapper
        implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER =
            Logger.getLogger(
                    GenericExceptionMapper.class.getName()
            );

    @Override
    public Response toResponse(
            Throwable exception
    ) {

        LOGGER.log(
                Level.SEVERE,
                "Unexpected application error",
                exception
        );

        ErrorResponse error =
                new ErrorResponse(
                        "INTERNAL_SERVER_ERROR",
                        "An unexpected server error occurred"
                );

        return Response
                .status(
                        Response.Status.INTERNAL_SERVER_ERROR
                )
                .entity(error)
                .build();
    }
}
