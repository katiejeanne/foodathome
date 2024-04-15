package dev.katiejeanne.foodathome.repositories;

import dev.katiejeanne.foodathome.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        User testUser = new User();
        testUser.setUsername("test_user");
        testUser.setPassword("{noop}test");
        userRepository.save(testUser);
    }

    @Test
    public void findByUsername_WhenUserExists_ReturnsUser() {

        Optional<User> tempUser = userRepository.findByUsername("test_user");

        assertTrue(tempUser.isPresent());
        assertEquals("test_user", tempUser.get().getUsername());
    }

    @Test
    public void findByUsername_WhenUserDoesNotExist_ReturnsEmpty() {

        Optional<User> tempUser = userRepository.findByUsername("not_a_user");

        assertTrue(tempUser.isEmpty());
    }
}
