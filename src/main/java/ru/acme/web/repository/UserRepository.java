package ru.acme.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.acme.web.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
}
