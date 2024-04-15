package dev.katiejeanne.foodathome.service;


import dev.katiejeanne.foodathome.TestSetupUtility;
import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import dev.katiejeanne.foodathome.security.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void getAllCategoriesAndItems_withLoggedInUser_returnsCorrectCategoriesAndItems() {

        long expectedHouseholdId = 1L;

        // Make sure mock of static method closes after use
        try (MockedStatic<SecurityUtils> mockedStatic = mockStatic(SecurityUtils.class)) {

            // Set up mocks
            mockedStatic.when(SecurityUtils::getCurrentHouseholdId)
                    .thenReturn(expectedHouseholdId);

            List<Category> mockedRepositoryResults = TestSetupUtility.getSomeCategoriesWithItems();

            when(categoryRepository.findAllCategoriesWithItemsUsingHouseholdId(expectedHouseholdId))
                    .thenReturn(mockedRepositoryResults);

            // Run method
            List<Category> testedMethodResults = categoryService.getAllCategoriesAndItems();

            // Test results
            assertNotNull(testedMethodResults);
            assertEquals(mockedRepositoryResults.size(), testedMethodResults.size());
            assertEquals(mockedRepositoryResults, testedMethodResults);

            // Verify mocks were accessed
            mockedStatic.verify(SecurityUtils::getCurrentHouseholdId);
            verify(categoryRepository).findAllCategoriesWithItemsUsingHouseholdId(expectedHouseholdId);
        }

    }

}
