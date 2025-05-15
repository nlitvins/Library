package com.nlitvins.web_application.outbound.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@EntityListeners(AuditingEntityListener.class)
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "book_id", nullable = false)
    private int bookId;

    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "term_date", nullable = false)
    @JsonIgnore
    private LocalDateTime termDate;

    @Column(name = "status", nullable = false)
    @JsonIgnore
    private Short status;

    @Column(name = "extension_count", nullable = false)
    @JsonIgnore
    private Short extensionCount;

    @Column(name = "updated_date", nullable = false)
    @JsonIgnore
    @LastModifiedDate
    private LocalDateTime updatedDate;


    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setTermDate(LocalDateTime termDate) {
        this.termDate = termDate;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public void setExtensionCount(Short extensionCount) {
        this.extensionCount = extensionCount;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }


    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getTermDate() {
        return termDate;
    }

    public Short getStatus() {
        return status;
    }

    public Short getExtensionCount() {
        return extensionCount;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
}

