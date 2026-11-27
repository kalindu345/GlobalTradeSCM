package com.globaltrade.scm.api.resource;

import com.globaltrade.scm.entity.InventoryItem;
import com.globaltrade.scm.service.InventoryService;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/inventory")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryResource {

    @EJB
    private InventoryService inventoryService;

    @POST
    public Response createItem(
            InventoryItem item
    ) {

        InventoryItem created =
                inventoryService.createItem(
                        item
                );

        return Response
                .status(
                        Response.Status.CREATED
                )
                .entity(created)
                .build();
    }

    @GET
    public Response getAll() {

        return Response
                .ok(
                        inventoryService.findAll()
                )
                .build();
    }
}
