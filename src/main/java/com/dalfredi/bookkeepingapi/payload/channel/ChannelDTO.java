package com.dalfredi.bookkeepingapi.payload.channel;

import com.dalfredi.bookkeepingapi.payload.user.UserDTO;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A DTO for the {@link com.dalfredi.bookkeepingapi.model.Channel} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ChannelDTO {
    private Long id;
    @NotBlank
    @Size(max = 32)
    private String shortName;
    @Size(max = 255)
    private String fullName;
    @NotBlank
    @Size(max = 32)
    private String tgUsername;
    @NotNull
    private UserDTO owner;
}