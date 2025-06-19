package fetch.receipt.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "receipts")
public class Receipt {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "retailer", nullable = false)
    private String retailer;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "purchase_time", nullable = false)
    private LocalTime purchaseTime;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Item> items;

    @Column(name = "total", nullable = false)
    private String total;  // ← changed from BigDecimal to String

    @Column(name = "points")
    private Long points;

    public Receipt() {}

    public Receipt(String id, String retailer, LocalDate purchaseDate, LocalTime purchaseTime, List<Item> items, String total) {
        this.id = id;
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.total = total;
        setItems(items);
    }

    // Bidirectional setter
    public void setItems(List<Item> items) {
        this.items = items;
        if (items != null) {
            items.forEach(item -> item.setReceipt(this));
        }
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRetailer() { return retailer; }
    public void setRetailer(String retailer) { this.retailer = retailer; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public LocalTime getPurchaseTime() { return purchaseTime; }
    public void setPurchaseTime(LocalTime purchaseTime) { this.purchaseTime = purchaseTime; }

    public List<Item> getItems() { return items; }

    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }

    public Long getPoints() { return points; }
    public void setPoints(Long points) { this.points = points; }
}