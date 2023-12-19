package com.cs790.app.repository;

import com.cs790.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>  {

    Optional<User> findById(String id);

    User findByEmail(String email);
}
