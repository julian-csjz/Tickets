package com.julian.tickets.mapper;

import com.julian.tickets.dto.TicketRequestDTO;
import com.julian.tickets.dto.TicketResponseDTO;
import com.julian.tickets.model.Ticket;
import com.julian.tickets.model.User;

public class TicketMapper {

    //Entity to ResponseDTO
    public static TicketResponseDTO toResponseDTO(Ticket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setDescription(ticket.getDescription());
        dto.setUserId(ticket.getUser() != null ? ticket.getUser().getId() : null);
        dto.setStatus(ticket.getStatus());
        dto.setCreateDate(ticket.getCreateDate());
        dto.setUpdateDate(ticket.getUpdateDate());
        return dto;
    }

    //RequestDTO to Entity
    public static Ticket toEntity(TicketRequestDTO dto, User user) {
        Ticket ticket = new Ticket();
        ticket.setDescription(dto.getDescription());
        ticket.setUser(user);
        ticket.setStatus(dto.getStatus());
        return ticket;
    }

    public static void updateEntity(TicketRequestDTO dto, Ticket ticket, User userTicket) {
        ticket.setDescription(dto.getDescription());
        ticket.setStatus(dto.getStatus());
        ticket.setUser(userTicket);
    }
}
