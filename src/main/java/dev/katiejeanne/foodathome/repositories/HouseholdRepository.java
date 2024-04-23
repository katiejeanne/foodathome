package dev.katiejeanne.foodathome.repositories;

import dev.katiejeanne.foodathome.domain.Household;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseholdRepository extends JpaRepository<Household, Long> {

}
