package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import dev.katiejeanne.foodathome.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllCategoriesAndItems() {
        Long householdId = SecurityUtils.getCurrentHouseholdId();

        List<Category> categories = categoryRepository.findAllCategoriesWithItemsUsingHouseholdId(householdId);

        return categories;
    }
}
