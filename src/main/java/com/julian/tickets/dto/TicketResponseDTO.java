package com.julian.tickets.dto;

import com.julian.tickets.model.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Ticket response DTO")
public class TicketResponseDTO {

    @Schema(description = "Unique ticket ID", example = "e9b1a67d-4f21-4c9b-9a22-12ab34cd5001")
    private UUID id;

    @Schema(description = "Description of ticket", example = "Error al iniciar sesión")
    private String description;

    @Schema(description = "User ID of the person who created the ticket", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID userId;

    @Schema(description = "Status of ticket", example = "OPEN")
    private TicketStatus status;

    @Schema(description = "Create date of ticket", example = "2025-11-15T22:00:00")
    private LocalDateTime createDate;

    @Schema(description = "Update date of ticket", example = "2025-11-15T22:00:00")
    private LocalDateTime updateDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
