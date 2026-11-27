package com.globaltrade.scm.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            name = "class_name",
            nullable = false,
            length = 150
    )
    private String className;

    @Column(
            name = "method_name",
            nullable = false,
            length = 150
    )
    private String methodName;

    @Column(
            nullable = false
    )
    private Boolean successful;

    @Column(
            name = "execution_time_ms"
    )
    private Long executionTimeMs;

    @Column(
            name = "error_message",
            length = 1000
    )
    private String errorMessage;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @PrePersist
    public void beforeInsert() {

        if (createdAt == null) {
            createdAt =
                    LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(
            String className
    ) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(
            String methodName
    ) {
        this.methodName = methodName;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(
            Boolean successful
    ) {
        this.successful =
                successful;
    }

    public Long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(
            Long executionTimeMs
    ) {
        this.executionTimeMs =
                executionTimeMs;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(
            String errorMessage
    ) {
        this.errorMessage =
                errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
