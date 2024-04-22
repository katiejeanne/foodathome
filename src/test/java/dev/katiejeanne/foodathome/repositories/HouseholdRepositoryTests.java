package dev.katiejeanne.foodathome.repositories;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.Item;
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

    @Test
    public void retrieveWholeHouseholdById_withHousehold_returnsEntireHousehold() {

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

        // Save household and keep id for later reference
        householdRepository.save(household1);
        Long householdId = household1.getId();

        // Clear cache
        testEntityManager.flush();
        testEntityManager.clear();

        // Retrieve entire Household
        Optional<Household> persistedHousehold = householdRepository.findHouseholdById(householdId);

        // Assert that household and all its categories and items were retrieved
        assertTrue(persistedHousehold.isPresent());
        assertEquals(2, persistedHousehold.get().getCategories().size());
        for (Category category : persistedHousehold.get().getCategories()) {
            assertNotEquals(0, category.getId());
            assertNotEquals(0, category.getItems().size());
            for (Item item : category.getItems()) {
                assertNotEquals(0, item.getId());
            }
        }






    }



}
