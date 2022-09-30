package ru.acme.web.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.acme.web.model.User;

import java.util.Optional;


class UserRepositoryTest extends AbstractRepository {

    @Autowired
    UserRepository repository;

    @Test
    void findByEmailIgnoreCase() {
        String email = "admin@admin.adm";
        Optional<User> user = repository.findByEmailIgnoreCase(email);
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(email, user.get().getEmail());
    }

    @Test
    void findById() {
        long id = 100_000L;
        Optional<User> user = repository.findById(id);
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(id, user.get().getId());
    }
}