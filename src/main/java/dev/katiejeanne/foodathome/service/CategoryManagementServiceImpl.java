package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Item;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryManagementServiceImpl implements CategoryManagementService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryManagementServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public void addItem(Item item, Category category) {

        if (item == null || category == null) {
            throw new IllegalArgumentException("Item and category must not be null.");
        }

        if (item.getCategory() != null && item.getCategory() != category) {
            removeItemFromCategory(item);
        }
        assignNewCategory(item, category);
    }

    private void removeItemFromCategory(Item item) {
        Category formerCategory = item.getCategory();
        formerCategory.removeItem(item);
        categoryRepository.save(formerCategory);
    }

    private void assignNewCategory(Item item, Category category) {
        item.setCategory(category);
        if (!category.getItems().contains(item)) {
            category.addItem(item);
        }
        categoryRepository.save(category);
    }


}
