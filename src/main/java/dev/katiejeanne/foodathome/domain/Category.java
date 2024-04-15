package dev.katiejeanne.foodathome.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "category_household")
    private Household household;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    public Category() {}

    public Category(String name, Household household) {
        this.name = name;
        household.addCategory(this);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        if (this.household != null && this.household != household) {
            throw new IllegalArgumentException("Category already belongs to a household");
        }
        if (this.household == null) {
            this.household = household;
        }
        if (!household.getCategories().contains(this)) {
            household.addCategory(this);
        }

    }

    public List<Item> getItems() {
        return items;
    }


    public void addItem(Item item) {
        // First check for item to avoid an infinite loop
        if (!items.contains(item)) {
            items.add(item);
        }
        if (item.getCategory() != this) {
            item.setCategory(this);
        }

    }

}
