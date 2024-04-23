package dev.katiejeanne.foodathome.repositories;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class RepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void findCategoryByHouseholdId_withPopulatedHouseholds_returnsAllCategoriesAndItems() {

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

        // Add second item to category 1
        Item item2 = new Item();
        item2.setCategory(category1);
        category1.addItem(item2);

        // Add second category to household
        Category category2 = new Category();
        category2.setHousehold(household1);
        household1.addCategory(category2);

        // Add item to second category
        Item item3 = new Item();
        item3.setCategory(category2);
        category2.addItem(item3);

        // Create second household
        Household household2 = new Household();

        // Create category 3
        Category category3 = new Category();
        category3.setHousehold(household2);
        household2.addCategory(category3);

        // Add item to category 3
        Item item4 = new Item();
        item4.setCategory(category3);
        category3.addItem(item4);

        // Save household and keep id for later reference
        householdRepository.save(household1);
        Long householdId = household1.getId();
        householdRepository.save(household2);

        // Clear cache
        testEntityManager.flush();
        testEntityManager.clear();

        // Retrieve all categories in household 1
        List<Category> retrievedCategories = categoryRepository.findCategoriesByHouseholdId(householdId);

        // Assert that household and all its categories and items were retrieved
        // Assert that second household is not included in results
        assertEquals(2, retrievedCategories.size());
        for (Category category : retrievedCategories) {
            assertNotEquals(0, category.getId());
            assertNotEquals(0, category.getItems().size());
            for (Item item : category.getItems()) {
                assertNotEquals(0, item.getId());
            }
        }
    }
}
