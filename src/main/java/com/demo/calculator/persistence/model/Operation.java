package com.demo.calculator.persistence.model;

import com.demo.calculator.enums.OperationType;
import com.demo.calculator.enums.converter.OperationTypeConverter;

import javax.persistence.*;

@Entity
@Table(name = "operation")
public class Operation extends BaseModel {

    @Id
    @Column(name = "id_operation")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Convert(converter = OperationTypeConverter.class)
    private OperationType type;

    @Column(name = "cost")
    private Double cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
