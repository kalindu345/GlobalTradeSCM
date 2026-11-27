package com.globaltrade.scm.service;

import com.globaltrade.scm.entity.Shipment;
import com.globaltrade.scm.enums.AlertSeverity;
import com.globaltrade.scm.enums.AlertType;
import com.globaltrade.scm.enums.ShipmentStatus;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class ShipmentMonitoringService {

    @PersistenceContext(
            unitName = "GlobalTradePU"
    )
    private EntityManager entityManager;

    @EJB
    private AlertService alertService;

    @TransactionAttribute(
            TransactionAttributeType.REQUIRED
    )
    public int processDelayedShipments() {

        List<Shipment> shipments =
                entityManager
                        .createQuery(
                                """
                                SELECT s
                                FROM Shipment s
                                WHERE s.status = :status
                                AND s.expectedDelivery < :now
                                """,
                                Shipment.class
                        )
                        .setParameter(
                                "status",
                                ShipmentStatus.IN_TRANSIT
                        )
                        .setParameter(
                                "now",
                                LocalDateTime.now()
                        )
                        .getResultList();

        int updatedCount = 0;

        for (Shipment shipment : shipments) {

            shipment.setStatus(
                    ShipmentStatus.DELAYED
            );

            alertService.createAlert(
                    AlertType.SHIPMENT_DELAY,
                    AlertSeverity.HIGH,
                    "Shipment "
                            + shipment.getTrackingNumber()
                            + " is delayed",
                    shipment
            );

            updatedCount++;
        }

        return updatedCount;
    }
}
