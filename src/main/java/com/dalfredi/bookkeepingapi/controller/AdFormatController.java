package com.dalfredi.bookkeepingapi.controller;

import com.dalfredi.bookkeepingapi.payload.AdFormatDTO;
import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import com.dalfredi.bookkeepingapi.service.AdFormatService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/format")
@RequiredArgsConstructor
@Tag(name = "Ad Format", description = "Manage formats of ads")
public class AdFormatController {
    private final AdFormatService formatService;

    @PostMapping
    @Operation(summary = "Define new format",
        description = "Name is used for referencing. Local name is how it will be look in frontend")
    public ResponseEntity<AdFormatDTO> addFormat(
        @Valid @RequestBody AdFormatDTO adFormatRequest
    ) {
        AdFormatDTO newFormat = formatService.addFormat(adFormatRequest);
        return new ResponseEntity<>(newFormat, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get format names by ID")
    public ResponseEntity<AdFormatDTO> getAdFormat(
        @PathVariable(name = "id") Long formatId
    ) {
        AdFormatDTO format = formatService.getFormatById(formatId);
        return new ResponseEntity<>(format, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update ads format")
    public ResponseEntity<AdFormatDTO> updateAdFormat(
        @PathVariable(name = "id") Long formatId,
        @Valid @RequestBody AdFormatDTO formatRequest
    ) {
        AdFormatDTO updatedFormat =
            formatService.updateFormat(formatId, formatRequest);
        return new ResponseEntity<>(updatedFormat, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete ad format by ID")
    public ResponseEntity<ApiResponse> deleteAdFormat(
        @PathVariable(name = "id") Long formatId
    ) {
        ApiResponse apiResponse =
            formatService.deleteFormat(formatId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
