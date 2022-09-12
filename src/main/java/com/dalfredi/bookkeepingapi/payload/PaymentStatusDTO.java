package com.dalfredi.bookkeepingapi.payload;

import com.dalfredi.bookkeepingapi.model.PaymentStatus;
import com.dalfredi.bookkeepingapi.model.StatusName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A DTO for the {@link com.dalfredi.bookkeepingapi.model.PaymentStatus} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PaymentStatusDTO {
    private Long id;
    private StatusName name;

    public static PaymentStatusDTO of(PaymentStatus status) {
        return new PaymentStatusDTO()
            .setId(status.getId())
            .setName(status.getName());
    }
}