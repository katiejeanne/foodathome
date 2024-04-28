package dev.katiejeanne.foodathome;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.HouseholdRole;
import dev.katiejeanne.foodathome.domain.Item;
import dev.katiejeanne.foodathome.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestSetupUtility {

    public static List<Category> getSomeCategoriesWithItems() {

        // Create household to hold categories
        Household household1 = new Household();

        // Create category 1
        Category category1 = new Category();
        household1.addCategory(category1);
        category1.setHousehold(household1);

        // Add item to category 1
        Item item1 = new Item();
        item1.setCategory(category1);
        category1.addItem(item1);

        // Add second item to category 1
        Item item2 = new Item();
        item2.setCategory(category1);
        category1.addItem(item2);

        // Add second category to household
        Category category2 = new Category();
        category2.setHousehold(household1);
        household1.addCategory(category2);

        // Add item to second category
        Item item3 = new Item();
        item3.setCategory(category2);
        category2.addItem(item3);

        return Arrays.asList(category1, category2);

    }

    public static void setUpSecurityContext(String username, String password, long householdId, HouseholdRole householdRole) {

        CustomUserDetails customUserDetails = new CustomUserDetails(username, password, Collections.singleton(householdRole), householdId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        securityContext.setAuthentication(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    public static void setUpSecurityContext(String username, String password, long householdId) {
        // If no role is provided use ROLE_ADMIN as a default
        setUpSecurityContext(username, password, householdId, HouseholdRole.ROLE_ADMIN);
    }

    public static void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }


}
