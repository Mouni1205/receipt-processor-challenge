package fetch.receipt.repository;

import fetch.receipt.model.Receipt;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

//ConcurrentHashMap to implement in-memory database
@Repository
public class ReceiptRepository {

    // Thread-safe map to store receipts along with IDs
    private final Map<String, Receipt> receipts = new ConcurrentHashMap<>();

    // Thread-safe map to store points calculated for each receipt
    private final Map<String, Long> points = new ConcurrentHashMap<>();

    public Receipt save(String id, Receipt receipt) {
        receipts.put(id, receipt);
        return receipt;
    }
    public Optional<Receipt> findById(String id) {
        return Optional.ofNullable(receipts.get(id));
    }
    public void savePoints(String id, long pointsValue) {
        points.put(id, pointsValue);
    }
    public Optional<Long> getPoints(String id) {
        return Optional.ofNullable(points.get(id));
    }
}
