package com.julian.tickets.service;

import com.julian.tickets.dto.UserRequestDTO;
import com.julian.tickets.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO dto);

    UserResponseDTO updateUser(UUID id, UserRequestDTO dto);

    void deleteUser(UUID id);

    UserResponseDTO getUserById(UUID id);

    List<UserResponseDTO> getAllUsers();
}
