package dev.katiejeanne.foodathome;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.HouseholdRole;
import dev.katiejeanne.foodathome.domain.Item;
import dev.katiejeanne.foodathome.security.CustomUserDetails;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;
import static org.mockito.Mockito.when;

public class TestSetupUtility {

    public static List<Category> getSomeCategoriesWithItems() {
        Household household1 = new Household();
        Category category1 = new Category("Category 1", household1);
        Item item1 = new Item("Item 1", category1);
        Item item2 = new Item("Item 2", category1);
        Category category2 = new Category("Category 2", household1);
        Item item3 = new Item("Item 3", category2);
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
