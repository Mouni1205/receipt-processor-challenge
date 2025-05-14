package fetch.receipt.exception;

public class ReceiptNotFoundException extends RuntimeException {
    public ReceiptNotFoundException(String id) {
        super("Receipt not found with id: " + id);
    }
}