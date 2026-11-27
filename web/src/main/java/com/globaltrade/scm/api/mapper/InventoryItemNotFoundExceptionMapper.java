package com.globaltrade.scm.api.mapper;

import com.globaltrade.scm.api.response.ErrorResponse;
import com.globaltrade.scm.exception.InventoryItemNotFoundException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InventoryItemNotFoundExceptionMapper
        implements ExceptionMapper<
        InventoryItemNotFoundException
        > {

    @Override
    public Response toResponse(
            InventoryItemNotFoundException exception
    ) {

        ErrorResponse error =
                new ErrorResponse(
                        "INVENTORY_ITEM_NOT_FOUND",
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
