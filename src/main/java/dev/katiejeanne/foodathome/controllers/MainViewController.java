package dev.katiejeanne.foodathome.controllers;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainViewController {

    private final CategoryService categoryService;

    public MainViewController (CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String returnBasicMainView(Model theModel) {

        List<Category> categories = categoryService.getAllCategoriesAndItems();

        theModel.addAttribute("categories", categories);

        return "mainview";
    }
}
