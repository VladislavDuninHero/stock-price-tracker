package com.pet.stock_price_tracker.repository;

import com.pet.stock_price_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User u WHERE u.login = :login")
    Optional<User> findUserByLogin(String login);

    @Query("FROM User u")
    List<User> getAllUsers();

    @Query("FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);
}
