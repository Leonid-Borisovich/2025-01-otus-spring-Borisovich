package ru.otus.hw.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.auth.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
