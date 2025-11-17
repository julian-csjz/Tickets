package com.julian.tickets.service;

import com.julian.tickets.model.UserAuth;
import com.julian.tickets.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public UserSecurityService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = this.userAuthRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + "not found."));
        return User.builder()
                .username(userAuth.getUsername())
                .password(userAuth.getPassword())
                .roles("ADMIN")
                .build();
    }
}
