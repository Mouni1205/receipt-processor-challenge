package fetch.receipt.model;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_description", nullable = false)
    private String shortDescription;

    @Column(name = "price", nullable = false)
    private String price; // changed from BigDecimal to String

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    public Item() {
    }

    public Item(String shortDescription, String price, Receipt receipt) {
        this.shortDescription = shortDescription;
        this.price = price;
        this.receipt = receipt;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

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

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
