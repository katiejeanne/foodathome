package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.TestSetupUtility;
import dev.katiejeanne.foodathome.domain.*;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import dev.katiejeanne.foodathome.repositories.HouseholdRepository;
import dev.katiejeanne.foodathome.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
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

    @Test
    public void saveNewStandaloneUser_userAlreadyBelongsToHousehold_throwsException() {

        User user = new User();
        Household household = new Household();
        user.setHousehold(household);

        assertThrows(IllegalArgumentException.class, () -> householdManagementService.saveNewStandaloneUserAndCreateTheirHousehold(user));

        // Assert neither household nor user were saved
        assertEquals(0, user.getId());
        assertEquals(0, household.getId());
    }

    @Test
    public void saveNewStandaloneUser_nullUser_throwsException() {
        User user = null;

        assertThrows(IllegalArgumentException.class, () -> householdManagementService.saveNewStandaloneUserAndCreateTheirHousehold(user));

        assertNull(user);
    }

    @Test
    public void addNewUserToHousehold_validUser_savesAndAddsUser() {

        // Create entities
        Household household = new Household();
        User user = new User();

        // Add user to Household
        householdManagementService.addNewUserToHousehold(user, household, HouseholdRole.ROLE_ADMIN);
        Long householdId = household.getId();

        // Retrieve from repository and verify household
        Optional<Household> retrievedHousehold = householdRepository.findById(householdId);
        assertTrue(retrievedHousehold.isPresent());
        assertEquals(1, retrievedHousehold.get().getUsers().size());

        // Verify user
        User retrievedUser = retrievedHousehold.get().getUsers().get(0);
        assertNotEquals(0, retrievedUser.getId());
        assertEquals(HouseholdRole.ROLE_ADMIN, retrievedUser.getHouseholdRole());

    }

    @Test
    public void addNewUserToHousehold_userAlreadyAssigned_throwsException() {

        // Create households and user
        Household previousHousehold = new Household();
        User user = new User();
        user.setHousehold(previousHousehold);

        Household addingHousehold = new Household();

        // Attempt to add user
        assertThrows(IllegalStateException.class, () -> householdManagementService.addNewUserToHousehold(user, addingHousehold, HouseholdRole.ROLE_ADMIN));

        // Assert no entities were saved to repository
        assertEquals(0, previousHousehold.getId());
        assertEquals(0, addingHousehold.getId());
        assertEquals(0, user.getId());
    }

    @Test
    public void addNewUserToHousehold_userAlreadyBelongsToHousehold_passesWithoutDuplication() {

        // Set up user and household
        User user = new User();
        Household household = new Household();
        user.setHousehold(household);
        household.addUser(user);

        // Add user to a household where it already belongs
        householdManagementService.addNewUserToHousehold(user, household, HouseholdRole.ROLE_ADMIN);
        Long householdId = household.getId();

        // All information should be accurate without duplication of user
        Optional<Household> retrievedHousehold = householdRepository.findById(householdId);
        assertTrue(retrievedHousehold.isPresent());

        assertEquals(1, retrievedHousehold.get().getUsers().size());
        User retrivedUser = retrievedHousehold.get().getUsers().get(0);

        assertEquals(HouseholdRole.ROLE_ADMIN, retrivedUser.getHouseholdRole());
        assertEquals(retrievedHousehold.get(), retrivedUser.getHousehold());
        assertNotEquals(0, user.getId());
    }

    @Test
    public void addNewUserToHousehold_nullUser_throwsException() {

        User user = null;
        Household household = new Household();

        // Null user throws exception
        assertThrows(IllegalArgumentException.class, () -> householdManagementService.addNewUserToHousehold(user, household, HouseholdRole.ROLE_ADMIN));

        // Household was not saved
        assertEquals(0, household.getId());
    }

    @Test
    public void addNewUserToHousehold_nullHousehold_throwsException() {

        User user = new User();
        Household household = null;

        // Null household throws exception
        assertThrows(IllegalArgumentException.class, () -> householdManagementService.addNewUserToHousehold(user, household, HouseholdRole.ROLE_ADMIN));

        // User was not saved
        assertEquals(0, user.getId());
    }

    @Test
    public void addCategory_validCategoryAndHousehold_correctlySavesBoth() {

        // Create category and household
        Category category = new Category();
        Household household = new Household();

        // Add category to household
        householdManagementService.addCategoryToHousehold(category, household);
        Long categoryId = category.getId();

        // Retrieve from repository
        Optional<Category> retrievedCategory = categoryRepository.findById(categoryId);

        // Verify category and household
        assertTrue(retrievedCategory.isPresent());
        assertNotEquals(0, retrievedCategory.get().getId());

        Household retrievedHousehold = retrievedCategory.get().getHousehold();
        assertNotNull(retrievedHousehold);
        assertTrue(retrievedHousehold.getCategories().contains(retrievedCategory.get()));
        assertNotEquals(0, household.getId());

    }

    @Test
    public void addCategory_categoryAlreadyBelongsToDifferentHousehold_throwsException() {

        // Attach a category to a household
        Household household1 = new Household();
        Category category = new Category();
        category.setHousehold(household1);
        household1.addCategory(category);

        // Create second household
        Household household2 = new Household();

        // Attempt to add category to second household
        assertThrows(IllegalStateException.class, () -> householdManagementService.addCategoryToHousehold(category, household2));

        // Make sure no entities were saved
        assertEquals(0, household1.getId());
        assertEquals(0, household2.getId());
        assertEquals(0, category.getId());

    }

    @Test
    public void addCategoryToHousehold_categoryAlreadyBelongsToHousehold_passesWithoutDuplication() {

        // Add category to household
        Household household = new Household();
        Category category = new Category();
        category.setHousehold(household);
        household.addCategory(category);

        // Use service to try to add category again
        householdManagementService.addCategoryToHousehold(category, household);
        Long categoryId = category.getId();

        // Retrieve from repository and verify
        Optional<Category> retrievedCategory = categoryRepository.findById(categoryId);
        assertTrue(retrievedCategory.isPresent());

        Household retrievedHousehold = retrievedCategory.get().getHousehold();
        assertNotEquals(0, retrievedHousehold.getId());
        assertEquals(1, retrievedHousehold.getCategories().size());
        assertTrue(retrievedHousehold.getCategories().contains(retrievedCategory.get()));

    }

    @Test
    public void addCategoryToHousehold_nullCategory_throwsException() {

        // Set up entities
        Category category = null;
        Household household = new Household();

        // Null category throws an exception
        assertThrows(IllegalArgumentException.class, () -> householdManagementService.addCategoryToHousehold(category, household));

    }

    @Test
    public void addCategoryToHousehold_nullHousehold_throwsException() {

        // Set up entities
        Category category = new Category();
        Household household = null;

        // Null category throws an exception
        assertThrows(IllegalArgumentException.class, () -> householdManagementService.addCategoryToHousehold(category, household));

    }

    @Test
    public void getAllCategoriesWithItems() {

        // Set up entities
        // Create household to hold categories
        Household household1 = new Household();

        // Create category 1
        Category category1 = new Category();
        household1.addCategory(category1);
        category1.setHousehold(household1);

        // Add item to category 1
        Item item1 = new Item();
        item1.setCategory(category1);
        category1.addItem(item1);

        // Add second category to household
        Category category2 = new Category();
        category2.setHousehold(household1);
        household1.addCategory(category2);

        // Add item to second category
        Item item2 = new Item();
        item2.setCategory(category2);
        category2.addItem(item2);

        // Create second household
        Household household2 = new Household();

        // Create category 3
        Category category3 = new Category();
        category3.setHousehold(household2);
        household2.addCategory(category3);

        // Add item to category 3
        Item item3 = new Item();
        item3.setCategory(category3);
        category3.addItem(item3);

        // Save household and keep id for later reference
        householdRepository.save(household1);
        householdRepository.save(household2);
        long householdId = household1.getId();

        // Set up security context with correct householdId
        TestSetupUtility.setUpSecurityContext("test_user","{noop}test", householdId);

        // Get list of categories
        List<Category> retrievedCategories = householdManagementService.getAllCategoriesWIthItems();

        // Make sure list returns correct number of categories and all their items
        assertEquals(2, retrievedCategories.size());
        for (Category category : retrievedCategories) {
            assertEquals(1, category.getItems().size());
        }

    }


}
