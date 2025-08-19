package ru.otus.hw.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.security.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);

}
