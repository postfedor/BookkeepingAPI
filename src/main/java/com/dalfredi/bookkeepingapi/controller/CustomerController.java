package com.dalfredi.bookkeepingapi.controller;

import com.dalfredi.bookkeepingapi.payload.CustomerDTO;
import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import com.dalfredi.bookkeepingapi.security.CurrentUser;
import com.dalfredi.bookkeepingapi.security.UserPrincipal;
import com.dalfredi.bookkeepingapi.service.CustomerService;
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
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Customer management")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @Parameter(name = "currentUser", hidden = true)
    @Operation(summary = "Add new customer")
    public ResponseEntity<CustomerDTO> addChannel(
        @Valid @RequestBody CustomerDTO customerRequest,
        @CurrentUser UserPrincipal currentUser
    ) {
        CustomerDTO newCustomer = customerService.addCustomer(customerRequest);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Parameter(name = "currentUser", hidden = true)
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<CustomerDTO> getCustomerById(
        @PathVariable(name = "id") Long customerId,
        @CurrentUser UserPrincipal currentUser
    ) {
        CustomerDTO newCustomer = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Parameter(name = "currentUser", hidden = true)
    @Operation(summary = "Update customer info")
    public ResponseEntity<CustomerDTO> updateCustomer(
        @PathVariable(name = "id") Long customerId,
        @Valid @RequestBody CustomerDTO customer,
        @CurrentUser UserPrincipal currentUser
    ) {
        CustomerDTO updatedCustomer =
            customerService.updateCustomer(customerId, customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Parameter(name = "currentUser", hidden = true)
    @Operation(summary = "Delete customer by ID")
    public ResponseEntity<ApiResponse> deleteSale(
        @PathVariable(name = "id") Long customerId,
        @CurrentUser UserPrincipal currentUser
    ) {
        ApiResponse apiResponse =
            customerService.deleteCustomerById(customerId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
