package com.globaltrade.scm.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(
        rollback = true
)
public class InventoryItemNotFoundException
        extends Exception {

    public InventoryItemNotFoundException(
            Long itemId
    ) {

        super(
                "Inventory item not found with ID: "
                        + itemId
        );
    }
}
