package com.dalfredi.bookkeepingapi.payload;

import com.dalfredi.bookkeepingapi.model.Customer;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A DTO for the {@link com.dalfredi.bookkeepingapi.model.Customer} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CustomerDTO {
    private Long id;
    @NotBlank
    @Size(max = 40)
    private String name;
    @NotBlank
    @Size(max = 32)
    private String tgUsername;

    public static CustomerDTO of(Customer customer) {
        return new CustomerDTO()
            .setId(customer.getId())
            .setName(customer.getName())
            .setTgUsername(customer.getTgUsername());
    }
}