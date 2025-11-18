package com.julian.tickets.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julian.tickets.config.security.JwtUtil;
import com.julian.tickets.dto.LoginRequestDto;
import com.julian.tickets.dto.TicketRequestDTO;
import com.julian.tickets.dto.UserRequestDTO;
import com.julian.tickets.model.TicketStatus;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TicketIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private String jwtToken;
    private String userId;

    @BeforeEach
    void setUp() throws Exception {
        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setUsername("ticketUser");
        requestDto.setPassword("Owner");

        String jwtResp = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        jwtToken = jwtUtil.create("ticketUser");

        UserRequestDTO userDto = new UserRequestDTO();
        userDto.setName("Julian");
        userDto.setLastname("Casas");

        String userResp = mockMvc.perform(post("/users")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        userId = objectMapper.readTree(userResp).get("id").asText();

    }

    @Test
    void createAndGetTicket_flow() throws Exception {
        TicketRequestDTO ticket = new TicketRequestDTO();
        ticket.setDescription("Integracion - fallo X");
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setUserId(java.util.UUID.fromString(userId));

        String ticketResp = mockMvc.perform(post("/tickets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Integracion - fallo X"))
                .andReturn().getResponse().getContentAsString();

        String ticketId = objectMapper.readTree(ticketResp).get("id").asText();

        mockMvc.perform(get("/tickets/" + ticketId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticketId));
    }

    @Test
    void filterTickets_byUserAndStatus() throws Exception {
        TicketRequestDTO ticket = new TicketRequestDTO();
        ticket.setDescription("FilterTest");
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setUserId(java.util.UUID.fromString(userId));

        mockMvc.perform(post("/tickets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/tickets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .param("userId", userId)
                        .param("status", "OPEN"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String body = result.getResponse().getContentAsString();
                    assertThat(body).isNotBlank();
                });
    }

    @Test
    void updateAndDeleteTicket_flow() throws Exception {
        TicketRequestDTO ticket = new TicketRequestDTO();
        ticket.setDescription("ToUpdate");
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setUserId(java.util.UUID.fromString(userId));

        String ticketResp = mockMvc.perform(post("/tickets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String ticketId = objectMapper.readTree(ticketResp).get("id").asText();

        ticket.setDescription("UpdatedDesc");
        ticket.setStatus(TicketStatus.CLOSED);

        mockMvc.perform(put("/tickets/" + ticketId)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("UpdatedDesc"))
                .andExpect(jsonPath("$.status").value("CLOSED"));

        mockMvc.perform(delete("/tickets/" + ticketId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/tickets/" + ticketId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

}
