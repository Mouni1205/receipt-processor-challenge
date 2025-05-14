package fetch.receipt.service;

import fetch.receipt.exception.ReceiptNotFoundException;
import fetch.receipt.model.Item;
import fetch.receipt.model.Receipt;
import fetch.receipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.UUID;

//Business logic to implement receipt processing
@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public String processReceipt(Receipt receipt) {
        // Generate a unique ID using UUID
        String id = UUID.randomUUID().toString();

        // Save the receipt
        receiptRepository.save(id, receipt);

        // Calculate and save points for the receipt
        long points = calculatePoints(receipt);
        receiptRepository.savePoints(id, points);

        return id;
    }
    public long getPoints(String id) {
        // Check if receipt exists
        if (!receiptRepository.findById(id).isPresent()) {
            throw new ReceiptNotFoundException(id);
        }

        // Return the calculated points
        return receiptRepository.getPoints(id).orElse(0L);
    }

    private long calculatePoints(Receipt receipt) {
        long points = 0;

        // Rule 1: One point per alphanumeric character in retailer name
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Convert total to BigDecimal for accurate calculations
        BigDecimal totalValue = new BigDecimal(receipt.getTotal());

        // Rule 2: 50 points if total ends in .00 (round dollar)
        if (receipt.getTotal().endsWith(".00")) {
            points += 50;
        }

        // Rule 3: 25 points if total is multiple of 0.25
        if (totalValue.remainder(BigDecimal.valueOf(0.25)).compareTo(BigDecimal.ZERO) == 0) {
            points += 25;
        }

        // Rule 4: 5 points for every 2 items
        points += 5 * (receipt.getItems().size() / 2);

        // Rule 5: For items with description length % 3 == 0, add ceil(price * 0.2)
        for (Item item : receipt.getItems()) {
            String desc = item.getShortDescription().trim();
            if (desc.length() % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                BigDecimal bonus = price.multiply(BigDecimal.valueOf(0.2))
                        .setScale(0, RoundingMode.CEILING);
                points += bonus.intValue();
            }
        }

        // Rule 6: 6 points if purchase day is odd
        if (receipt.getPurchaseDate().getDayOfMonth() % 2 == 1) {
            points += 6;
        }

        // Rule 7: 10 points if time is after 2:00pm and before 4:00pm
        LocalTime time = receipt.getPurchaseTime();
        if (time.isAfter(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        return points;
    }

}