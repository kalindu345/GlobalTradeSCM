package com.globaltrade.scm.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(
        rollback = true
)
public class VendorNotFoundException
        extends Exception {

    public VendorNotFoundException(
            Long vendorId
    ) {

        super(
                "Vendor not found with ID: "
                        + vendorId
        );
    }
}
