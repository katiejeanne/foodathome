package dev.katiejeanne.foodathome.controllers;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.dto.CategoriesForm;
import dev.katiejeanne.foodathome.service.HouseholdManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MainViewController {

    private final HouseholdManagementService householdManagementService;

    public MainViewController (HouseholdManagementService householdManagementService) {
        this.householdManagementService = householdManagementService;
    }

    @GetMapping("/")
    public String returnBasicMainView(Model theModel) {

        List<Category> categories = householdManagementService.getAllCategoriesWIthItems();

        CategoriesForm categoriesForm = new CategoriesForm();
        categoriesForm.setCategoriesAndItems(categories);

        theModel.addAttribute("categoriesForm", categoriesForm);

        return "mainview";
    }

    @PostMapping("/")
    public String updateStatus(Model theModel, CategoriesForm categoriesForm) {



        return "redirect:/";
    }
}
