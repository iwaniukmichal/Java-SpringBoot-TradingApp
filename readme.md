# Exchange It

## Overview
ExchangeIt is a webservice designed to provide a simple interface for individuals starting their journey with the stock exchange. It offers basic functionalities, including stock price diagrams over time, company details, and trading. To engage in trading activities, users need to create an account. Once registered, users can access their transaction history, portfolio, and additional trading functionalities.

The entire application is tailored to support newcomers in understanding the complexities of the stock exchange with minimal risk. It deliberately offers a straightforward interface, allowing users to focus on the essential aspects of the process.

The initial project plan is available [here](./milestones/readme.md).

Api description is available at /swagger-ui/index.html#/

## How to Deploy
For deployment, follow the standard tasks for Spring. Additionally, an existing PostgreSQL (psql) database must be available, typically on localhost:5432. The default database should have the user "postgres" with the password "password" and the database named "tradingdb".

To validate the deployment status by running tests, follow these steps to execute `TradingDataControllerTests` correctly:
1. Ensure the existence of a table named `controllers_tests`. The easiest way to create it is by running `TradingDataControllerTests`, which will indicate that all tests passed, even though no actual tests are defined in the database.
2. Insert test cases into the database. Test cases are available [here](./src/test/resources/controllers_tests.sql).
