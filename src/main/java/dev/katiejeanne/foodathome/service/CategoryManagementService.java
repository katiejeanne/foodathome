package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Item;

import java.util.List;

public interface CategoryManagementService {

    // Categories contain and manage items.
    // Items only exist within a category.

    void addNewItem(Item item);

    List<Item> getItems();

}
