package com.dalfredi.bookkeepingapi.payload.sale;

import com.dalfredi.bookkeepingapi.model.Sale;
import com.dalfredi.bookkeepingapi.payload.AdFormatDTO;
import com.dalfredi.bookkeepingapi.payload.CustomerDTO;
import com.dalfredi.bookkeepingapi.payload.PaymentStatusDTO;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A DTO for the {@link com.dalfredi.bookkeepingapi.model.Sale} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class SaleDTO {
    private Long id;
    @NotNull
    private LocalDateTime dateTime;
    @NotNull
    private AdFormatDTO format;
    @NotNull
    private CustomerDTO customer;
    @NotNull
    @Min(value = 0L, message = "The value must be positive")
    private Long price;
    @NotNull
    private PaymentStatusDTO status;
    private Long channelId;

    public static SaleDTO of(Sale sale) {
        return new SaleDTO()
            .setId(sale.getId())
            .setDateTime(sale.getDateTime())
            .setFormat(AdFormatDTO.of(sale.getFormat()))
            .setCustomer(CustomerDTO.of(sale.getCustomer()))
            .setPrice(sale.getPrice())
            .setStatus(PaymentStatusDTO.of(sale.getStatus()))
            .setChannelId(sale.getChannel().getId());
    }
}