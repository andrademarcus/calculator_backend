package com.demo.calculator.operation;

import com.demo.calculator.enums.OperationType;
import com.demo.calculator.request.OperationFormRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.calculator.response.OperationResponse;
import com.demo.calculator.security.JwtUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Transactional
public class CalculatorControllerTest {

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
    public void invokeCalculatorAdditionSuccess() throws Exception {

        OperationFormRequest formRequest = new OperationFormRequest();
        formRequest.setOperationType(OperationType.ADDITION);
        formRequest.setNumber1(1D);
        formRequest.setNumber2(1D);
        assertTrue(request(formRequest).getOperationResults() == 2);

    }

    @Test
    public void invokeCalculatorSubtractionSuccess() throws Exception {

        OperationFormRequest formRequest = new OperationFormRequest();
        formRequest.setOperationType(OperationType.SUBSTRACTION);
        formRequest.setNumber1(1D);
        formRequest.setNumber2(1D);
        assertTrue(request(formRequest).getOperationResults() == 0);

    }

    @Test
    public void invokeCalculatorDivisionSuccess() throws Exception {

        OperationFormRequest formRequest = new OperationFormRequest();
        formRequest.setOperationType(OperationType.DIVISION);
        formRequest.setNumber1(10D);
        formRequest.setNumber2(2D);
        assertTrue(request(formRequest).getOperationResults() == 5);

    }

    @Test
    public void invokeCalculatorSquareRootSuccess() throws Exception {

        OperationFormRequest formRequest = new OperationFormRequest();
        formRequest.setOperationType(OperationType.SQUARE_ROOT);
        formRequest.setNumber1(4D);
        assertTrue(request(formRequest).getOperationResults() == 2);

    }

    @Test
    public void invokeCalculatorRandomStringSuccess() throws Exception {

        OperationFormRequest formRequest = new OperationFormRequest();
        formRequest.setOperationType(OperationType.RANDOM_STRING);
        assertTrue(request(formRequest).getOperationResultsString() != null && !request(formRequest).getOperationResultsString().equals(""));

    }

    @Test
    public void invokeCalculatorMultiplicationSuccess() throws Exception {

        OperationFormRequest formRequest = new OperationFormRequest();
        formRequest.setOperationType(OperationType.MULTIPLICATION);
        formRequest.setNumber1(2D);
        formRequest.setNumber2(2D);
        assertTrue(request(formRequest).getOperationResults() == 4);

    }


    @Test
    public void invokeCalculatorAdditionError() throws Exception {

        OperationFormRequest formRequest = new OperationFormRequest();
        formRequest.setOperationType(OperationType.ADDITION);
        formRequest.setNumber1(1D);
        formRequest.setNumber2(1D);
        assertFalse(request(formRequest).getOperationResults() != 2);

    }

    @Test
    public void invokeCalculatorSubtractionError() throws Exception {

        OperationFormRequest formRequest = new OperationFormRequest();
        formRequest.setOperationType(OperationType.SUBSTRACTION);
        formRequest.setNumber1(1D);
        formRequest.setNumber2(1D);
        assertFalse(request(formRequest).getOperationResults() != 0);

    }

    @Test
    public void invokeCalculatorDivisionError() throws Exception {

        OperationFormRequest formRequest = new OperationFormRequest();
        formRequest.setOperationType(OperationType.DIVISION);
        formRequest.setNumber1(10D);
        formRequest.setNumber2(2D);
        assertFalse(request(formRequest).getOperationResults() != 5);

    }

    @Test
    public void invokeCalculatorSquareRootError() throws Exception {

        OperationFormRequest formRequest = new OperationFormRequest();
        formRequest.setOperationType(OperationType.SQUARE_ROOT);
        formRequest.setNumber1(4D);
        assertFalse(request(formRequest).getOperationResults() != 2);

    }



    @Test
    public void invokeCalculatorMultiplicationError() throws Exception {

        OperationFormRequest formRequest = new OperationFormRequest();
        formRequest.setOperationType(OperationType.MULTIPLICATION);
        formRequest.setNumber1(2D);
        formRequest.setNumber2(2D);
        assertFalse(request(formRequest).getOperationResults() != 4);

    }

    private OperationResponse request(OperationFormRequest formRequest) throws Exception {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("marcusandrade816@gmail.com", "Myself00"));

        assertThat(authentication).isNotNull();
        String token = jwtTokenProvider.generateJwtToken(authentication);
        assertThat(token).isNotNull();



        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        MvcResult result = mockMvc.perform(post("/api/v1/operations/calculator")
                        .content(mapper.writeValueAsBytes(formRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        OperationResponse response = new ObjectMapper().readValue(json, OperationResponse.class);

        return response;


    }




}
