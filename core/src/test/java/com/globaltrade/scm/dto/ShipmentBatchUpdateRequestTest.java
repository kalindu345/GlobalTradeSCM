package com.globaltrade.scm.dto;

import com.globaltrade.scm.enums.ShipmentStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShipmentBatchUpdateRequestTest {

    @Test
    void shouldStoreShipmentIdsAndNewStatus() {

        ShipmentBatchUpdateRequest request =
                new ShipmentBatchUpdateRequest();

        request.setShipmentIds(
                List.of(1L, 2L, 3L)
        );

        request.setNewStatus(
                ShipmentStatus.IN_TRANSIT
        );

        assertEquals(
                List.of(1L, 2L, 3L),
                request.getShipmentIds()
        );

        assertEquals(
                ShipmentStatus.IN_TRANSIT,
                request.getNewStatus()
        );
    }
}
