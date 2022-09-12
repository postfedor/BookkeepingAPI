package com.dalfredi.bookkeepingapi.controller;

import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import com.dalfredi.bookkeepingapi.payload.channel.ChannelRequest;
import com.dalfredi.bookkeepingapi.payload.channel.ChannelResponse;
import com.dalfredi.bookkeepingapi.security.CurrentUser;
import com.dalfredi.bookkeepingapi.security.UserPrincipal;
import com.dalfredi.bookkeepingapi.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = "/channel",
    consumes = "application/json",
    produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Channel", description = "Telegram channel entity API")
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping
    @Parameter(name = "currentUser", hidden = true)
    @Operation(summary = "Add new channel", description = "Channel owner set to current authenticated user")
    public ResponseEntity<ChannelResponse> addChannel(
        @Valid @RequestBody ChannelRequest channelRequest,
        @CurrentUser UserPrincipal currentUser
    ) {
        ChannelResponse channelResponse =
            channelService.addChannel(channelRequest, currentUser.getId());
        return new ResponseEntity<>(channelResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get channel by ID", description = "Checks if requested channel belongs to current user. Returns only one channel.")
    public ResponseEntity<ChannelResponse> getChannelInfo(
        @PathVariable(name = "id") Long channelId,
        @CurrentUser UserPrincipal currentUser
    ) {
        ChannelResponse channelResponse =
            channelService.findChannelById(channelId, currentUser.getId());
        return new ResponseEntity<>(channelResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Parameter(name = "currentUser", hidden = true)
    @Operation(summary = "Update an existing channel",
        description = "Only channel which belongs to current user could be updated.")
    public ResponseEntity<ChannelResponse> updateChannelInfo(
        @PathVariable(name = "id") Long channelId,
        @Valid @RequestBody ChannelRequest channelRequest,
        @CurrentUser UserPrincipal currentUser
    ) {
        ChannelResponse channelResponse =
            channelService.updateChannel(channelId, channelRequest,
                currentUser.getId());
        return new ResponseEntity<>(channelResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Parameter(name = "currentUser", hidden = true)
    @Operation(summary = "Delete channel by ID", description = "Channel may only be deleted by its owner")
    public ResponseEntity<ApiResponse> deleteChannel(
        @PathVariable(name = "id") Long channelId,
        @CurrentUser UserPrincipal currentUser
    ) {
        ApiResponse apiResponse =
            channelService.deleteChannel(channelId, currentUser.getId());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
