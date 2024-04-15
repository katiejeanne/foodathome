package dev.katiejeanne.foodathome.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="user_household")
    private Household household;

    private String username;

    private String password;

    private String email;

    private String displayName;

    @Enumerated(EnumType.STRING)
    private HouseholdRole householdRole;

    public User() {
        // Default to admin role for new users
        householdRole = HouseholdRole.ROLE_ADMIN;
    }

    public User(String username, String password) {
        // While no role is provided default to Admin role
        this.username = username;
        this.password = password;
        this.householdRole = HouseholdRole.ROLE_ADMIN;
    }

    public User(String username, String password, Household household) {
        // When no role is provided default to Admin role
        this.username = username;
        this.password = password;
        setHousehold(household);
        this.householdRole = HouseholdRole.ROLE_ADMIN;
    }

    public User(String username, String password, Household household, HouseholdRole householdRole) {
        this.username = username;
        this.password = password;
        setHousehold(household);
        this.householdRole = householdRole;

    }


    public long getId() {
        return id;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {

        if (this.household != household && this.household != null) {
            throw new IllegalArgumentException("User already belongs to a household.");
        }
        this.household = household;
        // Add to household if not already added
        if (!household.getUsers().contains(this)) {
            household.addUser(this);
        }


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HouseholdRole getHouseholdRole() {
        return householdRole;
    }

    public void setHouseholdRole(HouseholdRole householdRole) {
        this.householdRole = householdRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
