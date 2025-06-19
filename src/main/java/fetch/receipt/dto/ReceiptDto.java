package fetch.receipt.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public class ReceiptDto {

    @NotNull(message = "Retailer is required")
    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "Retailer format is invalid")
    private String retailer;

    @NotNull(message = "Purchase date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date must be in YYYY-MM-DD format")
    private String purchaseDate;

    @NotNull(message = "Purchase time is required")
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Time must be in HH:MM 24-hour format")
    private String purchaseTime;

    @NotEmpty(message = "Items cannot be empty")
    private List<@Valid ItemDto> items;

    @NotNull(message = "Total is required")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be in format 0.00")
    private String total;

    // Getters and setters
    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
