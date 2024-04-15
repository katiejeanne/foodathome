package dev.katiejeanne.foodathome.repositories;

import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class HouseholdRepositoryTests {

    @Autowired
    HouseholdRepository householdRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void saveHousehold_NewHouseholdWithUser_persistsBothHouseholdAndUser() {

        Household household = new Household();
        User user = new User();
        household.addUser(user);
        householdRepository.save(household);
        testEntityManager.flush();
        testEntityManager.clear();

        Household persistedHousehold = householdRepository.findById(household.getId()).orElseThrow();

        assertNotNull(persistedHousehold);
        assertEquals(1, persistedHousehold.getUsers().size());
        for (User persistedUser : persistedHousehold.getUsers()) {
            assertEquals(persistedHousehold.getId(), persistedUser.getHousehold().getId());
        }
    }

    @Test
    public void saveHousehold_UpdatedHouseholdWithUser_updatesHouseholdAndUser() {

        Household household = new Household();
        User user = new User();
        household.addUser(user);
        householdRepository.save(household);
        testEntityManager.flush();
        testEntityManager.clear();

        Household persistedHousehold = householdRepository.findById(household.getId()).orElseThrow();




    }




}
