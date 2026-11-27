package com.globaltrade.scm.service;

import com.globaltrade.scm.entity.InventoryItem;
import com.globaltrade.scm.enums.AlertSeverity;
import com.globaltrade.scm.enums.AlertType;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class InventoryMonitoringService {

    @PersistenceContext(
            unitName = "GlobalTradePU"
    )
    private EntityManager entityManager;

    @EJB
    private AlertService alertService;

    @TransactionAttribute(
            TransactionAttributeType.REQUIRED
    )
    public int processLowStockItems() {

        List<InventoryItem> items =
                entityManager
                        .createQuery(
                                """
                                SELECT i
                                FROM InventoryItem i
                                WHERE i.quantity <= i.reorderLevel
                                """,
                                InventoryItem.class
                        )
                        .getResultList();

        int count = 0;

        for (InventoryItem item : items) {

            String message =
                    "Low inventory for SKU "
                            + item.getSku()
                            + ". Current quantity: "
                            + item.getQuantity();

            boolean alreadyExists =
                    alertService
                            .existsUnresolvedAlert(
                                    AlertType.LOW_INVENTORY,
                                    message
                            );

            if (!alreadyExists) {

                alertService.createAlert(
                        AlertType.LOW_INVENTORY,
                        AlertSeverity.MEDIUM,
                        message,
                        null
                );

                count++;
            }
        }

        return count;
    }
}
