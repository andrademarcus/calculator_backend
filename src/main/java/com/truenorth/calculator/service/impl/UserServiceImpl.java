package com.truenorth.calculator.service.impl;

import com.truenorth.calculator.enums.Status;
import com.truenorth.calculator.persistence.model.User;
import com.truenorth.calculator.persistence.repository.UserRepository;
import com.truenorth.calculator.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username, Status.ACTIVE)
                .orElseThrow(() -> new EntityExistsException("User " + username + " not found"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
