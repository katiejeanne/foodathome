package dev.katiejeanne.foodathome.init;

import dev.katiejeanne.foodathome.domain.*;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import dev.katiejeanne.foodathome.repositories.HouseholdRepository;
import dev.katiejeanne.foodathome.repositories.ItemRepository;
import dev.katiejeanne.foodathome.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private final HouseholdRepository householdRepository;

    public DataInitializer(CategoryRepository categoryRepository,
                           ItemRepository itemRepository,
                           UserRepository userRepository,
                           HouseholdRepository householdRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.householdRepository = householdRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        List<Item> storedItems = itemRepository.findAll();
        // If no items are in the database initialize with default data.
        if (storedItems.isEmpty()) {
            initializeData();
        }
    }

    private void initializeData () {
        // TODO: Fill with default data
    }
}
