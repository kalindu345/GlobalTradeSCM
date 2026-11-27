package com.globaltrade.scm.service;

import com.globaltrade.scm.entity.Alert;
import com.globaltrade.scm.entity.Shipment;
import com.globaltrade.scm.enums.AlertSeverity;
import com.globaltrade.scm.enums.AlertType;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class AlertService {

    @PersistenceContext(
            unitName = "GlobalTradePU"
    )
    private EntityManager entityManager;

    @TransactionAttribute(
            TransactionAttributeType.REQUIRED
    )
    public Alert createAlert(
            AlertType type,
            AlertSeverity severity,
            String message,
            Shipment shipment
    ) {

        Alert alert = new Alert();

        alert.setType(type);
        alert.setSeverity(severity);
        alert.setMessage(message);
        alert.setShipment(shipment);

        entityManager.persist(alert);

        return alert;
    }

    @TransactionAttribute(
            TransactionAttributeType.SUPPORTS
    )
    public List<Alert> findAll() {

        return entityManager
                .createQuery(
                        "SELECT a FROM Alert a ORDER BY a.createdAt DESC",
                        Alert.class
                )
                .getResultList();
    }

    @TransactionAttribute(
            TransactionAttributeType.SUPPORTS
    )
    public boolean existsUnresolvedAlert(
            AlertType type,
            String message
    ) {

        Long count =
                entityManager
                        .createQuery(
                                """
                                SELECT COUNT(a)
                                FROM Alert a
                                WHERE a.type = :type
                                AND a.message = :message
                                AND a.resolved = false
                                """,
                                Long.class
                        )
                        .setParameter(
                                "type",
                                type
                        )
                        .setParameter(
                                "message",
                                message
                        )
                        .getSingleResult();

        return count > 0;
    }
}
