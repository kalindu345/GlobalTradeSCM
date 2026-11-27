package com.globaltrade.scm.api.resource;

import com.globaltrade.scm.service.AlertService;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/alerts")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class AlertResource {

    @EJB
    private AlertService alertService;

    @GET
    public Response getAll() {

        return Response
                .ok(
                        alertService.findAll()
                )
                .build();
    }
}
