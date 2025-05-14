Receipt Processor Challenge

I have designed a Spring Boot based web service to process retail receipts and calculate reward points based on specific rules. The application provides RESTful endpoints to submit receipts and retrieve the points awarded for them. All data is stored in-memory, with no external database required.

Application Flow

1. ReceiptProcessorApplication initializes the Spring context and exposes the API on port 8080.
2. POST /receipts/process :- Accepts Receipt Json payload, validates the input using jakarta annotations, assigns unique UUID, stores the receipt in memory, applies business rules to calculate points, and finally returns the generates ID.
3. GET /receipts/{id}/points :- Accepts the receipt id in the path and returns the previously calculated points for that receipt.

Build Instructions: 

1. Build Docker Image :- docker build -t receipt_processor_challenge:latest .
2. Run Docker Container :- docker run -p 8080:8080 receipt_processor_challenge:latest

API will be available at:

http://localhost:8080

Built with Java 17, Spring Boot 3.1.0, Gradle 8.3.
Tested with Docker and Postman (screenshots included under /screenshots).