# Exchange It

ExchangeIt is a web application designed as a simple stock exchange simulator, perfect for individuals beginning their journey with the stock market. It provides a user-friendly interface to practice trading without real-world risk.

## Features

-   **User Authentication:** Secure registration and login functionality.
-   **Stock Trading:** Buy and sell stocks of various companies.
-   **Portfolio Management:** View your current holdings and track their value.
-   **Transaction History:** Review a complete history of your trades.
-   **Real-Time Data:** Fetches latest stock data using the Alpha Vantage API.
-   **API Documentation:** Interactive API documentation available via Swagger UI.

## Technology Stack

-   **Backend:** Java, Spring Boot (Web, Data JPA, Security)
-   **Frontend:** Thymeleaf, Bootstrap, jQuery
-   **Database:** PostgreSQL
-   **Build Tool:** Maven
-   **API Documentation:** Springdoc (Swagger UI)

## Getting Started

### Prerequisites

-   Java Development Kit (JDK) 17 or later
-   Apache Maven
-   PostgreSQL database server

### Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/Java-SpringBoot-TradingApp.git
    cd Java-SpringBoot-TradingApp
    ```

2.  **Configure the database:**
    -   Make sure your PostgreSQL server is running.
    -   Create a database named `tradingdb`.
    -   Update the database credentials in `src/main/resources/application.properties` if they differ from the defaults:
        ```properties
        spring.datasource.username=postgres
        spring.datasource.password=password
        ```

3.  **Build and run the application:**
    ```bash
    mvn spring-boot:run
    ```
    The application will be accessible at `http://localhost:8080`.

## API Documentation

Once the application is running, you can explore the REST API documentation using Swagger UI by navigating to:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Testing

To run the provided integration tests, you may need to set up the test database environment as described in the original `readme.md`. The tests use an in-memory H2 database but some tests may require a specific setup.
To run the tests, execute:
```bash
mvn test
```