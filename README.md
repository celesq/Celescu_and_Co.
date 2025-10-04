# Celescu & Co.

Built a modular **backend banking platform** in **Java**, designed to emulate real-world financial systems with a strong focus on scalability, extensibility, and fault tolerance.  
The project supports **25+ users**, **50+ accounts across 10 currencies**, and **600+ validated operations**, including deposits, withdrawals, transfers, currency exchanges, card payments, and real-time balance tracking.  
It leverages a suite of **design patterns** — **Factory**, **Builder**, **Strategy**, **Observer**, and **Singleton** — to ensure modularity, robustness, and maintainability while preserving clarity and cohesion in the architecture.

# System Overview

The application simulates a fully functional digital banking system where users can:
- Create and manage **classic** or **savings** accounts.  
- Generate **classic** or **one-time** cards linked to accounts.  
- Perform **money transfers** and **split transactions** between users.  
- Execute **currency conversions** using up-to-date exchange rates.  
- Monitor **balance status** and automatically freeze or warn cards through an **Observer Pattern**.  
- Maintain complete **transaction histories** and **aliases** for simplified transfers.  

All input data (users, merchants, accounts, rates, and commands) is loaded from structured JSON files.  
Each command is executed in sequence, and all outputs (including errors) are written to a JSON-formatted output file.

# Core Features and Functionality

## Account Management  
Each account can be of type **ClassicAccount** or **SavingsAccount**, created dynamically via a **Factory Pattern** that instantiates the appropriate subclass based on the input field `accountType`.

**Example:**
```java
if (accountType.equals("classic"))
    return new ClassicAccount(...);
else
    return new SavingsAccount(...);
```
This approach enables seamless extension when adding new account types without modifying existing logic.

Each account maintains:
- Unique **IBAN identifiers**
- **Currency type** and **initial balance**
- **Transaction history**
- Associated **cards** and **aliases**

## Card System  
Two types of cards exist:
- **ClassicCard** – persistent cards tied to accounts, capable of multiple transactions.  
- **OneTimeCard** – disposable cards that deactivate automatically after one use.  

Both implement the **Card interface**, which defines standard behaviors such as `payOnline`, `checkCardStatus`, and `freeze()`.  
Cards inherit reusable functionality from parent classes, minimizing code duplication and ensuring strong encapsulation.

## Transactions  
All transaction-related logic is encapsulated within a dedicated `Transaction` class implemented using the **Builder Pattern**.  
Since transactions vary in parameters (e.g., currency, merchant, target account), the builder provides flexibility while ensuring immutability.

**Builder Example:**
```java
Transaction t = new Transaction.Builder()
    .setDescription("Online purchase")
    .setAmount(59.99)
    .setMerchant("Amazon")
    .setTimestamp(currentTime)
    .build();
```

Each transaction records:
- Description and timestamp (mandatory fields)
- Optional fields like sender, receiver, currency, and merchant  
- Conversion rate (if applicable)

The Builder Pattern keeps the code clean, reduces constructor overloads, and improves code readability.

## Observer Pattern — Balance Monitoring  
A **BalanceObserver** monitors all accounts, ensuring financial integrity across operations.  
When `checkCardStatus` is triggered, it evaluates the account’s balance:
- If below the **minimum threshold**, all linked cards are **frozen**.  
- If balance is within 30 units of the minimum, cards enter a **warning state**.  

Accounts act as **subjects**, and cards are **observers** — this relationship ensures automatic synchronization between account state and card availability.

Although the ideal implementation would continuously observe balance changes after every transaction, test constraints required explicit status checks upon command execution.  
Nevertheless, the Observer Pattern remains conceptually correct and modular, supporting future scalability.

## Factory & Strategy Patterns — Account Creation and Currency Conversion  
The **Factory Pattern** centralizes account creation, while the **Strategy Pattern** is applied to currency conversion.  
Through `calculateExchangeRate`, the system dynamically selects the optimal conversion strategy based on exchange paths available in the dataset.  

Rates are stored in lists and iterated multiple times to guarantee coverage for indirect conversions.  
This method ensures accurate conversion across **10+ currencies**, maintaining mathematical consistency and test compliance.

## Currency Operations  

The system provides a comprehensive set of **currency-related operations**, essential for managing multi-currency banking behavior:  

### `getExchangeRate`  
Retrieves the current exchange rate between two currencies. If a direct rate is unavailable, the system finds an indirect conversion path using intermediary currencies, ensuring complete connectivity between all currencies in the dataset.  

