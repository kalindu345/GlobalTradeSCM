package com.globaltrade.scm.service;

import com.globaltrade.scm.security.SecurityRoles;

import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;

import java.security.Principal;

@Stateless
public class SecurityService {

    @Resource
    private SessionContext sessionContext;

    public String getCurrentUsername() {

        Principal principal =
                sessionContext
                        .getCallerPrincipal();

        if (principal == null) {
            return "anonymous";
        }

        return principal.getName();
    }

    public boolean isLogisticsCoordinator() {

        return sessionContext.isCallerInRole(
                SecurityRoles.LOGISTICS_COORDINATOR
        );
    }

    public boolean isCustomsAgent() {

        return sessionContext.isCallerInRole(
                SecurityRoles.CUSTOMS_AGENT
        );
    }

    public boolean isWarehouseManager() {

        return sessionContext.isCallerInRole(
                SecurityRoles.WAREHOUSE_MANAGER
        );
    }

    public boolean isVendorRepresentative() {

        return sessionContext.isCallerInRole(
                SecurityRoles.VENDOR_REPRESENTATIVE
        );
    }
}
