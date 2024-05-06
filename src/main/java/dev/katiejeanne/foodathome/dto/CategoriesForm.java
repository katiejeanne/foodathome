package dev.katiejeanne.foodathome.dto;

import dev.katiejeanne.foodathome.domain.Category;

import java.util.List;

public class CategoriesForm {

    private List<Category> categoriesAndItems;

    public List<Category> getCategoriesAndItems() {
        return categoriesAndItems;
    }

    public void setCategoriesAndItems(List<Category> categoriesAndItems) {
        this.categoriesAndItems = categoriesAndItems;
    }
}
