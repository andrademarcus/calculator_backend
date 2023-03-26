package com.demo.calculator.userRecord;

import com.demo.calculator.security.JwtUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Transactional
public class UserRecordControllerTest {

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    JwtUtil jwtTokenProvider;

    private MockMvc mockMvc;

    @BeforeAll
    public void init(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    public void findUserRecordsAuthSuccess() throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("marcusandrade816@gmail.com", "Myself00"));

        assertThat(authentication).isNotNull();
        String token = jwtTokenProvider.generateJwtToken(authentication);
        assertThat(token).isNotNull();

        mockMvc.perform(get("/api/v1/userRecord/findAll")
                        .header("Authorization", "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserRecordSuccess() throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("marcusandrade816@gmail.com", "Myself00"));

        assertThat(authentication).isNotNull();
        String token = jwtTokenProvider.generateJwtToken(authentication);
        assertThat(token).isNotNull();

        mockMvc.perform(delete("/api/v1/userRecord/delete/95")
                        .header("Authorization", "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void findUserRecordsAuthError() throws Exception {

        try {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("usernotfound", "test"));

            assertThat(authentication).isNotNull();
            String token = jwtTokenProvider.generateJwtToken(authentication);
            assertThat(token).isNotNull();

            mockMvc.perform(get("/api/v1/userRecord/findAll")
                            .header("Authorization", "Bearer " + token))
                    .andDo(MockMvcResultHandlers.print());
            //.andExpect(status().is4xxClientError());

        } catch (BadCredentialsException e) {
            assertTrue(e instanceof BadCredentialsException);
        }

    }



}
