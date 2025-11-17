package com.julian.tickets.repository;

import com.julian.tickets.model.Ticket;
import com.julian.tickets.model.TicketStatus;
import com.julian.tickets.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID>, JpaSpecificationExecutor<Ticket> {
    List<Ticket> findByStatus(TicketStatus status);

    List<Ticket> findByUserId_Id(UUID userId);

    List<Ticket> findByUser_IdAndStatus(UUID userId, TicketStatus status);

}
