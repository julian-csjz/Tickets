package com.julian.tickets.mapper;

import com.julian.tickets.dto.UserRequestDTO;
import com.julian.tickets.dto.UserResponseDTO;
import com.julian.tickets.model.User;

public class UserMapper {

    //Entity to ResponseDTO
    public static UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLastname(user.getLastname());
        dto.setCreateDate(user.getCreateDate());
        dto.setUpdateDate(user.getUpdateDate());
        return dto;
    }

    // RequestDTO to Entity
    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        return user;
    }

    public static void updateEntity(UserRequestDTO dto, User user) {
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
    }
}
