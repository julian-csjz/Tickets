package com.julian.tickets.service;

import com.julian.tickets.dto.TicketRequestDTO;
import com.julian.tickets.dto.TicketResponseDTO;
import com.julian.tickets.exception.BadRequestException;
import com.julian.tickets.exception.ResourceNotFoundException;
import com.julian.tickets.mapper.TicketMapper;
import com.julian.tickets.model.Ticket;
import com.julian.tickets.model.TicketStatus;
import com.julian.tickets.model.User;
import com.julian.tickets.repository.TicketRepository;
import com.julian.tickets.repository.UserRepository;
import com.julian.tickets.repository.specification.TicketSpecification;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class TicketServiceImpl implements TicketService{
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    @CacheEvict(cacheNames = "TicketsPerUser", allEntries = true)
    public TicketResponseDTO createTicket(TicketRequestDTO dto) {
        User usuario = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BadRequestException("The specified user doesn't exist"));

        Ticket ticket = TicketMapper.toEntity(dto, usuario);
        ticketRepository.save(ticket);
        return TicketMapper.toResponseDTO(ticket);
    }

    @Override
    @CacheEvict(cacheNames = "TicketsPerUser", allEntries = true)
    public TicketResponseDTO updateTicket(UUID id, TicketRequestDTO dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket wit id " + id + "not found"));

        User userTicket = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User wit id " + dto.getUserId() + "not found"));

        TicketMapper.updateEntity(dto, ticket, userTicket);
        return TicketMapper.toResponseDTO(ticket);
    }

    @Override
    @CacheEvict(cacheNames = "TicketsPerUser", allEntries = true)
    public void deleteTicket(UUID id) {
        ticketRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = "TicketsPerUser", key = "#id")
    public TicketResponseDTO getTicketById(UUID id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket wit id " + id + "not found"));
        return TicketMapper.toResponseDTO(ticket);
    }

    @Override
    @Cacheable(cacheNames = "TicketsPerUser", key = "{#userId, #status}")
    public Page<TicketResponseDTO> getTickets(UUID userId, String status, Pageable pageable) {
        TicketStatus ticketStatus = null;
        if (status != null) {
            ticketStatus = TicketStatus.valueOf(status.toUpperCase());
        }

        Specification<Ticket> spec = TicketSpecification.filter(ticketStatus, userId);

        return ticketRepository.findAll(spec, pageable)
                .map(TicketMapper::toResponseDTO);
    }
}
