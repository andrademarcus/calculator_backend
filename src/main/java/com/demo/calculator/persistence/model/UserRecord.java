package com.demo.calculator.persistence.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "user_record")
public class UserRecord extends BaseModel {

    @Id
    @Column(name = "id_record")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_operation")
    private Operation operation;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_user_deleted")
    private User userDeleted;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "user_balance")
    private Double userBalance;

    @Column(name = "operation_response")
    private String operationResponse;

    @Column(name = "date_inserted")
    private LocalDateTime date;

    @Column(name = "date_inserted_uts")
    private Long dateUts;

    @Column(name = "date_deleted")
    private LocalDateTime dateDeleted;

    @Column(name = "date_deleted_uts")
    private Long dateDeletedUts;

    @Column(name = "success")
    private boolean success;

    @Column(name = "deleted")
    private boolean deleted;

    @PreUpdate
    @PrePersist
    public void preUpdate() {
        if (this.date != null) {
            this.dateUts = this.date.toEpochSecond(ZoneOffset.UTC);
        }
        if (this.dateDeleted != null) {
            this.dateDeletedUts = this.dateDeleted.toEpochSecond(ZoneOffset.UTC);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserDeleted() {
        return userDeleted;
    }

    public void setUserDeleted(User userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Double userBalance) {
        this.userBalance = userBalance;
    }

    public String getOperationResponse() {
        return operationResponse;
    }

    public void setOperationResponse(String operationResponse) {
        this.operationResponse = operationResponse;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getDateUts() {
        return dateUts;
    }

    public void setDateUts(Long dateUts) {
        this.dateUts = dateUts;
    }

    public LocalDateTime getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(LocalDateTime dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getDateDeletedUts() {
        return dateDeletedUts;
    }

    public void setDateDeletedUts(Long dateDeletedUts) {
        this.dateDeletedUts = dateDeletedUts;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
