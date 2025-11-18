package com.julian.tickets.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julian.tickets.config.security.JwtUtil;
import com.julian.tickets.dto.LoginRequestDto;
import com.julian.tickets.dto.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("intUser2");
        dto.setPassword("Tester");

        String created = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        jwtToken = jwtUtil.create("intUser2");
    }

    @Test
    void createAndGetUser_flow() throws Exception {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("CreateTest");
        dto.setLastname("User");

        String resp = mockMvc.perform(post("/users")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("CreateTest"))
                .andReturn().getResponse().getContentAsString();

        JsonNode node = objectMapper.readTree(resp);
        String id = node.get("id").asText();

        mockMvc.perform(get("/users/" + id)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("CreateTest"));
    }

    @Test
    void updateUser_flow() throws Exception {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("UpdTest");
        dto.setLastname("User");

        String resp = mockMvc.perform(post("/users")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(resp).get("id").asText();

        dto.setName("UpdatedName");
        mockMvc.perform(put("/users/" + id)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedName"));
    }



}
