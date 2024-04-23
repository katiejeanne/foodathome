package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.HouseholdRole;
import dev.katiejeanne.foodathome.domain.User;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import dev.katiejeanne.foodathome.repositories.HouseholdRepository;
import dev.katiejeanne.foodathome.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HouseholdManagementServiceImpl implements HouseholdManagementService {


    HouseholdRepository householdRepository;

    CategoryRepository categoryRepository;

    public HouseholdManagementServiceImpl(HouseholdRepository householdRepository, CategoryRepository categoryRepository) {
        this.householdRepository = householdRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public void saveNewStandaloneUserAndCreateTheirHousehold(User user) {

        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }

        if (user.getHousehold() != null) {
            throw new IllegalArgumentException("User already belongs to a household.");
        }

        // Create new Household
        Household household = new Household();

        // Add user to household
        household.addUser(user);

        // Set household name
        if (user.getDisplayName() != null) {
            household.setName(user.getDisplayName() + "'s Household Inventory");
        }
        else {
            household.setName("My Household Inventory");
        }

        // Set user to admin role
        user.setHouseholdRole(HouseholdRole.ROLE_ADMIN);

        // Set user's household to created household
        user.setHousehold(household);

        householdRepository.save(household);
    }

    @Transactional
    @Override
    public void addNewUserToHousehold(User user, Household household, HouseholdRole householdRole) {

        if (user == null || household == null) {
            throw new IllegalArgumentException("User and household must not be null.");
        }

        if (user.getHousehold() == null) {
            user.setHouseholdRole(householdRole);
            household.addUser(user);
            user.setHousehold(household);
            householdRepository.save(household);
        }
        else if (user.getHousehold() == household) {
            throw new IllegalStateException("User already belongs to this household");
        }
        else {
            throw new IllegalStateException("User already belongs to a different household.");
        }
    }

    @Transactional
    @Override
    public void addCategory(Category category, Household household) {

        if (category == null || household == null) {
            throw new IllegalArgumentException("Household and user must not be null.");
        }

        if (category.getHousehold() == null) {
            household.addCategory(category);
            category.setHousehold(household);
            householdRepository.save(household);
        }
        else if (category.getHousehold() == household) {
            throw new IllegalStateException("Category already belongs to this household");
        }
        else {
            throw new IllegalStateException("Category already belongs to a different household.");
        }

    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> getAllCategoriesWIthItems() {

        // Retrieve the householdId from the currently logged in user
        Long householdId = SecurityUtils.getCurrentHouseholdId();

        return categoryRepository.findCategoriesByHouseholdId(householdId);
    }
}
