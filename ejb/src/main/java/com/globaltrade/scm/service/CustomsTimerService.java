package com.globaltrade.scm.service;

import com.globaltrade.scm.entity.CustomsDocument;
import com.globaltrade.scm.enums.AlertSeverity;
import com.globaltrade.scm.enums.AlertType;
import com.globaltrade.scm.enums.CustomsStatus;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timeout;
import jakarta.ejb.Timer;
import jakarta.ejb.TimerConfig;
import jakarta.ejb.TimerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;


@Singleton
@Startup
public class CustomsTimerService {
    private static final Logger LOGGER =
            Logger.getLogger(
                    CustomsTimerService.class.getName()
            );


    @PersistenceContext(
            unitName = "GlobalTradePU"
    )
    private EntityManager entityManager;

    @Resource
    private TimerService timerService;

    @EJB
    private AlertService alertService;

    @PostConstruct
    public void initialize() {

        LOGGER.info(
                "CustomsTimerService initialized"
        );
    }

    public void scheduleDeadlineTimer(
            Long customsDocumentId,
            LocalDateTime deadline
    ) {

        Date timeoutDate =
                Date.from(
                        deadline
                                .atZone(
                                        ZoneId.systemDefault()
                                )
                                .toInstant()
                );

        TimerConfig config =
                new TimerConfig(
                        customsDocumentId,
                        true
                );

        timerService.createSingleActionTimer(
                timeoutDate,
                config
        );
    }

    @Timeout
    public void deadlineReached(
            Timer timer
    ) {

        try {

            Long documentId =
                    (Long) timer.getInfo();

            LOGGER.info(
                    "Processing customs deadline timer "
                            + "for document ID: "
                            + documentId
            );

            CustomsDocument document =
                    entityManager.find(
                            CustomsDocument.class,
                            documentId
                    );

            if (document == null) {

                LOGGER.warning(
                        "Customs document not found for timer. "
                                + "Document ID: "
                                + documentId
                );

                return;
            }

            if (document.getStatus()
                    == CustomsStatus.APPROVED) {

                LOGGER.info(
                        "Customs document already approved. "
                                + "No deadline action required. "
                                + "Document ID: "
                                + documentId
                );

                return;
            }

            document.setStatus(
                    CustomsStatus.EXPIRED
            );

            alertService.createAlert(
                    AlertType.CUSTOMS_DEADLINE,
                    AlertSeverity.CRITICAL,
                    "Customs deadline reached for document "
                            + document.getReferenceNumber(),
                    document.getShipment()
            );

            LOGGER.info(
                    "Customs document marked as EXPIRED. "
                            + "Document ID: "
                            + documentId
            );

        } catch (Exception exception) {

            LOGGER.severe(
                    "Customs deadline timer failed. "
                            + "Reason: "
                            + exception.getMessage()
            );
        }
    }

    @PreDestroy
    public void shutdown() {

        LOGGER.info(
                "CustomsTimerService shutting down"
        );
    }
}