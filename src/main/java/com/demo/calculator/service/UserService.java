package com.demo.calculator.service;

import com.demo.calculator.persistence.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    User findByUsername(String username) throws UsernameNotFoundException;
    User save(User user);

}
