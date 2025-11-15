package com.julian.tickets.dto;

import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {
    @NotBlank(message = "Field 'name' is mandatory")
    private String name;

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
