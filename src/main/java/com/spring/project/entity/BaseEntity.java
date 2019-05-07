package com.spring.project.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class BaseEntity<U> implements Serializable {

    /**
     * For CreatedBy and ModifiedBy:
     * We can use Type as per business requirement or simply we can use ID or any unique attribute of User present
     * in SecurityContextHolder as Principal User.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long ID;

    @CreatedBy
    @Column(name = "CreatedBy")
    private U createdBy;

    @CreatedDate
    @Column(name = "CreatedDate", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "ModifiedBy")
    private U modifiedBy;

    @LastModifiedDate
    @Column(name = "ModifiedDate")
    private LocalDateTime modifiedDate;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public U getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(U modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}