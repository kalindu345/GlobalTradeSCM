package com.globaltrade.scm.interceptor;

import com.globaltrade.scm.interceptor.binding.Audited;
import com.globaltrade.scm.service.AuditService;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Audited
@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 20)
public class AuditInterceptor {

    @Inject
    private AuditService auditService;

    @AroundInvoke
    public Object audit(
            InvocationContext context
    ) throws Exception {

        String className =
                context
                        .getTarget()
                        .getClass()
                        .getSimpleName();

        String methodName =
                context
                        .getMethod()
                        .getName();

        long startTime =
                System.nanoTime();

        try {

            Object result =
                    context.proceed();

            long executionTimeMs =
                    (
                            System.nanoTime()
                                    - startTime
                    ) / 1_000_000;

            auditService.saveAudit(
                    className,
                    methodName,
                    true,
                    executionTimeMs,
                    null
            );

            return result;

        } catch (Exception exception) {

            long executionTimeMs =
                    (
                            System.nanoTime()
                                    - startTime
                    ) / 1_000_000;

            auditService.saveAudit(
                    className,
                    methodName,
                    false,
                    executionTimeMs,
                    exception.getMessage()
            );

            throw exception;
        }
    }
}
