package dev.katiejeanne.foodathome.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();


    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    public Household () {}

    public Household(String name, User user) {

        this.name = name;
        if (user.getHousehold() != null) {
            throw new IllegalArgumentException("User already belongs to a household");
        }
        users.add(user);
        // Set user household to this if not already set
        if (user.getHousehold() == null) {
            user.setHousehold(this);
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        if (user.getHousehold() != null & user.getHousehold() != this) {
            throw new IllegalArgumentException("User already belongs to a household.");
        }
        if (!users.contains(user)) {
            users.add(user);
        }
        if (user.getHousehold() == null)  {
            user.setHousehold(this);
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        if (category.getHousehold() != null & category.getHousehold() != this) {
            throw new IllegalArgumentException("Category already belongs to a household.");
        }
        if (category.getHousehold() == null)  {
            category.setHousehold(this);
        }
        if (!categories.contains(category)) {
            categories.add(category);
        }
    }


}
