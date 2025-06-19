package fetch.receipt.controller;

import fetch.receipt.dto.IdResponse;
import fetch.receipt.dto.PointsResponse;
import fetch.receipt.dto.ReceiptDto;
import fetch.receipt.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    // POST /receipts/process
    @PostMapping("/process")
    public ResponseEntity<IdResponse> processReceipt(@Valid @RequestBody ReceiptDto receiptDto) {
        String id = receiptService.processReceipt(receiptDto);
        return ResponseEntity.ok(new IdResponse(id));
    }

    // GET /receipts/{id}/points
    @GetMapping("/{id}/points")
    public ResponseEntity<PointsResponse> getPoints(@PathVariable String id) {
        long points = receiptService.getPoints(id);
        return ResponseEntity.ok(new PointsResponse(points));
    }
}