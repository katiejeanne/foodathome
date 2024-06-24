package dev.katiejeanne.foodathome.controllers;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.service.CategoryManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    CategoryManagementService categoryManagementService;

    public CategoryController(CategoryManagementService categoryManagementService) {
        this.categoryManagementService = categoryManagementService;
    }

    @GetMapping("/addcategory")
    public String addNewCategory(Model theModel) {
        Category newCategory = new Category();

        theModel.addAttribute("category", newCategory);

        return "categorydetails";
    }

    @GetMapping("/editcategory/{id}")
    public String editCategory(@PathVariable Long id, Model theModel) {
        Category existingCategory = categoryManagementService.findCategory(id);
        theModel.addAttribute("category", existingCategory);
        return "categorydetails";
    }

    @PostMapping("/savecategory")
    public String saveCategory(@ModelAttribute Category category) {
        categoryManagementService.saveCategory(category);
        return "redirect:/";
    }
}
