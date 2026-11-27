package com.globaltrade.scm.api.resource;

import com.globaltrade.scm.entity.CustomsDocument;
import com.globaltrade.scm.service.CustomsService;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customs")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class CustomsResource {

    @EJB
    private CustomsService customsService;

    @PUT
    @Path("/{id}/approve")
    public Response approveDocument(
            @PathParam("id") Long id
    ) {

        CustomsDocument document =
                customsService
                        .approveDocument(id);

        return Response
                .ok(document)
                .build();
    }
}
