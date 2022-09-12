package com.dalfredi.bookkeepingapi.payload;

import com.dalfredi.bookkeepingapi.model.role.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A DTO for the {@link com.dalfredi.bookkeepingapi.model.role.Role} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class RoleDTO {
    private Long id;
    private RoleName name;
}