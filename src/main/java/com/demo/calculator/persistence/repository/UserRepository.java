package com.demo.calculator.persistence.repository;

import com.demo.calculator.enums.Status;
import com.demo.calculator.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select e from User e where username like trim(?1) and status = ?2")
    Optional<User> findByUsername(String username, Status status);

}
