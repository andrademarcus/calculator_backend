package com.demo.calculator.persistence.specification;

import com.demo.calculator.persistence.model.Operation;
import com.demo.calculator.persistence.model.UserRecord;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.ZoneId;

public class UserRecordSpecification {

    public static Specification<UserRecord> filterByUser(Long userId) {
        return (root, query, builder) -> builder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<UserRecord> filterByOperation(Operation operation) {
        return (root, query, builder) -> builder.equal(root.get("operation"), operation);
    }

    public static Specification<UserRecord> filterByValid() {
        return (root, query, builder) -> builder.equal(root.get("deleted"), false);
    }

    public static Specification<UserRecord> filterByDateStart(LocalDate date) {
        return (root, query, builder) -> builder.ge(root.get("dateUts"), date.atTime(0, 0, 0)
                .atZone(ZoneId.systemDefault())
                .toEpochSecond());
    }

    public static Specification<UserRecord> filterByDateEnd(LocalDate date) {
        return (root, query, builder) -> builder.le(root.get("dateUts"), date.atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault()).toEpochSecond());
    }

}
