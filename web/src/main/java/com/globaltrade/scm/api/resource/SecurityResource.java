package com.globaltrade.scm.api.resource;

import com.globaltrade.scm.service.SecurityService;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.LinkedHashMap;
import java.util.Map;

@Path("/security")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class SecurityResource {

    @EJB
    private SecurityService securityService;

    @GET
    @Path("/me")
    public Response currentUser() {

        Map<String, Object> data =
                new LinkedHashMap<>();

        data.put(
                "username",
                securityService.getCurrentUsername()
        );

        data.put(
                "logisticsCoordinator",
                securityService.isLogisticsCoordinator()
        );

        data.put(
                "customsAgent",
                securityService.isCustomsAgent()
        );

        data.put(
                "warehouseManager",
                securityService.isWarehouseManager()
        );

        data.put(
                "vendorRepresentative",
                securityService.isVendorRepresentative()
        );

        return Response
                .ok(data)
                .build();
    }
}
