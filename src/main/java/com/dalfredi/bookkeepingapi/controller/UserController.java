package com.dalfredi.bookkeepingapi.controller;

import com.dalfredi.bookkeepingapi.payload.UserIdentityAvailability;
import com.dalfredi.bookkeepingapi.payload.UserSummary;
import com.dalfredi.bookkeepingapi.security.CurrentUser;
import com.dalfredi.bookkeepingapi.security.UserPrincipal;
import com.dalfredi.bookkeepingapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/checkUsernameAvailability")
    public ResponseEntity<UserIdentityAvailability> checkUsernameAvailability(
        @RequestParam(value = "username") String username
    ) {
        UserIdentityAvailability userIdentityAvailability =
            userService.checkUsernameAvailability(username);

        return new ResponseEntity<>(userIdentityAvailability, HttpStatus.OK);
    }

    @GetMapping("/checkEmailAvailability")
    public ResponseEntity<UserIdentityAvailability> checkEmailAvailability(
        @RequestParam(value = "email") String email
    ) {
        UserIdentityAvailability userIdentityAvailability =
            userService.checkEmailAvailability(email);
        return new ResponseEntity<>(userIdentityAvailability, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserSummary> getCurrentUser(@CurrentUser
                                                      UserPrincipal currentUser) {
        UserSummary userSummary = userService.getCurrentUser(currentUser);

        return new ResponseEntity< >(userSummary, HttpStatus.OK);
    }
}
