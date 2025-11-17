package com.julian.tickets.mapper;

import com.julian.tickets.dto.LoginRequestDto;
import com.julian.tickets.dto.LoginResponseDto;
import com.julian.tickets.model.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //Entity to ResponseDTO
    public LoginResponseDto toResponseDTO(UserAuth user) {
        LoginResponseDto dto = new LoginResponseDto();
        dto.setUsername(user.getUsername());
        return dto;
    }

    // RequestDTO to Entity
    public UserAuth toEntity(LoginRequestDto dto) {
        UserAuth user = new UserAuth();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        user.setUsername(dto.getUsername());
        user.setPassword(encodedPassword);
        return user;
    }
}
