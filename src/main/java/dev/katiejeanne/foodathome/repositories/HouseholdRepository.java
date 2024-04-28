package dev.katiejeanne.foodathome.repositories;

import dev.katiejeanne.foodathome.domain.Household;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdRepository extends JpaRepository<Household, Long> {

}
