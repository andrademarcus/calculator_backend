package com.demo.calculator.persistence.repository;

import com.demo.calculator.enums.OperationType;
import com.demo.calculator.persistence.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("select e from Operation e where e.type = ?1")
    Optional<Operation> findByType(OperationType type);

}
