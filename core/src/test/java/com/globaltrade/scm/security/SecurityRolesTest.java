package com.globaltrade.scm.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecurityRolesTest {

    @Test
    void shouldContainCorrectRoleNames() {

        assertEquals(
                "LOGISTICS_COORDINATOR",
                SecurityRoles.LOGISTICS_COORDINATOR
        );

        assertEquals(
                "CUSTOMS_AGENT",
                SecurityRoles.CUSTOMS_AGENT
        );

        assertEquals(
                "WAREHOUSE_MANAGER",
                SecurityRoles.WAREHOUSE_MANAGER
        );

        assertEquals(
                "VENDOR_REPRESENTATIVE",
                SecurityRoles.VENDOR_REPRESENTATIVE
        );
    }
}
