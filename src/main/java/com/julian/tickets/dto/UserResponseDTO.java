package com.julian.tickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "User response DTO")
public class UserResponseDTO {
    @Schema(description = "Unique user ID", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    private UUID id;

    @Schema(description = "Name of user", example = "Camilo")
    private String name;

    @Schema(description = "Lastname of user", example = "Gómez")
    private String lastname;

    @Schema(description = "Create date of user", example = "2025-11-15T22:00:00")
    private LocalDateTime createDate;

    @Schema(description = "Update date of user", example = "2025-11-15T22:15:00")
    private LocalDateTime updateDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
