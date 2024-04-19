package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
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

    @Transactional
    @Override
    public void saveNewStandaloneUserAndCreateTheirHousehold(User user) {

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

        // Set user's household to created household
        user.setHousehold(household);

        householdRepository.save(household);
    }

    @Transactional
    @Override
    public void addNewUserToHousehold(User user, Household household) {
        if (user.getHousehold() == null) {
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

    @Override
    public void addCategory(Category category, Household household) {

    }

    @Override
    public List<Category> getAllCategoriesWIthItems() {
        Long householdId = SecurityUtils.getCurrentHouseholdId();

        List<Category> categories = categoryRepository.findAllCategoriesWithItemsUsingHouseholdId(householdId);

        return categories;
    }
}
