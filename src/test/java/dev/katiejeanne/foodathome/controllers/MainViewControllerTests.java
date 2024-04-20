package dev.katiejeanne.foodathome.controllers;

import dev.katiejeanne.foodathome.TestSetupUtility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(MainViewController.class)
public class MainViewControllerTests {

//    @MockBean
//    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        TestSetupUtility.setUpSecurityContext("test_user","test",1L);
    }

    @AfterEach
    public void tearDown() {
        TestSetupUtility.clearSecurityContext();
    }

    @Test
    public void returnBasicMainView_withBasicData_returnsCorrectResponse() throws Exception {

//        // Set up mock categories
//        List<Category> mockCategories = TestSetupUtility.getSomeCategoriesWithItems();
//        when(categoryService.getAllCategoriesAndItems()).thenReturn(mockCategories);
//
//        // Perform request and assert response
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("mainview"))
//                .andExpect(model().attributeExists("categories"));
//
//        // Verify mocked service was used
//        verify(categoryService).getAllCategoriesAndItems();

    }


}
