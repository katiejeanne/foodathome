package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Item;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryManagementServiceImpl implements CategoryManagementService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryManagementServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public Category addItemToCategory(Item item, Category category) {

        if (item == null || category == null) {
            throw new IllegalArgumentException("Item and category must not be null.");
        }

        if (item.getCategory() != null && item.getCategory() != category) {
            removeItemFromCategory(item);
        }
        category = assignNewCategory(item, category);

        return category;
    }

    private void removeItemFromCategory(Item item) {
        Category formerCategory = item.getCategory();
        formerCategory.removeItem(item);
        categoryRepository.save(formerCategory);
    }

    private Category assignNewCategory(Item item, Category category) {
        item.setCategory(category);
        if (!category.getItems().contains(item)) {
            category.addItem(item);
        }
        category = categoryRepository.save(category);
        return category;
    }


}
