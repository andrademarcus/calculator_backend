package com.truenorth.calculator.service;

import com.truenorth.calculator.persistence.model.User;
import com.truenorth.calculator.response.UserDetailsImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    User findByUsername(String username) throws UsernameNotFoundException;
    User save(User user);

}
