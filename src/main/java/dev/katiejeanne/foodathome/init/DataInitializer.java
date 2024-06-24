package dev.katiejeanne.foodathome.init;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.Item;
import dev.katiejeanne.foodathome.domain.User;
import dev.katiejeanne.foodathome.repositories.HouseholdRepository;
import dev.katiejeanne.foodathome.service.CategoryManagementService;
import dev.katiejeanne.foodathome.service.HouseholdManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final HouseholdManagementService householdManagementService;

    private final HouseholdRepository householdRepository;

    private final CategoryManagementService categoryManagementService;

    @Autowired
    public DataInitializer(HouseholdManagementService householdManagementService, HouseholdRepository householdRepository, CategoryManagementService categoryManagementService) {
        this.householdManagementService = householdManagementService;
        this.householdRepository = householdRepository;
        this.categoryManagementService = categoryManagementService;
    }


    @Transactional
    @Override
    public void run(String... args) throws Exception {

        List<Household> households = householdRepository.findAll();
        // If no households are in the database initialize data
        if (households.isEmpty()) {
            initializeData();
        }
    }


    private void initializeData () {
        // Initialize with two households, each with 1 user and 3 categories filled with 5 items each.

        // First create the users
        User testUser1 = initializeUser("demo", "{noop}demo",
                "Test User 1", "testuser1@example.com");
        User testUser2 = initializeUser("testuser2", "{noop}test",
                "Test User 2", "testuser2@example.com");


        // Initialize a household for each user
        Household household1 = householdManagementService.saveNewStandaloneUserAndCreateTheirHousehold(testUser1);
        Household household2 = householdManagementService.saveNewStandaloneUserAndCreateTheirHousehold(testUser2);

        // Create items and categories for each category
        initializeCategoriesAndItems(household1);
        initializeCategoriesAndItems(household2);

    }

    private User initializeUser(String username, String password, String displayName, String email) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDisplayName(displayName);
        user.setEmail(email);

        return user;
    }

    private void initializeCategoriesAndItems(Household household) {
        // Default categories and items
        String[] categoryNames = {"Dairy", "Fruits", "Grains"};
        String[][] itemNames = {
                {"Milk", "Almond milk", "Yogurt", "Sliced gouda", "Cottage cheese"},
                {"Mangoes", "Starfruit", "Lychee", "Custard apples", "Wax apples"},
                {"All purpose flour", "Wheat flour", "Basatmi rice", "Pinto beans", "Couscous"}
        };

        // Create category then items in category for each category
        for (int i = 0; i < categoryNames.length; i++) {
            Category category = initializeCategory(categoryNames[i], household);
            for (String itemName : itemNames[i]) {
                initializeItem(itemName, category);
            }
        }

    }

    private Category initializeCategory(String name, Household household) {

        Category category = new Category();
        category.setName(name);
        category = householdManagementService.addCategoryToHousehold(category, household);
        return category;
    }

    private void initializeItem(String name, Category category) {

        Item item = new Item();
        item.setName(name);
        categoryManagementService.addItemToCategory(item, category);
    }


}
