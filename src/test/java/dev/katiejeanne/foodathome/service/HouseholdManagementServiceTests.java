package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.User;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import dev.katiejeanne.foodathome.repositories.HouseholdRepository;
import dev.katiejeanne.foodathome.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class HouseholdManagementServiceTests {

    @Autowired
    private HouseholdManagementService householdManagementService;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveNewStandaloneUserAndCreateTheirHousehold_newUser_savesUserAndCreatesHousehold() {
        // Create new user
        User user = new User();

        // Save the user and create the household
        householdManagementService.saveNewStandaloneUserAndCreateTheirHousehold(user);
        Long userId = user.getId();

        // Retrieve the saved user and household
        Optional<User> savedUser = userRepository.findById(userId);
        assertTrue(savedUser.isPresent());

        Household newHousehold = savedUser.get().getHousehold();
        assertNotNull(newHousehold);

        assertNotEquals(0, newHousehold.getId());
        assertEquals("My Household Inventory", newHousehold.getName());
    }

}
