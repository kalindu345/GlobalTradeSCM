package com.globaltrade.scm.interceptor;

import com.globaltrade.scm.interceptor.binding.PerformanceMonitored;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.util.logging.Logger;

@PerformanceMonitored
@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 30)
public class PerformanceInterceptor {

    private static final Logger LOGGER =
            Logger.getLogger(
                    PerformanceInterceptor
                            .class
                            .getName()
            );

    @PostConstruct
    public void initialize() {

        LOGGER.info(
                "PerformanceInterceptor initialized"
        );
    }

    @AroundInvoke
    public Object measurePerformance(
            InvocationContext context
    ) throws Exception {

        long startTime =
                System.nanoTime();

        try {

            return context.proceed();

        } finally {

            long executionTimeMs =
                    (
                            System.nanoTime()
                                    - startTime
                    ) / 1_000_000;

            LOGGER.info(
                    "Performance: "
                            + context
                            .getMethod()
                            .getDeclaringClass()
                            .getSimpleName()
                            + "."
                            + context
                            .getMethod()
                            .getName()
                            + " executed in "
                            + executionTimeMs
                            + " ms"
            );
        }
    }

    @PreDestroy
    public void shutdown() {

        LOGGER.info(
                "PerformanceInterceptor destroyed"
        );
    }
}