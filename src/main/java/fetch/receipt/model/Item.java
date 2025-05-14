package fetch.receipt.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Item {

    @NotNull(message = "Short description cannot be null")
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = "Short description format is invalid")
    private String shortDescription;

    @NotNull(message = "Price cannot be null")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Price must be in format 0.00")
    private String price;

    // Default constructor required for Jackson deserialization
    public Item() {
    }

    // Constructor with all fields for easier testing
    public Item(String shortDescription, String price) {
        this.shortDescription = shortDescription;
        this.price = price;
    }

    // Getters and Setters
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
