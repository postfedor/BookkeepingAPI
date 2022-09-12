package com.dalfredi.bookkeepingapi.payload.channel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class ChannelRequest {
    @NotBlank
    @Size(max = 32)
    private String shortName;

    @Size(max = 255)
    private String fullName;

    @NotBlank
    @Size(max = 32)
    private String tgUsername;
}
