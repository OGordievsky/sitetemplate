package ru.acme.web.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.acme.web.repository.UserRepository;

import java.util.Optional;

import static ru.acme.web.data.TestData.test_admin;

class UserServiceTest extends AbstractServiceTest {

    @BeforeEach
    void setMockReturnValues() {
        Mockito.when(userRepository.findByEmailIgnoreCase(test_admin.getEmail()))
                .thenReturn(Optional.of(test_admin));

    }

    @Autowired
    UserService service;

    @MockBean
    UserRepository userRepository;

    @Test
    void loadUserByUsername() {
        UserDetails user = service.loadUserByUsername(test_admin.getEmail());
        Assertions.assertEquals(user, test_admin);
    }

    @Test
    void loadUserByUsernameNotExisted() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(""));
    }
}