package com.globaltrade.scm.service;

import com.globaltrade.scm.entity.Shipment;
import com.globaltrade.scm.enums.ShipmentStatus;

import com.globaltrade.scm.exception.ShipmentNotFoundException;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Status;
import jakarta.transaction.UserTransaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.globaltrade.scm.security.SecurityRoles;
import jakarta.annotation.security.RolesAllowed;

@Stateless
@TransactionManagement(
        TransactionManagementType.BEAN
)
public class ShipmentBatchService {

    private static final Logger LOGGER =
            Logger.getLogger(
                    ShipmentBatchService.class.getName()
            );

    @PersistenceContext(
            unitName = "GlobalTradePU"
    )
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    @RolesAllowed(
            SecurityRoles.LOGISTICS_COORDINATOR
    )

    public int updateShipmentStatuses(
            List<Long> shipmentIds,
            ShipmentStatus newStatus
    ) {

        if (shipmentIds == null
                || shipmentIds.isEmpty()) {

            throw new IllegalArgumentException(
                    "Shipment ID list cannot be empty"
            );
        }

        if (newStatus == null) {

            throw new IllegalArgumentException(
                    "New shipment status is required"
            );
        }

        try {

            userTransaction.begin();

            int updatedCount = 0;

            for (Long shipmentId : shipmentIds) {

                Shipment shipment =
                        entityManager.find(
                                Shipment.class,
                                shipmentId
                        );

                if (shipment == null) {

                    throw new ShipmentNotFoundException(
                            shipmentId
                    );
                }

                shipment.setStatus(
                        newStatus
                );

                updatedCount++;
            }

            entityManager.flush();

            userTransaction.commit();

            LOGGER.info(
                    "BMT batch shipment update committed. "
                            + "Updated shipments: "
                            + updatedCount
            );

            return updatedCount;

        } catch (Exception exception) {

            try {

                userTransaction.rollback();

                LOGGER.warning(
                        "BMT transaction rolled back"
                );

            } catch (Exception rollbackException) {

                LOGGER.log(
                        Level.SEVERE,
                        "BMT rollback failed",
                        rollbackException
                );
            }

            throw new RuntimeException(
                    "Batch shipment update failed: "
                            + exception.getMessage(),
                    exception
            );
        }
    }
    private void rollbackSafely() {

        try {

            int status =
                    userTransaction.getStatus();

            if (status
                    == Status.STATUS_ACTIVE
                    || status
                    == Status.STATUS_MARKED_ROLLBACK) {

                userTransaction.rollback();

                LOGGER.warning(
                        "BMT transaction rolled back"
                );
            }

        } catch (Exception rollbackException) {

            LOGGER.log(
                    Level.SEVERE,
                    "Unable to rollback BMT transaction",
                    rollbackException
            );
        }
    }
}
