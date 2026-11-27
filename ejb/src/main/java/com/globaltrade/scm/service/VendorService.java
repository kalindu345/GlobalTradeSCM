package com.globaltrade.scm.service;

import com.globaltrade.scm.entity.Vendor;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.globaltrade.scm.interceptor.binding.Audited;

import java.util.List;

@Audited
@Stateless
public class VendorService {

    @PersistenceContext(
            unitName = "GlobalTradePU"
    )
    private EntityManager entityManager;

    @TransactionAttribute(
            TransactionAttributeType.REQUIRED
    )
    public Vendor createVendor(
            Vendor vendor
    ) {

        entityManager.persist(vendor);

        return vendor;
    }

    @TransactionAttribute(
            TransactionAttributeType.SUPPORTS
    )
    public List<Vendor> findAll() {

        return entityManager
                .createQuery(
                        "SELECT v FROM Vendor v ORDER BY v.id",
                        Vendor.class
                )
                .getResultList();
    }

    @TransactionAttribute(
            TransactionAttributeType.SUPPORTS
    )
    public Vendor findById(
            Long id
    ) {

        return entityManager.find(
                Vendor.class,
                id
        );
    }
}
