package dev.katiejeanne.foodathome.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    private Category category;

    private Item item;

    private Household household;

    private String testName = "My Category";

    @BeforeEach
    public void createCategory() {
        category = new Category();
        category.setName(testName);

        item = new Item();
        item.setCategory(category);
        category.addItem(item);

        household = new Household();
        category.setHousehold(household);
        household.addCategory(category);
    }

    @Test
    public void setAndGetName_whenSet_correctlyReturnsName() {

        assertEquals(testName, category.getName());
    }

    @Test
    public void setAndGetHousehold_whenSet_correctlyReturnsHousehold() {

        assertEquals(household, category.getHousehold());
    }

    @Test
    public void addAndGetItems_whenAdded_correctlyReturnsItems() {

        assertEquals(1, category.getItems().size());
        assertTrue(category.getItems().contains(item));
    }

    @Test
    public void removeItem_whenRemoved_correctlyRemovesItem() {

        category.removeItem(item);
        assertEquals(0, category.getItems().size());
    }


}
