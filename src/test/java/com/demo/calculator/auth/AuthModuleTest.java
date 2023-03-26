package com.demo.calculator.auth;

import com.demo.calculator.service.UserService;
import com.demo.calculator.controller.AuthController;
import com.demo.calculator.persistence.repository.UserRepository;
import com.demo.calculator.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthModuleTest {

    @Autowired(required = true)
    private AuthController authController;

    @Autowired(required = true)
    private AuthenticationManager authenticationManager;

    @Autowired(required = true)
    private PasswordEncoder encoder;

    @Autowired(required = true)
    private JwtUtil jwtUtil;

    @Autowired(required = true)
    private UserService userService;

    @Autowired(required = true)
    private UserRepository userRepository;

    @Test
    void modulesLoaded() {
        assertThat(authController).isNotNull();
        assertThat(authenticationManager).isNotNull();
        assertThat(encoder).isNotNull();
        assertThat(jwtUtil).isNotNull();
        assertThat(userService).isNotNull();
        assertThat(userRepository).isNotNull();
    }

}
