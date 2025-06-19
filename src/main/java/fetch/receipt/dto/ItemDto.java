package fetch.receipt.dto;

import jakarta.validation.constraints.*;

public class ItemDto {

    @NotNull(message = "Short description is required")
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = "Short description format is invalid")
    private String shortDescription;

    @NotNull(message = "Price is required")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Price must be in format 0.00")
    private String price;

    // Getters and setters
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
