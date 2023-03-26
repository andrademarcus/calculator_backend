package com.demo.calculator.userRecord;

import com.demo.calculator.service.AuthenticationFacade;
import com.demo.calculator.service.UserRecordService;
import com.demo.calculator.controller.UserRecordController;
import com.demo.calculator.persistence.repository.UserRecordRepository;
import com.demo.calculator.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

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
