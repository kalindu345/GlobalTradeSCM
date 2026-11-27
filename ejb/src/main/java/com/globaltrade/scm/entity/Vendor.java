package com.globaltrade.scm.entity;

import com.globaltrade.scm.enums.VendorStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            length = 150
    )
    private String name;

    @Column(
            nullable = false,
            length = 100
    )
    private String country;

    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String email;

    @Column(name = "performance_score")
    private Double performanceScore;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VendorStatus status;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @PrePersist
    public void beforeInsert() {

        if (status == null) {
            status = VendorStatus.ACTIVE;
        }

        if (performanceScore == null) {
            performanceScore = 100.0;
        }

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(
            String country
    ) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email
    ) {
        this.email = email;
    }

    public Double getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(
            Double performanceScore
    ) {
        this.performanceScore =
                performanceScore;
    }

    public VendorStatus getStatus() {
        return status;
    }

    public void setStatus(
            VendorStatus status
    ) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
