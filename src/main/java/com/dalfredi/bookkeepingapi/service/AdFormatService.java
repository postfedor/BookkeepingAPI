package com.dalfredi.bookkeepingapi.service;

import static com.dalfredi.bookkeepingapi.utils.Constants.AD_FORMAT;
import static com.dalfredi.bookkeepingapi.utils.Constants.ID;

import com.dalfredi.bookkeepingapi.exception.ResourceNotFoundException;
import com.dalfredi.bookkeepingapi.model.AdFormat;
import com.dalfredi.bookkeepingapi.payload.AdFormatDTO;
import com.dalfredi.bookkeepingapi.payload.api.ApiResponse;
import com.dalfredi.bookkeepingapi.repository.AdFormatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdFormatService {
    private final AdFormatRepository formatRepository;

    public AdFormatDTO addFormat(AdFormatDTO formatRequest) {
        AdFormat adFormat = new AdFormat();
        adFormat.setName(formatRequest.getName());
        adFormat.setLocalName(formatRequest.getLocalName());
        return AdFormatDTO.of(formatRepository.save(adFormat));
    }

    public AdFormatDTO getFormatById(Long formatId) {
        AdFormat format = formatRepository.findById(formatId)
            .orElseThrow(
                () -> new ResourceNotFoundException(AD_FORMAT, ID, formatId));
        return AdFormatDTO.of(format);
    }

    public AdFormatDTO updateFormat(Long formatId, AdFormatDTO formatRequest) {
        AdFormat format = formatRepository.findById(formatId)
            .orElseThrow(
                () -> new ResourceNotFoundException(AD_FORMAT, ID, formatId));
        format.setName(formatRequest.getName());
        format.setLocalName(formatRequest.getLocalName());
        formatRepository.save(format);
        return AdFormatDTO.of(format);
    }

    public ApiResponse deleteFormat(Long formatId) {
        if (!formatRepository.existsById(formatId)) {
            throw new ResourceNotFoundException(AD_FORMAT, ID, formatId);
        }
        formatRepository.deleteById(formatId);
        return new ApiResponse(Boolean.TRUE,
            "You successfully deleted ad format");
    }
}
