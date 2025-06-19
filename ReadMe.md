# Receipt Processor Challenge

A Spring Boot-based web service that processes retail receipts and calculates reward points based on specific business rules. The application exposes RESTful endpoints to submit receipts and retrieve the corresponding reward points. It uses PostgreSQL for persistent storage and is deployable as a containerized service using AWS Fargate.

## Features

- POST `/receipts/process`  
  Accepts a receipt JSON payload, validates the fields, assigns a unique UUID, stores the receipt in PostgreSQL, calculates reward points, and returns the generated receipt ID.

- GET `/receipts/{id}/points`  
  Returns the points associated with a previously submitted receipt using its ID.

## Tech Stack

- Java 17  
- Spring Boot 3.1.0  
- PostgreSQL  
- Docker  
- AWS ECS Fargate  
- AWS ECR  
- Gradle 8.3  
- Postman for testing

## Sample Payloads

### Submit Receipt (POST)

**Endpoint:** `POST /receipts/process`  
**Payload:**

```json
{
  "retailer": "Target",
  "purchaseDate": "2023-06-12",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Pepsi - 12 pack",
      "price": "5.99"
    },
    {
      "shortDescription": "Eggs",
      "price": "3.25"
    }
  ],
  "total": "9.24"
}
```

**Sample Response:**

```json
{
  "id": "84e23acc-98fb-469d-b777-b75f099dfa51"
}
```

### Get Points (GET)

**Endpoint:** `GET /receipts/{id}/points`  
**Example:**  
`GET /receipts/84e23acc-98fb-469d-b777-b75f099dfa51/points`

**Response:**

```json
{
  "points": 67
}
```

## Environment Variables

The following environment variables must be set for the application to connect to the PostgreSQL database:

| Key                      | Description                       | Example                                                   |
|--------------------------|-----------------------------------|-----------------------------------------------------------|
| SPRING_DATASOURCE_URL    | JDBC URL of the PostgreSQL DB     | jdbc:postgresql://your-db-host:5432/receipt_db            |
| SPRING_DATASOURCE_USERNAME | PostgreSQL DB username         | postgres                                                  |
| SPRING_DATASOURCE_PASSWORD | PostgreSQL DB password         | your_password_here                                        |

These can be set in your local Docker run command or in the AWS ECS task definition under container environment variables.

## Local Build & Run

1. Build Docker Image  
   ```bash
   docker build -t receipt_processor_challenge:latest .
   ```

2. Run Container Locally  
   ```bash
   docker run -p 8080:8080 \
     -e SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/receipt_db \
     -e SPRING_DATASOURCE_USERNAME=postgres \
     -e SPRING_DATASOURCE_PASSWORD=your_password \
     receipt_processor_challenge:latest
   ```

3. Test the API  
   Open Postman or visit `http://localhost:8080`

## AWS Deployment

- Docker image is pushed to Amazon ECR
- ECS Task Definition is created using this image
- ECS Fargate service is configured to run the container
- PostgreSQL is hosted using Amazon RDS
- Security groups are configured to allow inbound traffic on ports 8080 (app) and 5432 (database)