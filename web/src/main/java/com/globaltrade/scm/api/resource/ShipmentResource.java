package com.globaltrade.scm.api.resource;

import com.globaltrade.scm.dto.ShipmentProcessRequest;
import com.globaltrade.scm.entity.Shipment;
import com.globaltrade.scm.exception.InsufficientInventoryException;
import com.globaltrade.scm.exception.InventoryItemNotFoundException;
import com.globaltrade.scm.exception.VendorNotFoundException;
import com.globaltrade.scm.service.ShipmentService;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/shipments")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShipmentResource {

    @EJB
    private ShipmentService shipmentService;

    @POST
    @Path("/process")
    public Response processShipment(
            ShipmentProcessRequest request
    )
            throws VendorNotFoundException,
            InventoryItemNotFoundException,
            InsufficientInventoryException {

        Shipment shipment =
                shipmentService.processShipment(
                        request
                );

        return Response
                .status(
                        Response.Status.CREATED
                )
                .entity(shipment)
                .build();
    }

    @GET
    public Response getAll() {

        return Response
                .ok(
                        shipmentService.findAll()
                )
                .build();
    }
}
