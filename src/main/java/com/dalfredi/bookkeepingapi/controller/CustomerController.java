//package com.dalfredi.bookkeepingapi.controller;
//
//import com.dalfredi.bookkeepingapi.payload.channel.ChannelRequest;
//import com.dalfredi.bookkeepingapi.payload.channel.ChannelResponse;
//import com.dalfredi.bookkeepingapi.security.CurrentUser;
//import com.dalfredi.bookkeepingapi.security.UserPrincipal;
//import com.dalfredi.bookkeepingapi.service.CustomerService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import javax.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(
//    value = "/customer",
//    consumes = "application/json",
//    produces = "application/json")
//@RequiredArgsConstructor
//@Tag(name = "Customer", description = "Customer management")
//public class CustomerController {
//    private final CustomerService customerService;
//
//    @PostMapping
//    @Parameter(name = "currentUser", hidden = true)
//    @Operation(summary = "Add new customer", description = "")
//    public ResponseEntity<ChannelResponse> addChannel(
//        @Valid @RequestBody ChannelRequest channelRequest,
//        @CurrentUser UserPrincipal currentUser
//    ) {
//        ChannelResponse channelResponse =
//            channelService.addChannel(channelRequest, currentUser.getId());
//        return new ResponseEntity<>(channelResponse, HttpStatus.CREATED);
//    }
//}
