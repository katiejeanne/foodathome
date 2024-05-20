package dev.katiejeanne.foodathome.controllers;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.dto.CategoriesForm;
import dev.katiejeanne.foodathome.service.CategoryManagementService;
import dev.katiejeanne.foodathome.service.HouseholdManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainViewController {

    private final HouseholdManagementService householdManagementService;

    private final CategoryManagementService categoryManagementService;

    public MainViewController (HouseholdManagementService householdManagementService, CategoryManagementService categoryManagementService) {
        this.householdManagementService = householdManagementService;
        this.categoryManagementService = categoryManagementService;
    }

    @GetMapping("/")
    public String returnBasicMainView(Model theModel) {

        List<Category> categories = householdManagementService.getAllCategoriesWIthItems();

        CategoriesForm categoriesForm = new CategoriesForm();
        categoriesForm.setCategoriesAndItems(categories);

        theModel.addAttribute("categoriesForm", categoriesForm);

        return "mainview";
    }

    @PostMapping({"/", "/available", "/shoppinglist"})
    public String updateStatus(@RequestParam Map<String, String> allParams, RedirectAttributes redirectAttributes) {

        try {
            categoryManagementService.updateItemStatuses(allParams);
            redirectAttributes.addFlashAttribute("successMessage", "All items were successfully updated.");
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Operation failed. No items were updated.");
        }


        return "redirect:/";
    }

    @GetMapping("/available")
    public String getAvailableItems(Model theModel) {

        List<Category> allCategoriesAndItems = householdManagementService.getAllCategoriesWIthItems();
        List<Category> categoriesWithAvailableItems = new ArrayList<>();

        for (Category category : allCategoriesAndItems) {
            Category tempCategory = categoryManagementService.getAvailableItems(category);
            categoriesWithAvailableItems.add(tempCategory);
        }

        CategoriesForm categoriesForm = new CategoriesForm();
        categoriesForm.setCategoriesAndItems(categoriesWithAvailableItems);

        theModel.addAttribute("categoriesForm", categoriesForm);

        return "mainview";
    }

    @GetMapping("/shoppinglist")
    public String getShoppingList(Model theModel) {

        List<Category> allCategoriesAndItems = householdManagementService.getAllCategoriesWIthItems();
        List<Category> categoriesWithLowOrOutItems = new ArrayList<>();

        for (Category category : allCategoriesAndItems) {
            Category tempCategory = categoryManagementService.getLowAndOutItems(category);
            categoriesWithLowOrOutItems.add(tempCategory);
        }

        CategoriesForm categoriesForm = new CategoriesForm();
        categoriesForm.setCategoriesAndItems(categoriesWithLowOrOutItems);

        theModel.addAttribute("categoriesForm", categoriesForm);

        return "mainview";
    }
}
