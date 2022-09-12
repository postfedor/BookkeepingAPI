package com.dalfredi.bookkeepingapi.payload.user;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A DTO for the {@link com.dalfredi.bookkeepingapi.model.User} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDTO implements Serializable {
    private Long id;
    @NotBlank
    @Size(max = 25)
    private String username;
    @Size(max = 40)
    private String firstName;
    @Size(max = 40)
    private String lastName;
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;
}