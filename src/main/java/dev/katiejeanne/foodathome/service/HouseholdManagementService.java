package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;

import java.util.List;

public interface HouseholdManagementService {

    // Households contain and manage users and categories.
    // Users and categories do not exist outside of a household

    void createNewHousehold();

    void addUser();

    void removeUser();

    void addCategory();

    void removeCategory();

    List<Category> getAllCategoriesAndItems();


}
