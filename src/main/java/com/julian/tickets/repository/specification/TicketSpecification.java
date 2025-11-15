package com.julian.tickets.repository.specification;

import com.julian.tickets.model.Ticket;
import com.julian.tickets.model.TicketStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class TicketSpecification {
    public static Specification<Ticket> filter(TicketStatus status, UUID usuarioId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (usuarioId != null) {
                predicates.add(cb.equal(root.get("user").get("id"), usuarioId));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
