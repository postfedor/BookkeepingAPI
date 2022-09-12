package com.dalfredi.bookkeepingapi.service;

import com.dalfredi.bookkeepingapi.payload.user.UserIdentityAvailability;
import com.dalfredi.bookkeepingapi.payload.user.UserSummary;
import com.dalfredi.bookkeepingapi.repository.RoleRepository;
import com.dalfredi.bookkeepingapi.repository.UserRepository;
import com.dalfredi.bookkeepingapi.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserSummary getCurrentUser(UserPrincipal currentUser) {
        return new UserSummary(
            currentUser.getId(), currentUser.getUsername(),
            currentUser.getFirstName(),
            currentUser.getLastName());
    }

    public UserIdentityAvailability checkUsernameAvailability(String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    public UserIdentityAvailability checkEmailAvailability(String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }
}
