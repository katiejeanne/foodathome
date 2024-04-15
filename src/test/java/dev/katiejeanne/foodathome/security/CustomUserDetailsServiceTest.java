package dev.katiejeanne.foodathome.security;

import dev.katiejeanne.foodathome.domain.Household;
import dev.katiejeanne.foodathome.domain.HouseholdRole;
import dev.katiejeanne.foodathome.domain.User;
import dev.katiejeanne.foodathome.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void loadByUsername_withValidUser_ReturnsUserDetailsObject() {

        // Set up test user
        User testUser = new User("test_user","{noop}test");

        Household mockHousehold = mock(Household.class);
        when(mockHousehold.getId()).thenReturn(1L);

        testUser.setHousehold(mockHousehold);

        when(userRepository.findByUsername("test_user")).thenReturn(Optional.of(testUser));


        // Perform function
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername("test_user");

        // Test results
        assertEquals("test_user", userDetails.getUsername());
        assertEquals("{noop}test", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(HouseholdRole.ROLE_ADMIN));
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals(mockHousehold.getId(), userDetails.getHouseholdId());

    }

    @Test
    public void loadByUsername_withInvalidUser_throwsUsernameNotFoundException() {

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("not_a_username");
        });

        assertEquals("Username not_a_username not found", exception.getMessage());


    }


}
