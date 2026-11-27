package com.globaltrade.scm.service;

import com.globaltrade.scm.entity.CustomsDocument;
import com.globaltrade.scm.entity.Shipment;

import com.globaltrade.scm.enums.CustomsStatus;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.globaltrade.scm.security.SecurityRoles;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDateTime;

@Stateless
public class CustomsService {

    @EJB
    private CustomsTimerService
            customsTimerService;

    @PersistenceContext(
            unitName = "GlobalTradePU"
    )
    private EntityManager entityManager;


    @TransactionAttribute(
            TransactionAttributeType.MANDATORY
    )
    public CustomsDocument createDocument(
            Shipment shipment,
            String documentType,
            String referenceNumber,
            LocalDateTime deadline
    ) {

        CustomsDocument document =
                new CustomsDocument();

        document.setShipment(shipment);

        document.setDocumentType(
                documentType
        );

        document.setReferenceNumber(
                referenceNumber
        );

        document.setDeadline(
                deadline
        );

        entityManager.persist(document);
        entityManager.flush();

        customsTimerService.scheduleDeadlineTimer(
                document.getId(),
                deadline
        );

        return document;
    }

    @RolesAllowed(
            SecurityRoles.CUSTOMS_AGENT
    )
    @TransactionAttribute(
            TransactionAttributeType.REQUIRED
    )
    public CustomsDocument approveDocument(
            Long documentId
    ) {

        CustomsDocument document =
                entityManager.find(
                        CustomsDocument.class,
                        documentId
                );

        if (document == null) {

            throw new IllegalArgumentException(
                    "Customs document not found: "
                            + documentId
            );
        }

        document.setStatus(
                CustomsStatus.APPROVED
        );

        return document;
    }
}
