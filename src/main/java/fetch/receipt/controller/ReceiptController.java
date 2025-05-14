package fetch.receipt.controller;

import fetch.receipt.dto.IdResponse;
import fetch.receipt.model.PointsResponse;
import fetch.receipt.model.Receipt;
import fetch.receipt.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//From the entry point the control jumps to controller, this class handles the two REST API end points.
// process :- post end point which is used for getting the receipt from the user and then give id as response.
// points :- get end point which calculates points based on the rules defined in the service layer and gives the calculated points as response.
@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<IdResponse> processReceipt(@Valid @RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        return new ResponseEntity<>(new IdResponse(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<PointsResponse> getPoints(@PathVariable String id) {
        long points = receiptService.getPoints(id);
        return new ResponseEntity<>(new PointsResponse(points), HttpStatus.OK);
    }
}