package dev.katiejeanne.foodathome.repositories;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private HouseholdRepository householdRepository;


    @Test
    public void saveCategoryWithItem_CategoryWithItem_CorrectlyPersistsBothCategoryAndItem() {

        Category category = new Category();
        Item item1 = new Item();
        Item item2 = new Item();
        category.addItem(item1);
        category.addItem(item2);
        categoryRepository.save(category);
        testEntityManager.flush();
        testEntityManager.clear();

        Category persistedCategory = categoryRepository.findById(category.getId()).orElseThrow();

        assertNotNull(persistedCategory);
        assertEquals(2, persistedCategory.getItems().size());
        // Ensure items were saved as their own entities
        persistedCategory.getItems().forEach(item -> assertNotEquals(0, item.getId()));
    }

    @Test
    public void findAllCategoriesWithItemsUsingHouseholdId_withValidData_ReturnsCategoriesAndItems() {
        // Set up test values
        Household household1 = new Household();
        householdRepository.save(household1);
        Household household2 = new Household();
        householdRepository.save(household2);

        Category category1 = new Category("cat1", household1);
        Item item1 = new Item("item1", category1);
        Item item2 = new Item("item2", category1);
        categoryRepository.save(category1);

        Category category2 = new Category("cat2", household1);
        Item item3 = new Item("item3", category2);
        categoryRepository.save(category2);

        Category category3 = new Category("cat3", household2);
        Item item4 = new Item("item4", category3);
        categoryRepository.save(category3);

        // Test find categories and items with household id query
        List<Category> categories = categoryRepository.findAllCategoriesWithItemsUsingHouseholdId(household1.getId());

        assertEquals(2, categories.size());
        for (Category cat : categories) {
            assertNotEquals(0, cat.getItems().size());
            for (Item item : cat.getItems()) {
                assertNotEquals(0, item.getId());
            }
        }


    }








}
