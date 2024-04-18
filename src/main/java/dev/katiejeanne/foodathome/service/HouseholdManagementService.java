package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;

import java.util.List;

public interface HouseholdService {

    void createHousehold();

    void addUser();

    void removeUser();

    void addCategory();

    void removeCategory();

    List<Category> getAllCategoriesAndItems();


}
