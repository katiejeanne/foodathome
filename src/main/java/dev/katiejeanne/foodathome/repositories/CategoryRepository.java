package dev.katiejeanne.foodathome.repositories;

import dev.katiejeanne.foodathome.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c JOIN FETCH c.items WHERE c.household.id = :householdId")
    List<Category> findAllCategoriesWithItemsUsingHouseholdId(@Param("householdId") Long householdId);
}
