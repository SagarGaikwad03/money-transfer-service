# RESTful API for money transfer between accounts
Standalone service for money transfer between account.

## Technology stack
- Java 8
- Maven
- Spark Framework
- Lombok
- GSON
- JUnit
- Spark Test

## Command to run jar
- java -jar ".\target\money-transfer-api.jar"

## Services
Create Account \n
POST - http://localhost:4567/account
Post body Sample - {"userName":"Sagar Gaikwad","balance":1000,"currency":"INR"}

Get Account
GET - http://localhost:4567/account?accountId={accountId}

Update Account (Deposit/Withdraw)
PUT - http://localhost:4567/account?accountId={accountId}&amount={amount}&transaction={Deposite}

Delete Account
DELETE - http://localhost:4567/account?accountId={accountId}

Transfer Money
POST - http://localhost:4567/transfer
Post body sample - {"fromAccountId":"e447ac53-4765-4ac6-846a-6a1822b36238","toAccountId":"f04b8a02-9551-48d0-b995-d0fe5fd49a49","amount":"5000"}

Get Transaction
GET - http://localhost:4567/transaction?transactionId={transactionId}

List Transactions
GET - http://localhost:4567/transaction



