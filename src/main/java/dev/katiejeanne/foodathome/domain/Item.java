package dev.katiejeanne.foodathome.domain;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "item_category")
    private Category category;

    public Item() {
        this.status = Status.IN_STOCK;
    }

    public Item(String name, Category category) {
        // Default to "In Stock"
        this.name = name;
        this.status = Status.IN_STOCK;
        setCategory(category);
    }

    public long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category newCategory) {

        if (this.category != null && this.category != newCategory) {
            this.category.getItems().remove(this);
        }
        this.category = newCategory;
        if (!category.getItems().contains(this)) {
            category.addItem(this);
        }

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
