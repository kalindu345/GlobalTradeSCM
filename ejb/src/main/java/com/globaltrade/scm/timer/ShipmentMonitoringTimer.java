package com.globaltrade.scm.timer;

import com.globaltrade.scm.service.ShipmentMonitoringService;

import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.util.logging.Logger;

@Singleton
@Startup
public class ShipmentMonitoringTimer {
    public static final Logger LOGGER =
            Logger.getLogger(
                    ShipmentMonitoringTimer.class.getName()
            );

    @PostConstruct
    public void initialize() {

        LOGGER.info(
                "ShipmentMonitoringTimer initialized"
        );
    }


    @EJB
    private ShipmentMonitoringService
            shipmentMonitoringService;

    @Schedule(
            hour = "*",
            minute = "*/1",
            second = "0",
            persistent = false
    )
    public void monitorDelayedShipments() {

        long startTime =
                System.nanoTime();

        try {

            int count =
                    shipmentMonitoringService
                            .processDelayedShipments();

            long durationMs =
                    (
                            System.nanoTime()
                                    - startTime
                    ) / 1_000_000;

            LOGGER.info(
                    "Shipment monitoring completed. "
                            + "Updated: "
                            + count
                            + ", execution time: "
                            + durationMs
                            + " ms"
            );

        } catch (Exception exception) {

            LOGGER.severe(
                    "Shipment monitoring failed: "
                            + exception.getMessage()
            );
        }
    }

    @PreDestroy
    public void shutdown() {

        LOGGER.info(
                "ShipmentMonitoringTimer shutting down"
        );
    }
}
