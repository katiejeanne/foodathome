package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Item;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import dev.katiejeanne.foodathome.repositories.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryManagementServiceTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryManagementService categoryManagementService;

    @Test
    public void addItem_validItemAndCategory_correctlySavesWithCorrectRelationship() {

        // Set up entities
        Category category = new Category();
        Item item = new Item();

        // Add item to category using service
        categoryManagementService.addItemToCategory(item, category);
        Long itemId = item.getId();

        // Retrieve saved entities and verify
        Optional<Item> retrievedItem = itemRepository.findById(itemId);
        assertTrue(retrievedItem.isPresent());

        Category retrievedCategory = retrievedItem.get().getCategory();
        assertNotEquals(0, retrievedCategory.getId());
        assertEquals(1, retrievedCategory.getItems().size());
        assertTrue(retrievedCategory.getItems().contains(retrievedItem.get()));

    }

    @Test
    public void addItemToCategory_itemAlreadyBelongsToOtherCategory_correctlyMovesItem() {

        // Set up entities
        Category category1 = new Category();
        category1.setName("Category 1");
        Item item = new Item();
        category1.addItem(item);
        item.setCategory(category1);

        Category category2 = new Category();
        category2.setName("Category 2");

        // Use service to add item to second category
        categoryManagementService.addItemToCategory(item, category2);
        Long itemId = item.getId();
        Long category2Id = category2.getId();
        Long category1id = category1.getId();


        // Verify all 3 items were saved with appropriate changes
        Optional<Item> retrievedItem = itemRepository.findById(itemId);
        assertTrue(retrievedItem.isPresent());

        Optional<Category> retrievedCategory1 = categoryRepository.findById(category1id);
        assertTrue(retrievedCategory1.isPresent());

        Optional<Category> retrievedCategory2 = categoryRepository.findById(category2Id);
        assertTrue(retrievedCategory2.isPresent());

        assertEquals("Category 2", retrievedItem.get().getCategory().getName());
        assertNotEquals("Category 1", retrievedItem.get().getCategory().getName());

        assertEquals(1, retrievedCategory2.get().getItems().size());
        assertEquals(0, retrievedCategory1.get().getItems().size());

    }

    @Test
    public void addItemToCategory_itemAlreadyBelongsToCategory_correctlySavesWithoutDuplicates() {

        // Set up entities
        Category category = new Category();
        Item item = new Item();
        category.addItem(item);
        item.setCategory(category);

        // Use service to add again and save
        categoryManagementService.addItemToCategory(item, category);
        Long itemId = item.getId();

        // Retrieve and verify correctly saved without duplication
        Optional<Item> retrievedItem = itemRepository.findById(itemId);
        assertTrue(retrievedItem.isPresent());

        Category retrievedCategory = retrievedItem.get().getCategory();
        assertNotEquals(0, retrievedCategory.getId());

        assertTrue(retrievedCategory.getItems().contains(retrievedItem.get()));
        assertEquals(1, retrievedCategory.getItems().size());
    }

    @Test
    public void addItemToCategory_nullItem_throwsException() {

        // Set up entities
        Item item = null;
        Category category = new Category();

        // Assert null item throws exception
        assertThrows(IllegalArgumentException.class, () -> categoryManagementService.addItemToCategory(item, category));


    }

    @Test
    public void addItemToCategory_nullCategory_throwsException() {

        // Set up entities
        Item item = new Item();
        Category category = null;

        // Assert null item throws exception
        assertThrows(IllegalArgumentException.class, () -> categoryManagementService.addItemToCategory(item, category));


    }



}
