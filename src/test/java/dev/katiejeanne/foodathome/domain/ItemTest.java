package dev.katiejeanne.foodathome.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    @Test
    public void itemConstructorWithCategory_newItemBeingConstructed_correctlyMirrorsRelationship() {

        Household household = new Household();
        Category category = new Category("testCategory",household);
        Item item = new Item("testItem", category);

        assertEquals(category, item.getCategory());
        assertTrue(category.getItems().contains(item));
        assertEquals(Status.IN_STOCK, item.getStatus());
        assertEquals("testItem", item.getName());
    }

    @Test
    public void defaultConstructor_setsStatusToInStock() {
        Item item = new Item();
        assertEquals(Status.IN_STOCK, item.getStatus());
    }

    @Test
    public void setCategory_newItem_correctlyMirrorsRelationship() {
        Category category = new Category();
        Item item = new Item();

        item.setCategory(category);

        assertEquals(category, item.getCategory());
        assertTrue(category.getItems().contains(item));
    }

    @Test
    public void setCategory_ItemWithExistingCategory_correctlySwitchesRelationship() {
        Category category1 = new Category();
        Category category2 = new Category();
        Item item = new Item();

        item.setCategory(category1);
        item.setCategory(category2);

        assertEquals(category2, item.getCategory());
        assertTrue(category2.getItems().contains(item));
        assertFalse(category1.getItems().contains(item));
    }

    @Test
    public void setCategory_ItemAlreadyBelongsToCategory_PassesWithoutDuplicates() {

        Category category = new Category();
        Item item = new Item("Test Item", category);
        item.setCategory(category);

        assertEquals(1, category.getItems().size());
        assertTrue(category.getItems().contains(item));
        assertEquals(category, item.getCategory());
    }

    @Test
    public void setStatus_setInStocktoLowStock_setsStatus() {

        Item item = new Item();
        item.setStatus(Status.LOW_STOCK);

        assertEquals(Status.LOW_STOCK, item.getStatus());
    }

    @Test
    public void setStatus_setInStockToOutOfStock_setsStatus() {

        Item item = new Item();
        item.setStatus(Status.OUT_OF_STOCK);

        assertEquals(Status.OUT_OF_STOCK, item.getStatus());
    }

    @Test
    public void setStatus_setLowStockToInStock_setsStatus() {
        Item item = new Item();
        item.setStatus(Status.LOW_STOCK);
        item.setStatus(Status.IN_STOCK);

        assertEquals(Status.IN_STOCK, item.getStatus());
    }

    @Test
    public void setStatus_setLowStockToOutOfStock_setsStatus() {
        Item item = new Item();
        item.setStatus(Status.LOW_STOCK);
        item.setStatus(Status.OUT_OF_STOCK);

        assertEquals(Status.OUT_OF_STOCK, item.getStatus());
    }

    @Test
    public void setStatus_setOutOfStockToInStock_setsStatus() {

        Item item = new Item();
        item.setStatus(Status.OUT_OF_STOCK);
        item.setStatus(Status.IN_STOCK);

        assertEquals(Status.IN_STOCK, item.getStatus());
    }

    @Test
    public void setStatus_setOutOfStockToLowStock_setStatus() {

        Item item = new Item();
        item.setStatus(Status.OUT_OF_STOCK);
        item.setStatus(Status.LOW_STOCK);

        assertEquals(Status.LOW_STOCK, item.getStatus());
    }

    @Test
    public void setName_newItem_setsName() {

        String itemName = "Test Item";
        Item item = new Item();
        item.setName(itemName);

        assertEquals(itemName, item.getName());
    }


}
