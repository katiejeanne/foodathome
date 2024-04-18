package dev.katiejeanne.foodathome.repositories;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class HouseholdRepositoryTests {

    @Autowired
    HouseholdRepository householdRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void saveHousehold_NewHouseholdWithUser_persistsBothHouseholdAndUser() {

        // Create a household with a User
        long savedHouseholdId = createHouseholdWithUserThenSaveAndReturnId();

        // Retrieve household
        Household persistedHousehold = householdRepository.findById(savedHouseholdId).orElseThrow();

        assertNotNull(persistedHousehold);
        assertEquals(1, persistedHousehold.getUsers().size());
        for (User persistedUser : persistedHousehold.getUsers()) {
            assertEquals(persistedHousehold.getId(), persistedUser.getHousehold().getId());
        }
    }

    @Test
    public void saveHousehold_UpdatedHouseholdWithUser_updatesHouseholdAndUser() {

        // Create and save a household with a user
        long savedHouseholdId = createHouseholdWithUserThenSaveAndReturnId();

        // Values to assign for test
        String householdName = "Test Household";
        String userName = "Test User";

        // Retrieve saved household and user, modify, and save
        Household persistedHousehold = householdRepository.findById(savedHouseholdId).orElseThrow();
        persistedHousehold.setName(householdName);
        User persistedUser = persistedHousehold.getUsers().get(0);
        persistedUser.setUsername(userName);
        householdRepository.save(persistedHousehold);
        testEntityManager.flush();
        testEntityManager.clear();

        // Verify modifications appropriately saved
        Household modifiedHousehold = householdRepository.findById(savedHouseholdId).orElseThrow();
        User modifiedUser = modifiedHousehold.getUsers().get(0);

        assertEquals(householdName, modifiedHousehold.getName());
        assertEquals(userName, modifiedUser.getUsername());
        assertEquals(1, modifiedHousehold.getUsers().size());

    }

    @Test
    public void deleteHousehold_HouseholdWithUser_RemovesBothHouseholdAndUser() {

        // Create a household with a user
        long savedHouseholdId = createHouseholdWithUserThenSaveAndReturnId();

        // Get the user id for later testing
        Household savedHousehold = householdRepository.findById(savedHouseholdId).orElseThrow();
        long userId = savedHousehold.getUsers().get(0).getId();

        // Clear cache to ensure accurate database response
        testEntityManager.flush();
        testEntityManager.clear();

        // Remove the household
        householdRepository.deleteById(savedHouseholdId);

        // Verify both Household and User were removed
        Optional<Household> removedHousehold = householdRepository.findById(savedHouseholdId);
        Optional<User> removedUser = userRepository.findById(userId);

        assertTrue(removedHousehold.isEmpty());
        assertTrue(removedUser.isEmpty());

    }


    @Test
    public void saveHousehold_NewCategoryWithUser_persistsBothHouseholdAndCategory() {

        // Create a household with a category
        long savedHouseholdId = createHouseholdWithCategorySaveAndReturnId();

        // Retrieve household
        Household persistedHousehold = householdRepository.findById(savedHouseholdId).orElseThrow();

        assertNotNull(persistedHousehold);
        assertEquals(1, persistedHousehold.getCategories().size());
        for (Category persistedCategory : persistedHousehold.getCategories()) {
            assertEquals(persistedHousehold.getId(), persistedCategory.getHousehold().getId());
        }
    }

    @Test
    public void saveHousehold_UpdatedCategoryWithUser_updatesHouseholdAndCategory() {

        // Create and save a household with a category
        long savedHouseholdId = createHouseholdWithCategorySaveAndReturnId();

        // Values to assign for test
        String householdName = "Test Household";
        String categoryName = "Test Category";

        // Retrieve saved household and category, modify, and save
        Household persistedHousehold = householdRepository.findById(savedHouseholdId).orElseThrow();
        persistedHousehold.setName(householdName);
        Category persistedCategory = persistedHousehold.getCategories().get(0);
        persistedCategory.setName(categoryName);
        householdRepository.save(persistedHousehold);
        testEntityManager.flush();
        testEntityManager.clear();

        // Verify modifications appropriately saved
        Household modifiedHousehold = householdRepository.findById(savedHouseholdId).orElseThrow();
        Category modifiedCategory = modifiedHousehold.getCategories().get(0);

        assertEquals(householdName, modifiedHousehold.getName());
        assertEquals(categoryName, modifiedCategory.getName());
        assertEquals(1, modifiedHousehold.getCategories().size());

    }

    @Test
    public void deleteHousehold_HouseholdWithCategory_RemovesBothHouseholdAndCategory() {

        // Create a household with a category
        long savedHouseholdId = createHouseholdWithCategorySaveAndReturnId();

        // Get the user id for later testing
        Household savedHousehold = householdRepository.findById(savedHouseholdId).orElseThrow();
        long categoryId = savedHousehold.getCategories().get(0).getId();

        // Clear cache to ensure accurate database response
        testEntityManager.flush();
        testEntityManager.clear();

        // Remove the household
        householdRepository.deleteById(savedHouseholdId);

        // Verify both Household and User were removed
        Optional<Household> removedHousehold = householdRepository.findById(savedHouseholdId);
        Optional<Category> removedCategory = categoryRepository.findById(categoryId);

        assertTrue(removedHousehold.isEmpty());
        assertTrue(removedCategory.isEmpty());

    }

    private long createHouseholdWithUserThenSaveAndReturnId() {

        // Create a household, attach a user, and save
        Household household = new Household();
        User user = new User();
        household.addUser(user);
        householdRepository.save(household);
        testEntityManager.flush();
        testEntityManager.clear();

        // Return Id for reference
        return household.getId();


    }

    private long createHouseholdWithCategorySaveAndReturnId () {
        // Create a household, attach category, then save
        Household household = new Household();
        Category category = new Category();
        household.addCategory(category);
        householdRepository.save(household);
        testEntityManager.flush();
        testEntityManager.clear();

        // Return Id for reference
        return household.getId();
    }




}
