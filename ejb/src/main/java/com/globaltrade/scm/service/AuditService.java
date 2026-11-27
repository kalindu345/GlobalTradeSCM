package com.globaltrade.scm.service;

import com.globaltrade.scm.entity.AuditLog;


import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless

public class AuditService {

    @PersistenceContext(
            unitName = "GlobalTradePU"
    )
    private EntityManager entityManager;

    @TransactionAttribute(
            TransactionAttributeType.REQUIRES_NEW
    )
    public void saveAudit(
            String className,
            String methodName,
            boolean successful,
            long executionTimeMs,
            String errorMessage
    ) {

        AuditLog log =
                new AuditLog();

        log.setClassName(
                className
        );

        log.setMethodName(
                methodName
        );

        log.setSuccessful(
                successful
        );

        log.setExecutionTimeMs(
                executionTimeMs
        );

        log.setErrorMessage(
                errorMessage
        );

        entityManager.persist(
                log
        );
    }

    @TransactionAttribute(
            TransactionAttributeType.SUPPORTS
    )
    public List<AuditLog> findAll() {

        return entityManager
                .createQuery(
                        """
                        SELECT a
                        FROM AuditLog a
                        ORDER BY a.createdAt DESC
                        """,
                        AuditLog.class
                )
                .getResultList();
    }
}
