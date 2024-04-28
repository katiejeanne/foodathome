package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.HouseholdRole;
import dev.katiejeanne.foodathome.domain.User;

import java.util.List;

public interface HouseholdManagementService {

    // Households contain and manage users and categories.
    // Users and categories do not exist outside a household

    Household saveNewStandaloneUserAndCreateTheirHousehold(User user);

    Household addNewUserToHousehold(User user, Household household, HouseholdRole householdRole);

    Category addCategoryToHousehold(Category category, Household household);

    List<Category> getAllCategoriesWIthItems();


}
