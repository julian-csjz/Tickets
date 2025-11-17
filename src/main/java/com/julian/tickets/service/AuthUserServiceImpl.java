package com.julian.tickets.service;


import com.julian.tickets.dto.LoginRequestDto;
import com.julian.tickets.dto.LoginResponseDto;
import com.julian.tickets.mapper.UserAuthMapper;
import com.julian.tickets.model.UserAuth;
import com.julian.tickets.repository.UserAuthRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthUserServiceImpl implements AuthUserService {

    private final UserAuthRepository userAuthRepository;
    private final UserAuthMapper userAuthMapper;

    @Autowired
    public AuthUserServiceImpl(UserAuthRepository userAuthRepository, UserAuthMapper userAuthMapper) {
        this.userAuthRepository = userAuthRepository;
        this.userAuthMapper = userAuthMapper;
    }

    @Override
    public LoginResponseDto createAuthUser(LoginRequestDto dto) {
        UserAuth userAuth = userAuthMapper.toEntity(dto);
        userAuthRepository.save(userAuth);
        return userAuthMapper.toResponseDTO(userAuth);
    }
}
