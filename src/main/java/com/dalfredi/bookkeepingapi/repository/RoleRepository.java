package com.dalfredi.bookkeepingapi.repository;

import com.dalfredi.bookkeepingapi.model.role.Role;
import com.dalfredi.bookkeepingapi.model.role.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
