package dev.katiejeanne.foodathome.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void argumentConstructor_newCategory_correctlyCreatesCategory() {
        String householdName = "Test Category";
        Household testHousehold = new Household();
        Category testCategory = new Category(householdName, testHousehold);

        assertNotNull(testCategory);
        assertEquals(householdName, testCategory.getName());
        assertEquals(testHousehold, testCategory.getHousehold());
    }

    @Test
    public void setHousehold_newCategory_correctlyMirrorsRelationship() {
        Household testHousehold = new Household();
        Category testCategory = new Category();
        testCategory.setHousehold(testHousehold);

        assertEquals(testHousehold, testCategory.getHousehold());
        assertTrue(testHousehold.getCategories().contains(testCategory));
    }

    @Test
    public void setHousehold_CategoryAlreadyAssignedToOtherHousehold_BlocksChangeAndThrowsError() {
        Household testHousehold1 = new Household();
        Household testHousehold2 = new Household();
        Category testCategroy = new Category("Test Category", testHousehold1);

        assertThrows(IllegalArgumentException.class, () -> testCategroy.setHousehold(testHousehold2));
        assertTrue(testHousehold1.getCategories().contains(testCategroy));
        assertFalse(testHousehold2.getCategories().contains(testCategroy));
        assertEquals(testHousehold1, testCategroy.getHousehold());
    }

    @Test
    public void setHousehold_CategoryAlreadyAssignedToSameHousehold_PassesWithoutErrorOrDuplication() {
        Household testHousehold = new Household();
        Category testCategory = new Category("Test Category", testHousehold);
        testCategory.setHousehold(testHousehold);

        assertEquals(1, testHousehold.getCategories().size());
        assertTrue(testHousehold.getCategories().contains(testCategory));
        assertEquals(testHousehold, testCategory.getHousehold());

    }


    @Test
    public void addItem_newItem_correctlyMirrorsRelationship() {
        Category category = new Category();
        Item item = new Item();

        category.addItem(item);
        assertTrue(category.getItems().contains(item));
        assertEquals(category, item.getCategory());

    }

    @Test
    public void addItem_ItemWithExistingCategory_correctlySwitchesRelationship() {

        Category category1 = new Category();
        Category category2 = new Category();
        Item item = new Item();
        category1.addItem(item);
        category2.addItem(item);

        assertTrue(category2.getItems().contains(item));
        assertFalse(category1.getItems().contains(item));
        assertEquals(category2, item.getCategory());
    }

    @Test
    public void addItem_ItemAlreadyBelongsToSameCategory_PassesWithNoErrorsOrDuplicates() {

        Category testCategory = new Category();
        Item item = new Item();
        testCategory.addItem(item);
        testCategory.addItem(item);

        assertEquals(1, testCategory.getItems().size());
        assertTrue(testCategory.getItems().contains(item));
        assertEquals(testCategory, item.getCategory());
    }

    @Test
    public void setName_validName_correctlySetsName() {

        String categoryName = "Test Category";
        Category category = new Category();
        category.setName(categoryName);

        assertEquals(categoryName, category.getName());
    }


}
