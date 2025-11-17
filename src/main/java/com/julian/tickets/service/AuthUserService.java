package com.julian.tickets.service;


import com.julian.tickets.dto.LoginRequestDto;
import com.julian.tickets.dto.LoginResponseDto;

public interface AuthUserService {
    LoginResponseDto createAuthUser(LoginRequestDto dto);
}
