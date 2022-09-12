package com.dalfredi.bookkeepingapi.payload.sale;

import com.dalfredi.bookkeepingapi.payload.AdFormatDTO;
import com.dalfredi.bookkeepingapi.payload.CustomerDTO;
import com.dalfredi.bookkeepingapi.payload.PaymentStatusDTO;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaleRequest {
    @NotNull
    LocalDateTime dateTime;
    @NotNull
    AdFormatDTO format;
    @NotNull
    CustomerDTO customer;
    @Min(value = 0, message = "The amount of sale could not be negative")
    Long price;
    PaymentStatusDTO status;
}
