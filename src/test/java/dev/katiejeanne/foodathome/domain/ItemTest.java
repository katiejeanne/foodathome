package dev.katiejeanne.foodathome.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    private Item item;

    private Category category;

    private String testName = "My Item";

    @BeforeEach
    public void setUpItem() {
        item = new Item();
        item.setName(testName);

        category = new Category();
        category.addItem(item);
        item.setCategory(category);
    }

    @Test
    public void defaultConstructor_setsStatusToInStock() {

        assertEquals(Status.IN_STOCK, item.getStatus());
    }

    @Test
    public void setAndGetCategory_whenSet_setCategory() {

        assertEquals(category, item.getCategory());
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    public void setStatus_whenSet_UpdatesStats(Status status) {

        item.setStatus(status);
        assertEquals(status, item.getStatus());
    }

    @Test
    public void setAndGetName_whenSet_setsName() {

        assertEquals(testName, item.getName());
    }


}
