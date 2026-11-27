package com.globaltrade.scm.service;

import com.globaltrade.scm.entity.InventoryItem;
import com.globaltrade.scm.exception.InsufficientInventoryException;
import com.globaltrade.scm.exception.InventoryItemNotFoundException;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.globaltrade.scm.security.SecurityRoles;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Stateless
public class InventoryService {

    @PersistenceContext(
            unitName = "GlobalTradePU"
    )
    private EntityManager entityManager;
    @RolesAllowed(
            SecurityRoles.WAREHOUSE_MANAGER
    )
    @TransactionAttribute(
            TransactionAttributeType.REQUIRED
    )

    public InventoryItem createItem(
            InventoryItem item
    ) {

        entityManager.persist(item);

        return item;
    }

    @TransactionAttribute(
            TransactionAttributeType.MANDATORY
    )
    public InventoryItem reserveInventory(
            Long itemId,
            Integer requestedQuantity
    )
            throws InventoryItemNotFoundException,
            InsufficientInventoryException {

        InventoryItem item =
                entityManager.find(
                        InventoryItem.class,
                        itemId
                );

        if (item == null) {

            throw new InventoryItemNotFoundException(
                    itemId
            );
        }

        if (requestedQuantity == null
                || requestedQuantity <= 0) {

            throw new IllegalArgumentException(
                    "Requested quantity must be greater than zero"
            );
        }

        if (item.getQuantity()
                < requestedQuantity) {

            throw new InsufficientInventoryException(
                    "Insufficient inventory. Available: "
                            + item.getQuantity()
                            + ", requested: "
                            + requestedQuantity
            );
        }

        item.setQuantity(
                item.getQuantity()
                        - requestedQuantity
        );

        return item;
    }

    @RolesAllowed({
            SecurityRoles.WAREHOUSE_MANAGER,
            SecurityRoles.LOGISTICS_COORDINATOR
    })
    @TransactionAttribute(
            TransactionAttributeType.SUPPORTS
    )

    public List<InventoryItem> findAll() {

        return entityManager
                .createQuery(
                        "SELECT i FROM InventoryItem i ORDER BY i.id",
                        InventoryItem.class
                )
                .getResultList();
    }
    @RolesAllowed({
            SecurityRoles.WAREHOUSE_MANAGER,
            SecurityRoles.LOGISTICS_COORDINATOR
    })
    @TransactionAttribute(
            TransactionAttributeType.SUPPORTS
    )
    public InventoryItem findById(
            Long id
    ) {

        return entityManager.find(
                InventoryItem.class,
                id
        );
    }
}