### `exchangeMoney`  
Transfers an amount from one account to another while automatically converting the currency according to the current exchange rate.  
Balances are adjusted in both accounts, maintaining precision up to two decimal places.

### `convertCurrency`  
Internally used in `Transactions` and `Utils`, this method standardizes cross-currency computations for all financial operations.  

### Example:  
```
> exchangeMoney EUR RON 120.50
< Exchanged 120.50 EUR into 598.43 RON at rate 4.9652
```

All exchange rates and conversions are validated to prevent negative balances or unsupported currency pairs.


## Alias System  
Aliases simplify transactions by allowing users to register custom identifiers linked to their IBANs.  
A `HashMap<String, String>` stores each alias-account mapping.  
During `sendMoney` or `split` operations, if a recipient is not recognized as an IBAN, the alias map is checked.  
This guarantees intuitive user experience and eliminates repetitive lookups.

## Exception Handling  
To ensure robustness, all commands are wrapped in `try-catch` blocks.  
Invalid commands (e.g., sending money to nonexistent accounts, insufficient balance, or invalid alias) generate standardized outputs, ensuring graceful error recovery.  
When JSON-based validation conflicted with error messages, the system safely logged them to console instead of breaking JSON structure.

## Data Management and Flow  
All input entities — users, accounts, exchange rates, merchants, and transactions — are parsed and initialized in the `Start` class via `parseDataAndStart()`.  
Command execution is delegated to the `Output` handler, which iterates through each command and calls the corresponding logic module.

**Flow Summary:**
1. Parse JSON input  
2. Initialize lists of accounts, users, rates, and merchants  
3. Execute commands sequentially  
4. Append structured outputs to JSON array  
5. Write results to output file  

## Utilities and Helper Methods  
The `Utils` class centralizes utility methods, such as:
- `roundToTwoDecimalPlaces(double value)` – ensures financial precision.  
- `putTransactionInObject(Transaction t)` – converts transaction objects into JSON-ready nodes.  

These methods streamline serialization and prevent redundancy across modules.

# Design Patterns Overview  

| Pattern | Application | Role |
|----------|--------------|------|
| **Factory** | Account creation | Dynamically selects correct account subclass |
| **Builder** | Transaction creation | Simplifies construction of flexible transaction types |
| **Observer** | Card monitoring | Syncs account balance with card state |
| **Strategy** | Exchange rate calculation | Supports multiple conversion approaches |
| **Singleton** | Shared data management | Ensures consistent global access to configurations |

# Implementation Notes

- **Accounts** and **cards** use inheritance for shared behavior, while interfaces (`Account`, `Card`) define consistent contracts.  
- Data is stored efficiently using **ArrayLists** and **HashMaps**, balancing dynamic growth with fast lookup.  
- **Merchant data**, **exchange rates**, and **transaction lists** are fully encapsulated within specialized data classes.  
- The **Output** module handles both textual and JSON serialization.  
- Transaction history is maintained per account, enabling filtering and retrieval for auditing purposes.  
- **Aliasing**, **exchange rate conversions**, and **balance verifications** are handled through clean, modular abstractions.

# Key Operations Supported

| Command | Description |
|----------|-------------|
| `createAccount` | Creates a new user account (classic or savings) |
| `createCard` / `createOneTimeCard` | Generates a new card for a given account |
| `addFunds` | Adds money to an account in a specific currency |
| `payOnline` | Processes an online purchase via a card |
| `sendMoney` | Transfers money between accounts or via alias |
| `split` | Splits a transaction among multiple recipients |
| `checkCardStatus` | Evaluates card state and applies Observer logic |
| `getTransactions` | Retrieves all transaction history for a user |
| `exchange` | Converts currency using current exchange rate data |

# Technical Summary

This project showcases advanced OOP and software design capabilities, emphasizing:
- Strong **encapsulation** and **single-responsibility** architecture  
- Full **command-driven execution** and **deterministic behavior**  
- Robust **error handling** and **state consistency**  
- High maintainability through **pattern-oriented structure**

# Summary

**Modular Banking System** is a robust simulation of a financial backend, reflecting modern architectural principles used in large-scale enterprise systems.  
It demonstrates practical application of **software design patterns**, efficient state management, and JSON-driven execution, while ensuring clarity, safety, and performance.  
Built as a clean, extensible codebase, it can be easily expanded into a full-stack online banking platform, preserving deterministic behavior and strong domain encapsulation — qualities aligned with **Big Tech engineering standards**.
