package com.pet.stock_price_tracker.repository;

import com.pet.stock_price_tracker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("FROM Role r WHERE r.role = :roleName")
    Optional<Role> findByRoleName(String roleName);
}
