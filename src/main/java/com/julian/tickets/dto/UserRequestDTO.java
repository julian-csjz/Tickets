package com.julian.tickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO to create a new user")
public class UserRequestDTO {
    @Schema(description = "Name of user", example = "Camilo")
    @NotBlank(message = "Field 'name' is mandatory")
    private String name;

    @Schema(description = "Lastname of user", example = "Gómez")
    @NotBlank(message = "Field 'lastname' is mandatory")
    private String lastname;

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
}
