package com.truenorth.calculator.userRecord;

import com.truenorth.calculator.controller.AuthController;
import com.truenorth.calculator.controller.UserRecordController;
import com.truenorth.calculator.persistence.repository.UserRecordRepository;
import com.truenorth.calculator.persistence.repository.UserRepository;
import com.truenorth.calculator.security.JwtUtil;
import com.truenorth.calculator.service.AuthenticationFacade;
import com.truenorth.calculator.service.UserRecordService;
import com.truenorth.calculator.service.UserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRecordModuleTest {

    @Autowired(required = true)
    private UserRecordController userRecordController;

    @Autowired(required = true)
    private UserRecordService userRecordService;

    @Autowired(required = true)
    private ModelMapper modelMapper;

    @Autowired(required = true)
    private UserRecordRepository userRecordRepository;

    @Autowired(required = true)
    private UserRepository userRepository;

    @Autowired(required = true)
    private MessageSource messageSource;

    @Autowired(required = true)
    private AuthenticationFacade authenticationFacade;

    @Test
    void modulesLoaded() {
        assertThat(userRecordController).isNotNull();
        assertThat(userRecordService).isNotNull();
        assertThat(modelMapper).isNotNull();
        assertThat(userRecordRepository).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(messageSource).isNotNull();
        assertThat(authenticationFacade).isNotNull();
    }

}
