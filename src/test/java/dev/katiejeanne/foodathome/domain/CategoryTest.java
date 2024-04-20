package dev.katiejeanne.foodathome.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void setName_validName_correctlySetsName() {

        String categoryName = "Test Category";
        Category category = new Category();
        category.setName(categoryName);

        assertEquals(categoryName, category.getName());
    }


}
