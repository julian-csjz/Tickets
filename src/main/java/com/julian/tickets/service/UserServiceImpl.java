package com.julian.tickets.service;

import com.julian.tickets.dto.UserRequestDTO;
import com.julian.tickets.dto.UserResponseDTO;
import com.julian.tickets.exception.ResourceNotFoundException;
import com.julian.tickets.mapper.UserMapper;
import com.julian.tickets.model.User;
import com.julian.tickets.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);
        userRepository.save(user);
        return UserMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User wit id " + id + "not found"));
        UserMapper.updateEntity(dto, user);
        return UserMapper.toResponseDTO(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User wit id " + id + "not found"));
        return UserMapper.toResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
