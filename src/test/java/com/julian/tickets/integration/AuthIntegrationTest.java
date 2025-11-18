package com.julian.tickets.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julian.tickets.dto.LoginRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private LoginRequestDto registerRequestDto;

    @BeforeEach
    void beforeEach() {
        registerRequestDto = new LoginRequestDto();
        registerRequestDto.setUsername("intUser");
        registerRequestDto.setPassword("Tester");
    }

    @Test
    void registerAndLogin_returnsJwtInResponse() throws Exception {
        String registerJson = objectMapper.writeValueAsString(registerRequestDto);
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerJson))
                .andExpect(status().isCreated());

        LoginRequestDto login = new LoginRequestDto();

        login.setUsername("intUser");
        login.setPassword("Tester");

        String loginJson = objectMapper.writeValueAsString(login);

        var mvcResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();

        String authHeader = mvcResult.getResponse().getHeader("Authorization");
        assertThat(authHeader != null || (responseBody != null && !responseBody.isBlank())).isTrue();
    }
}
