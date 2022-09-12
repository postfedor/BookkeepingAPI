package com.dalfredi.bookkeepingapi.controller;

import com.dalfredi.bookkeepingapi.payload.user.UserIdentityAvailability;
import com.dalfredi.bookkeepingapi.payload.user.UserSummary;
import com.dalfredi.bookkeepingapi.security.CurrentUser;
import com.dalfredi.bookkeepingapi.security.UserPrincipal;
import com.dalfredi.bookkeepingapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management")
public class UserController {
    private final UserService userService;

    @GetMapping("/checkUsernameAvailability")
    @SecurityRequirements
    @Operation(summary = "Check username availability", description = "Checks if username is taken or not")
    public ResponseEntity<UserIdentityAvailability> checkUsernameAvailability(
        @RequestParam(value = "username") String username
    ) {
        UserIdentityAvailability userIdentityAvailability =
            userService.checkUsernameAvailability(username);

        return new ResponseEntity<>(userIdentityAvailability, HttpStatus.OK);
    }

    @GetMapping("/checkEmailAvailability")
    @SecurityRequirements
    @Operation(summary = "Check email availability", description = "Checks if email is taken or not")
    public ResponseEntity<UserIdentityAvailability> checkEmailAvailability(
        @RequestParam(value = "email") String email
    ) {
        UserIdentityAvailability userIdentityAvailability =
            userService.checkEmailAvailability(email);
        return new ResponseEntity<>(userIdentityAvailability, HttpStatus.OK);
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user summary", description = "Retrieve information about current user which is authenticated with valid access JWT token")
    public ResponseEntity<UserSummary> getCurrentUser(@CurrentUser
                                                      UserPrincipal currentUser) {
        UserSummary userSummary = userService.getCurrentUser(currentUser);

        return new ResponseEntity<>(userSummary, HttpStatus.OK);
    }
}
