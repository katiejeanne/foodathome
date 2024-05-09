package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Item;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface CategoryManagementService {

    // Categories contain and manage items.
    // Items only exist within a category.

    Category addItemToCategory(Item item, Category category);

    @Transactional
    void updateItemStatuses(Map<String, String> allParams);
}
