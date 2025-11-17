package com.julian.tickets.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO for user authentication (login)")
public class LoginDto {

    @NotBlank
    @Schema(description = "Username for login", example = "camilo.gomez", required = true)
    private String username;

    @NotBlank
    @Schema(description = "User password", example = "MiPassword123!", required = true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
