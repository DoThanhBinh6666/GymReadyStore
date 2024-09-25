package com.project.shopapp_backend.repositories;

import com.project.shopapp_backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepsitory extends JpaRepository<Role, Long> {
}
