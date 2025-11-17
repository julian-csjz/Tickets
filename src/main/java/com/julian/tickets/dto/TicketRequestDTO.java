package com.julian.tickets.dto;

import com.julian.tickets.model.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "DTO to create or update a ticket")
public class TicketRequestDTO {

    @Schema(description = "Description of ticket",
            example = "Error al iniciar sesión del usuario",
            maxLength = 500)
    @NotBlank(message = "Field 'description' is mandatory")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Schema(description = "ID of the user who created the ticket",
            example = "a3f5c1d2-9b31-4e86-8b11-f92edc423001")
    @NotNull(message = "Field 'User Id' is mandatory")
    private UUID userId;

    @Schema(description = "Status of ticket",
            example = "OPEN",
            allowableValues = {"OPEN", "CLOSED"})
    @NotNull(message = "Field 'Status' is mandatory")
    private TicketStatus status;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
