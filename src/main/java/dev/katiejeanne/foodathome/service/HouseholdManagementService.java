package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.User;

import java.util.List;

public interface HouseholdManagementService {

    // Households contain and manage users and categories.
    // Users and categories do not exist outside of a household

    void saveNewStandaloneUserAndCreateTheirHousehold(User user);

    void addNewUserToHousehold(User user, Household household);

    void addCategory(Category category, Household household);

    List<Category> getAllCategoriesWIthItems();


}
