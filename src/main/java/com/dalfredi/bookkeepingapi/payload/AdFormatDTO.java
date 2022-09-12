package com.dalfredi.bookkeepingapi.payload;

import com.dalfredi.bookkeepingapi.model.AdFormat;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A DTO for the {@link com.dalfredi.bookkeepingapi.model.AdFormat} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AdFormatDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String localName;

    public static AdFormatDTO of(AdFormat format) {
        return new AdFormatDTO()
            .setId(format.getId())
            .setName(format.getName())
            .setLocalName(format.getLocalName());
    }
}