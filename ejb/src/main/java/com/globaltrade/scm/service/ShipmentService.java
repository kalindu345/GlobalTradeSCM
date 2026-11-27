package com.globaltrade.scm.service;

import com.globaltrade.scm.dto.ShipmentProcessRequest;
import com.globaltrade.scm.entity.Shipment;
import com.globaltrade.scm.entity.Vendor;
import com.globaltrade.scm.enums.ShipmentStatus;
import com.globaltrade.scm.exception.InsufficientInventoryException;
import com.globaltrade.scm.exception.InventoryItemNotFoundException;
import com.globaltrade.scm.exception.VendorNotFoundException;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.globaltrade.scm.interceptor.binding.Audited;
import com.globaltrade.scm.interceptor.binding.PerformanceMonitored;
import com.globaltrade.scm.interceptor.binding.Validated;

import com.globaltrade.scm.security.SecurityRoles;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Stateless
@DeclareRoles({
        SecurityRoles.LOGISTICS_COORDINATOR,
        SecurityRoles.CUSTOMS_AGENT,
        SecurityRoles.WAREHOUSE_MANAGER,
        SecurityRoles.VENDOR_REPRESENTATIVE
})

@TransactionManagement(
        TransactionManagementType.CONTAINER
)

public class ShipmentService {

    @PersistenceContext(
            unitName = "GlobalTradePU"
    )
    private EntityManager entityManager;

    @EJB
    private InventoryService inventoryService;

    @EJB
    private CustomsService customsService;

    @Validated
    @Audited
    @PerformanceMonitored
    @RolesAllowed(
            SecurityRoles.LOGISTICS_COORDINATOR
    )
    @TransactionAttribute(
            TransactionAttributeType.REQUIRED
    )
    public Shipment processShipment(
            ShipmentProcessRequest request
    )
            throws VendorNotFoundException,
            InventoryItemNotFoundException,
            InsufficientInventoryException {

        Vendor vendor =
                entityManager.find(
                        Vendor.class,
                        request.getVendorId()
                );

        if (vendor == null) {

            throw new VendorNotFoundException(
                    request.getVendorId()
            );
        }

        Shipment shipment =
                new Shipment();

        shipment.setTrackingNumber(
                request.getTrackingNumber()
        );

        shipment.setOrigin(
                request.getOrigin()
        );

        shipment.setDestination(
                request.getDestination()
        );

        shipment.setExpectedDelivery(
                request.getExpectedDelivery()
        );

        shipment.setStatus(
                ShipmentStatus.CREATED
        );

        shipment.setVendor(vendor);

        entityManager.persist(
                shipment
        );

        inventoryService.reserveInventory(
                request.getInventoryItemId(),
                request.getRequestedQuantity()
        );

        customsService.createDocument(
                shipment,
                request.getCustomsDocumentType(),
                request.getCustomsReferenceNumber(),
                request.getCustomsDeadline()
        );

        return shipment;
    }

    @TransactionAttribute(
            TransactionAttributeType.SUPPORTS
    )
    public List<Shipment> findAll() {

        return entityManager
                .createQuery(
                        "SELECT s FROM Shipment s ORDER BY s.id",
                        Shipment.class
                )
                .getResultList();
    }
}
