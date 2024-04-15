package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import dev.katiejeanne.foodathome.security.SecurityUtils;

import java.util.List;

public interface CategoryService {

    public List<Category> getAllCategoriesAndItems();
}
