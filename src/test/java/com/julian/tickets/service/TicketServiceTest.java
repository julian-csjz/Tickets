package com.julian.tickets.service;

import com.julian.tickets.dto.TicketRequestDTO;
import com.julian.tickets.dto.TicketResponseDTO;
import com.julian.tickets.exception.BadRequestException;
import com.julian.tickets.exception.ResourceNotFoundException;
import com.julian.tickets.model.Ticket;
import com.julian.tickets.model.TicketStatus;
import com.julian.tickets.model.User;
import com.julian.tickets.repository.TicketRepository;
import com.julian.tickets.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void findById_whenExists_returnsTicket() {
        UUID id = UUID.randomUUID();
        Ticket t = new Ticket();
        t.setId(id);
        t.setDescription("Error login");
        when(ticketRepository.findById(id)).thenReturn(Optional.of(t));

        TicketResponseDTO result = ticketService.getTicketById(id);

        assertNotNull(result);
        assertEquals("Error login", result.getDescription());
        verify(ticketRepository, times(1)).findById(id);
    }

    @Test
    void findById_whenNotExists_throwsResourceNotFound() {
        UUID id = UUID.randomUUID();
        when(ticketRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ticketService.getTicketById(id));
        verify(ticketRepository, times(1)).findById(id);
    }

    @Test
    void createTicket_whenUserExists_savesTicket() {
        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setId(userId);
        user.setName("User");
        user.setLastname("One");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        TicketRequestDTO toSave = new TicketRequestDTO();
        toSave.setDescription("Nueva falla");
        toSave.setUserId(user.getId());
        toSave.setStatus(TicketStatus.OPEN);

        Ticket saved = new Ticket();
        saved.setId(UUID.randomUUID());
        saved.setDescription("Nueva falla");
        saved.setUser(user);
        saved.setStatus(TicketStatus.OPEN);
        saved.setCreateDate(LocalDateTime.now());

        when(ticketRepository.save(any(Ticket.class))).thenReturn(saved);

        TicketResponseDTO result = ticketService.createTicket(toSave);

        assertNotNull(result.getId());
        assertEquals("Nueva falla", result.getDescription());
        verify(userRepository, times(1)).findById(userId);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void createTicket_whenUserNotExists_throwsResourceNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        TicketRequestDTO t = new TicketRequestDTO();
        User fakeUser = new User();
        fakeUser.setId(userId);
        t.setUserId(fakeUser.getId());
        t.setDescription("Desc");

        assertThrows(BadRequestException.class, () -> ticketService.createTicket(t));
    }

}
