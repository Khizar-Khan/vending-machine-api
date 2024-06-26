# Vending Machine API

A Spring Boot-powered API and CLI application for managing a vending machine, featuring coin initialisation, deposit, change dispensing, and coin status viewing. Designed with a focus on modularity, reusability, and robust error handling.

## Design Decisions

### Separation of Concerns

The application is designed with a clear separation of concerns:

- **Controller**: Manages the API endpoints.
- **Service**: Contains the business logic for handling vending machine operations.
- **CLI**: Provides an interactive command-line interface for users to interact with the vending machine API.

### Modularity and Reusability

- **Refactored Methods**: Input collection, HTTP request sending, and menu display are refactored into separate methods to enhance code readability and maintainability.
- **Enhanced Input Handling**: Methods such as `parseIntWithValidation` ensure robust input handling with proper error messages.
- **Coin Class**: Encapsulates coin-related data (denomination and count) into a dedicated class, making the code more modular and easier to extend. This allows for better data encapsulation and management of coin-related logic.

### User Experience

- **ANSI Escape Codes**: Used to colorise output in the command line, making responses and error messages easily distinguishable.
- **Informative Responses**: Provides clear and informative responses for API requests, enhancing the overall user experience.

### Error Handling

- **Input Validation**: Ensures that only valid inputs are processed, with clear error messages guiding the user to correct inputs.
- **Exception Handling**: Catches and logs exceptions, providing meaningful error messages without crashing the application.

## API Endpoints

### Initialise Vending Machine

- **Endpoint**: `/initialise`  
- **Method**: `POST`  
- **Description**: Initialises the vending machine with the specified coins.  
- **Request Body**:

```json
[
{"denomination": 1, "count": 10},
{"denomination": 2, "count": 5},
{"denomination": 5, "count": 2}
]
```

### Deposit Coins

- **Endpoint**: `/deposit`
- **Method**: `POST`
- **Description**: Deposits coins into the vending machine.
- **Request Body**:

```json
[
{"denomination": 1, "count": 5},
{"denomination": 2, "count": 3}
]
```

### Get Change
- **Endpoint**: `/change`
- **Method**: `POST`
- **Description**: Provides change for the specified amount.
- **Query Parameter**: 
   - `amount`: The amount of change required.

### View Coins
- **Endpoint**: `/coins`
- **Method**: `GET`
- **Description**: Retrieves the current state of coins in the vending machine.

## Building and Running the Application

### Prerequisites

- Java 17 or higher
- Maven

### Building the Project

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/vending-machine-api.git
   cd vending-machine-api
   ```

2. Build the project using Maven:
   ```sh
   mvn clean install
   ```

### Running the Application

1. Start the Spring Boot application:

   ```sh
   mvn spring-boot:run
   ```

2. In a separate terminal, run the CLI application:
   ```sh
   java -cp target/<jar-file>.jar com.example.vendingmachine.VendingMachineCLI
   ```

### Usage

The CLI application provides the following options:

1. **Initialise Vending Machine**: Sets the initial float of the vending machine.
2. **Deposit Coins**: Adds coins to the vending machine.
3. **Get Change**: Dispenses change for a specified amount.
4. **View Coins**: Displays the current state of coins in the vending machine.
5. **Exit**: Exits the application.

### Example Interaction

```
Vending Machine CLI

1. Initialise Vending Machine
2. Deposit Coins
3. Get Change
4. View Coins
5. Exit
   Choose an option: 1
   Enter denomination (or 'done' to finish): 1
   Enter count: 10
   Enter denomination (or 'done' to finish): 2
   Enter count: 5
   Enter denomination (or 'done' to finish): 5
   Enter count: 2
   Enter denomination (or 'done' to finish): done
   Response: Vending machine initialised

Choose an option: 3
Enter amount to get change for: 7
Response: [{denomination=5, count=1}, {denomination=2, count=1}]
```

## Design Justifications

- **Use of Spring Boot**: Provides a robust framework for building and managing the API endpoints, ensuring scalability and maintainability.
- **ANSI Escape Codes**: Enhance the user experience by making the CLI output more readable and visually appealing.
- **Error Handling**: Comprehensive error handling and input validation ensure the application is robust and user-friendly.
- **Refactoring for Reusability**: Methods are designed to be reusable and modular, making the codebase easier to maintain and extend.
- **Coin Class**: Creating a `Coin` class encapsulates coin-related data and logic, making the application more modular, maintainable, and easier to extend in the future. It also simplifies the handling of coin-related operations and improves data encapsulation.

## Conclusion

This project demonstrates a well-structured API application for managing a vending machine, with a focus on modularity, reusability, and robust error handling. The backend API is built using Spring Boot, ensuring scalability and maintainability. The CLI serves as an interactive interface to utilise the API, showcasing the seamless integration of front-end and back-end components. The design decisions and implementation choices aim to provide a scalable and maintainable solution, showcasing software development best practices.
