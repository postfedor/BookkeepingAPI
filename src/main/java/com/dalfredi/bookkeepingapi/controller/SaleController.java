package com.dalfredi.bookkeepingapi.controller;

import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import com.dalfredi.bookkeepingapi.payload.sale.SaleDTO;
import com.dalfredi.bookkeepingapi.payload.sale.SaleRequest;
import com.dalfredi.bookkeepingapi.security.CurrentUser;
import com.dalfredi.bookkeepingapi.security.UserPrincipal;
import com.dalfredi.bookkeepingapi.service.SaleService;
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
    value = "/channel/{channelId}/sales",
    consumes = "application/json",
    produces = "application/json")
@RequiredArgsConstructor
@Tag(name = "Sales", description = "Sales management")
public class SaleController {
    private final SaleService saleService;

    @PostMapping
    @Parameter(name = "currentUser", hidden = true)
    @Operation(summary = "Add new sale", description = "Add new sale record to channel. Only owner of channel or user with granted permission can do this.")
    public ResponseEntity<SaleDTO> addChannel(
        @PathVariable(name = "channelId") Long channelId,
        @Valid @RequestBody SaleRequest saleRequest,
        @CurrentUser UserPrincipal currentUser
    ) {
        SaleDTO
            newSale =
            saleService.addSale(channelId, saleRequest, currentUser.getId());
        return new ResponseEntity<>(newSale, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sale by ID",
        description = "Checks if requested sale belongs to current channel and current user owns this channel. Returns only one sale.")
    public ResponseEntity<SaleDTO> getSale(
        @PathVariable(name = "channelId") Long channelId,
        @PathVariable(name = "id") Long saleId,
        @CurrentUser UserPrincipal currentUser
    ) {
        SaleDTO sale =
            saleService.getById(channelId, saleId, currentUser.getId());
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Parameter(name = "currentUser", hidden = true)
    @Operation(summary = "Update sale info",
        description = "Only sale which belongs to current and current channel (path variable) could be updated.")
    public ResponseEntity<SaleDTO> updateSaleInfo(
        @PathVariable(name = "channelId") Long channelId,
        @PathVariable(name = "id") Long saleId,
        @Valid @RequestBody SaleDTO sale,
        @CurrentUser UserPrincipal currentUser
    ) {
        SaleDTO updatedSale = saleService.updateSale(channelId, saleId, sale,
            currentUser.getId());
        return new ResponseEntity<>(updatedSale, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Parameter(name = "currentUser", hidden = true)
    @Operation(summary = "Delete sale by ID",
        description = "Sale may only be deleted by owner of the channel")
    public ResponseEntity<ApiResponse> deleteSale(
        @PathVariable(name = "id") Long saleId,
        @CurrentUser UserPrincipal currentUser
    ) {
        ApiResponse apiResponse =
            saleService.deleteSaleById(saleId, currentUser.getId());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
