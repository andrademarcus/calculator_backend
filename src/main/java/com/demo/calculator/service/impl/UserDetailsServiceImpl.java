package com.demo.calculator.service.impl;

import com.demo.calculator.enums.Status;
import com.demo.calculator.persistence.repository.UserRepository;
import com.demo.calculator.response.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service used by web security to lookup a specific user for login authentication
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username, Status.ACTIVE)
                .map(user -> new UserDetailsImpl(user.getId(), user.getName(),
                        user.getUsername(), user.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found or it's inactive"));
    }

}
