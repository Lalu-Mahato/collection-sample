package com.example.collectionsample.collection.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "collections")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String compositeKey;
    private String bmId;
    private Double dpd;
    private Double dpdAmount;
    private Double emiAmount;
    private Double emiCollected;
    private Double osBalance;
    private String emiStatus;
    private String foId;
    private String groupId;
    private Double intEmiAmount;
    private long loanAccNumber;
    private Double loanAmount;
    private String productType;
    private String prospectId;
    private String rescheduleReason;
    private Date rescheduleDate;
    private Date emiDueDate;
    private Date loanEndDate;
    private Date loanStartDate;
    private Date pttransactionDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
