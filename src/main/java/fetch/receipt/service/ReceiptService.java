package fetch.receipt.service;

import fetch.receipt.dto.ReceiptDto;
import fetch.receipt.exception.ReceiptNotFoundException;
import fetch.receipt.model.Item;
import fetch.receipt.model.Receipt;
import fetch.receipt.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public String processReceipt(ReceiptDto dto) {
        String id = UUID.randomUUID().toString();

        // Convert DTO → Entity (note: price is stored as String now)
        List<Item> items = dto.getItems().stream()
                .map(i -> new Item(i.getShortDescription(), i.getPrice(), null))
                .collect(Collectors.toList());

        // Receipt.total is also String now
        Receipt receipt = new Receipt(
                id,
                dto.getRetailer(),
                LocalDate.parse(dto.getPurchaseDate()),
                LocalTime.parse(dto.getPurchaseTime()),
                items,
                dto.getTotal()
        );

        long points = calculatePoints(receipt);
        receipt.setPoints(points);

        // Set bidirectional references
        receipt.setItems(items);

        receiptRepository.save(receipt);
        return id;
    }

    @Transactional(readOnly = true)
    public long getPoints(String id) {
        return receiptRepository.findById(id)
                .map(Receipt::getPoints)
                .orElseThrow(() -> new ReceiptNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Receipt getReceiptById(String id) {
        return receiptRepository.findById(id)
                .orElseThrow(() -> new ReceiptNotFoundException(id));
    }

    private long calculatePoints(Receipt receipt) {
        long points = 0;

        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        BigDecimal total = new BigDecimal(receipt.getTotal());

        if (total.stripTrailingZeros().scale() <= 0) {
            points += 50;
        }

        if (total.remainder(BigDecimal.valueOf(0.25)).compareTo(BigDecimal.ZERO) == 0) {
            points += 25;
        }

        points += 5 * (receipt.getItems().size() / 2);

        for (Item item : receipt.getItems()) {
            String desc = item.getShortDescription().trim();
            if (desc.length() % 3 == 0) {
                BigDecimal bonus = new BigDecimal(item.getPrice())
                        .multiply(BigDecimal.valueOf(0.2))
                        .setScale(0, RoundingMode.CEILING);
                points += bonus.intValue();
            }
        }

        if (receipt.getPurchaseDate().getDayOfMonth() % 2 == 1) {
            points += 6;
        }

        LocalTime time = receipt.getPurchaseTime();
        if (time.isAfter(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        return points;
    }
}
