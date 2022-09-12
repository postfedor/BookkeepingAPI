package com.dalfredi.bookkeepingapi.service;

import com.dalfredi.bookkeepingapi.model.User;
import com.dalfredi.bookkeepingapi.repository.UserRepository;
import com.dalfredi.bookkeepingapi.security.UserPrincipal;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail,
                usernameOrEmail)
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format("User not found with this username or email: %s",
                    usernameOrEmail)));
        return UserPrincipal.create(user);
    }
}
