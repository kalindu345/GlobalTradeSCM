package com.globaltrade.scm.timer;

import com.globaltrade.scm.service.InventoryMonitoringService;

import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.logging.Logger;

@Singleton
@Startup
public class InventoryMonitoringTimer {

    @PostConstruct
    public void initialize() {

        LOGGER.info(
                "InventoryMonitoringTimer initialized"
        );
    }


    private static final Logger LOGGER =
            Logger.getLogger(
                    InventoryMonitoringTimer.class.getName()
            );

    @EJB
    private InventoryMonitoringService
            inventoryMonitoringService;

    @Schedule(
            hour = "*",
            minute = "*/2",
            second = "0",
            persistent = false
    )
    public void monitorInventory() {

        LOGGER.info(
                "Inventory monitoring started"
        );

        try {

            int count =
                    inventoryMonitoringService
                            .processLowStockItems();

            LOGGER.info(
                    "Inventory monitoring completed successfully. "
                            + "Low-stock items detected: "
                            + count
            );

        } catch (Exception exception) {

            LOGGER.severe(
                    "Inventory monitoring failed. "
                            + "Reason: "
                            + exception.getMessage()
            );
        }
    }
    @PreDestroy
    public void shutdown() {

        LOGGER.info(
                "InventoryMonitoringTimer shutting down"
        );
    }
}
