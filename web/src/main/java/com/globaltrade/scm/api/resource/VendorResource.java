package com.globaltrade.scm.api.resource;

import com.globaltrade.scm.entity.Vendor;
import com.globaltrade.scm.service.VendorService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@Path("/vendors")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VendorResource {

    @EJB
    private VendorService vendorService;

    @POST
    public Response createVendor(
            Vendor vendor
    ) {

        Vendor createdVendor =
                vendorService.createVendor(
                        vendor
                );

        return Response
                .status(
                        Response.Status.CREATED
                )
                .entity(createdVendor)
                .build();
    }

    @GET
    public List<Vendor> getAllVendors() {

        return vendorService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getVendor(
            @PathParam("id") Long id
    ) {

        Vendor vendor =
                vendorService.findById(id);

        if (vendor == null) {

            return Response
                    .status(
                            Response.Status.NOT_FOUND
                    )
                    .build();
        }

        return Response
                .ok(vendor)
                .build();
    }
}
