package com.demo.calculator.persistence.repository;

import com.demo.calculator.persistence.model.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRecordRepository extends JpaRepository<UserRecord, Long>,
        JpaSpecificationExecutor<UserRecord> {

    @Query("select e from UserRecord e where e.deleted = false and e.success = true and e.user.id = ?1 and e.operation is not null and id = ?2")
    Optional<UserRecord> find(Long userId, Long id);

}
