package dev.katiejeanne.foodathome.security;

import dev.katiejeanne.foodathome.TestSetupUtility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecurityUtilsTest {

    @BeforeEach
    public void setUpSecurityContext() {

        TestSetupUtility.setUpSecurityContext("test_user","{noop}test", 123L);
    }

    @AfterEach
    public void clearSecurityContext() {
        TestSetupUtility.clearSecurityContext();
    }

    @Test
    public void securityUtilsTest_withLoggedInUser_returnsHouseholdId() {
        long testHouseholdId = 123L;

        assertEquals(testHouseholdId, SecurityUtils.getCurrentHouseholdId());

    }

}
