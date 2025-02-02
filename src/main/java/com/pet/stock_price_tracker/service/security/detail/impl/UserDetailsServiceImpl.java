package com.pet.stock_price_tracker.service.security.detail.impl;

import com.pet.stock_price_tracker.entity.User;
import com.pet.stock_price_tracker.repository.UserRepository;
import com.pet.stock_price_tracker.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByLogin(username);
        System.out.println(user.get().getRoles().get(0));
        return user.map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
