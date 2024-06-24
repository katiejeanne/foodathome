package dev.katiejeanne.foodathome.init;

import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import dev.katiejeanne.foodathome.repositories.HouseholdRepository;
import dev.katiejeanne.foodathome.repositories.ItemRepository;
import dev.katiejeanne.foodathome.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Profile("test")
public class DataInitializerTests {

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void run_blankRepository_correctlyInitializesData() throws Exception {

        dataInitializer.run();

        assertEquals(2, userRepository.findAll().size());
        assertEquals(2, householdRepository.findAll().size());
        assertEquals(6, categoryRepository.findAll().size());
        assertEquals(31, itemRepository.findAll().size());

    }

}
