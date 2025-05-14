package fetch.receipt.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Receipt {

    @NotNull(message = "Retailer cannot be null")
    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "Retailer name format is invalid")
    private String retailer;

    @NotNull(message = "Purchase date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @NotNull(message = "Purchase time cannot be null")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime purchaseTime;

    @NotNull(message = "Items cannot be null")
    @NotEmpty(message = "At least one item is required")
    @Size(min = 1, message = "At least one item is required")
    @Valid
    private List<Item> items;

    @NotNull(message = "Total cannot be null")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be in format 0.00")
    private String total;

    // Default constructor required for Jackson deserialization
    public Receipt() {
    }

    // Constructor with all fields for easier testing
    public Receipt(String retailer, LocalDate purchaseDate, LocalTime purchaseTime, List<Item> items, String total) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.items = items;
        this.total = total;
    }

    // Getters and Setters
    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}