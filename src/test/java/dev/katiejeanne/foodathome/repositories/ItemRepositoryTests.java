package dev.katiejeanne.foodathome.repositories;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ItemRepositoryTests {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void saveItem_withCategory_CorrectlyPersistsBothItemAndCategoryWithRelationship() {
        Category category = new Category();
        Item item = new Item();

        item.setCategory(category);

        itemRepository.save(item);
        testEntityManager.flush();
        testEntityManager.clear();

        Item persistedItem = itemRepository.findById(item.getId()).orElseThrow();

        assertNotNull(persistedItem);
        assertNotNull(persistedItem.getCategory());
        assertNotEquals(0, persistedItem.getCategory().getId());


    }




}
