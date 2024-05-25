package dev.katiejeanne.foodathome.service;

import dev.katiejeanne.foodathome.domain.Category;
import dev.katiejeanne.foodathome.domain.Item;
import dev.katiejeanne.foodathome.domain.Status;
import dev.katiejeanne.foodathome.exception.ItemNotFoundException;
import dev.katiejeanne.foodathome.exception.UnauthorizedUserException;
import dev.katiejeanne.foodathome.repositories.CategoryRepository;
import dev.katiejeanne.foodathome.repositories.ItemRepository;
import dev.katiejeanne.foodathome.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CategoryManagementServiceImpl implements CategoryManagementService {

    private final CategoryRepository categoryRepository;

    private final ItemRepository itemRepository;

    @Autowired
    public CategoryManagementServiceImpl(CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    @Override
    public Category addItemToCategory(Item item, Category category) {

        if (item == null || category == null) {
            throw new IllegalArgumentException("Item and category must not be null.");
        }

        if (item.getCategory() != null && item.getCategory() != category) {
            removeItemFromCategory(item);
        }
        category = assignNewCategory(item, category);

        return category;
    }

    private void removeItemFromCategory(Item item) {
        Category formerCategory = item.getCategory();
        formerCategory.removeItem(item);
        categoryRepository.save(formerCategory);
    }

    private Category assignNewCategory(Item item, Category category) {
        item.setCategory(category);
        if (!category.getItems().contains(item)) {
            category.addItem(item);
        }
        category = categoryRepository.save(category);
        return category;
    }

    @Override
    @Transactional
    public void updateItemStatuses(Map<String, String> allParams) {

        // Get the household id of the active user
        Long householdId = SecurityUtils.getCurrentHouseholdId();

        allParams.forEach((item, status) -> {

            // check if param contains an item status
            if (!item.startsWith("status-")) {
                return;
            }

            try {
                // get item's Id from param
                Long itemId = Long.parseLong(item.substring(7));

                // retrieve item from repository
                Item tempItem = itemRepository.findById(itemId).
                        orElseThrow(() -> new ItemNotFoundException("Item " + itemId + " not found"));

                // compare household ids to make sure they match
                Long itemHouseholdId = tempItem.getCategory().getHousehold().getId();
                if (!Objects.equals(itemHouseholdId, householdId)) {
                    throw new UnauthorizedUserException("User not authorized to update this item.");
                }

                // Update item status and save
                tempItem.setStatus(Status.valueOf(status));
                itemRepository.save(tempItem);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid item format for " + item, e);
            }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status for " + item, e);
            }

        });

    }

    public Category getAvailableItems(Category category) {

        List<Item> allItems = category.getItems();

        Category tempCategory = new Category();
        tempCategory.setName(category.getName());

        for (Item item : allItems) {
            if (item.getStatus().equals(Status.IN_STOCK) || item.getStatus().equals(Status.LOW_STOCK)) {
                tempCategory.addItem(item);
            }
        }

        return tempCategory;
    }

    public Category getLowAndOutItems(Category category) {

        List<Item> allItems = category.getItems();

        Category tempCategory = new Category();
        tempCategory.setName(category.getName());

        for (Item item : allItems) {
            if (item.getStatus().equals(Status.LOW_STOCK) || item.getStatus().equals(Status.OUT_OF_STOCK)) {
                tempCategory.addItem(item);
            }
        }

        return tempCategory;
    }

    @Transactional
    @Override
    public void saveItem(Item item) {

        System.out.println("Item id: " + item.getId());

        Long previousCategoryId = 0L;

        if (item.getId() != null) {
            // Retrieve the previously saved data for this item
            Item previousItem = itemRepository.findById(item.getId()).orElseThrow(() -> new ItemNotFoundException("Item could not be found"));

            // Store the previous category id for update later
            previousCategoryId = previousItem.getCategory().getId();


            // Compare household Ids to make sure user is authorized to modify this item
            Long householdId = previousItem.getCategory().getHousehold().getId();
            Long userHouseholdId = SecurityUtils.getCurrentHouseholdId();
            if (!householdId.equals(userHouseholdId)) {
                throw new UnauthorizedUserException("User not authorized to modify this item.");
            }
        }

        Item savedItem = itemRepository.save(item);
        Long currentCategoryId = savedItem.getCategory().getId();

        // Compare category Ids to see if they have changed
        if (previousCategoryId != 0L && !previousCategoryId.equals(currentCategoryId)) {
            // If item was previously assigned to a different category, remove the item from that category.
            System.out.println("Item categories are different.");
            Category previousCategory = categoryRepository.findById(previousCategoryId).orElseThrow();
            previousCategory.removeItem(savedItem);
            categoryRepository.save(previousCategory);
        }

        // Find the current category the item is assigned to
        Category currentCategory = categoryRepository.findById(currentCategoryId).orElseThrow();

        // Only add the item to the current category if it doesn't already belong to that category
        if (!currentCategory.getItems().contains(savedItem)) {
            currentCategory.addItem(savedItem);
            categoryRepository.save(currentCategory);
        }

    }

    @Override
    public Item findItem(Long id) {
        return itemRepository.findById(id).orElseThrow();
    }


}
