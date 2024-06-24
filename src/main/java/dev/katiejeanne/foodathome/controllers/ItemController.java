package dev.katiejeanne.foodathome.controllers;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Item;
import dev.katiejeanne.foodathome.service.CategoryManagementService;
import dev.katiejeanne.foodathome.service.HouseholdManagementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ItemController {

    private final HouseholdManagementService householdManagementService;

    private CategoryManagementService categoryManagementService;

    public ItemController(HouseholdManagementService householdManagementService, CategoryManagementService categoryManagementService) {
        this.householdManagementService = householdManagementService;
        this.categoryManagementService = categoryManagementService;
    }

    @GetMapping("/additem")
    public String addNewItem(Model theModel) {

        Item newItem = new Item();

        theModel.addAttribute("item", newItem);

        List<Category> categories = householdManagementService.getAllCategoriesWIthItems();

        theModel.addAttribute("categories", categories);

        return "itemdetails";
    }

    @GetMapping("/edititem/{id}")
    public String editItem(@PathVariable Long id, Model theModel) {
        Item item = categoryManagementService.findItem(id);
        List<Category> categories = householdManagementService.getAllCategoriesWIthItems();
        theModel.addAttribute("item", item);
        theModel.addAttribute("categories", categories);
        return "itemdetails";
    }

    @PostMapping("/saveItem")
    public String saveItem(@ModelAttribute Item item) {
        categoryManagementService.saveItem(item);
        return "redirect:/";
    }





}
