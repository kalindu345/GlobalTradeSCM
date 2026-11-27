package com.globaltrade.scm.api.resource;

import com.globaltrade.scm.dto.ShipmentBatchUpdateRequest;
import com.globaltrade.scm.service.ShipmentBatchService;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.LinkedHashMap;
import java.util.Map;

@Path("/shipments/batch")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShipmentBatchResource {

    @EJB
    private ShipmentBatchService
            shipmentBatchService;

    @PUT
    @Path("/status")
    public Response updateStatuses(
            ShipmentBatchUpdateRequest request
    ) {

        int updated =
                shipmentBatchService
                        .updateShipmentStatuses(
                                request.getShipmentIds(),
                                request.getNewStatus()
                        );

        Map<String, Object> response =
                new LinkedHashMap<>();

        response.put(
                "updatedCount",
                updated
        );

        response.put(
                "newStatus",
                request.getNewStatus()
        );

        return Response
                .ok(response)
                .build();
    }
}
