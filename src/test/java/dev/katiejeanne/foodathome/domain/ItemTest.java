package dev.katiejeanne.foodathome.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {


    @Test
    public void defaultConstructor_setsStatusToInStock() {
        Item item = new Item();
        assertEquals(Status.IN_STOCK, item.getStatus());
    }

    @ParameterizedTest
    @EnumSource(Status.class)
    public void setStatus_whenSet_UpdatesStats(Status status) {
        Item item = new Item();
        item.setStatus(status);
        assertEquals(status, item.getStatus());
    }

    @Test
    public void setName_newItem_setsName() {

        String itemName = "Test Item";
        Item item = new Item();
        item.setName(itemName);

        assertEquals(itemName, item.getName());
    }


}
