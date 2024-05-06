package dev.katiejeanne.foodathome.domain;

public enum Status {

    IN_STOCK("in stock"),
    LOW_STOCK("low stock"),
    OUT_OF_STOCK("out of stock");

    private final String displayValue;

    Status(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
