package com.julian.tickets.service;

import com.julian.tickets.dto.UserRequestDTO;
import com.julian.tickets.dto.UserResponseDTO;
import com.julian.tickets.exception.ResourceNotFoundException;
import com.julian.tickets.model.User;
import com.julian.tickets.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findById_whenExists_returnsUser() {
        UUID id = UUID.randomUUID();
        User u = new User();
        u.setId(id);
        u.setName("Camilo");
        u.setLastname("Gomez");
        u.setCreateDate(LocalDateTime.now());
        when(userRepository.findById(id)).thenReturn(Optional.of(u));

        UserResponseDTO result = userService.getUserById(id);

        assertNotNull(result);
        assertEquals("Camilo", result.getName());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void findById_whenNotExists_throwsResourceNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void createUser_savesUser() {
        UserRequestDTO toSave = new UserRequestDTO();
        toSave.setName("Laura");
        toSave.setLastname("Rodriguez");

        User saved = new User();
        saved.setId(UUID.randomUUID());
        saved.setName("Laura");
        saved.setLastname("Rodriguez");
        saved.setCreateDate(LocalDateTime.now());

        when(userRepository.save(any(User.class))).thenReturn(saved);

        UserResponseDTO result = userService.createUser(toSave);

        assertNotNull(result.getId());
        assertEquals("Laura", result.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
