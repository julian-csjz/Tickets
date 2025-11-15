package com.julian.tickets.service;

import com.julian.tickets.dto.TicketRequestDTO;
import com.julian.tickets.dto.TicketResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TicketService {
    TicketResponseDTO createTicket(TicketRequestDTO dto);

    TicketResponseDTO updateTicket(UUID id, TicketRequestDTO dto);

    void deleteTicket(UUID id);

    TicketResponseDTO getTicketById(UUID id);

    Page<TicketResponseDTO> getTickets(UUID usuarioId, String status, Pageable pageable);
}
