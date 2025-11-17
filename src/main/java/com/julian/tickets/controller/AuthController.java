package com.julian.tickets.controller;

import com.julian.tickets.config.security.JwtUtil;
import com.julian.tickets.dto.LoginRequestDto;
import com.julian.tickets.dto.LoginResponseDto;
import com.julian.tickets.service.AuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final AuthUserService authUserService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AuthUserService authUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authUserService = authUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto loginDto) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);

        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getPrincipal());

        String jwt = this.jwtUtil.create(loginDto.getUsername());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }

    @Operation(summary = "Create an auth user", description = "Create a new auth user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Auth user created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> createTicket(
            @Valid @RequestBody LoginRequestDto dto) {

        LoginResponseDto authUserCreated = authUserService.createAuthUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(authUserCreated);
    }
}
