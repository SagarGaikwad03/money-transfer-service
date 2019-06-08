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
## Endpoints:
### Create accounts

```
http://localhost:4567/account
```

* HTTP Request:
```json
POST /account HTTP/1.1
Accept: application/json
Host: localhost:4567

{"userName":"Account holder 1","balance":1000,"currency":"INR"}

```

* HTTP Response:
```json
{"object":{"accountId":"73df405e-7fe9-4d4a-82ee-f7a3bf34026f","userName":"Account holder 1","balance":1000,"currency":"INR","createdAt":{"date":{"year":2019,"month":6,"day":8},"time":{"hour":13,"minute":24,"second":0,"nano":584000000}},"updatedAt":{"date":{"year":2019,"month":6,"day":8},"time":{"hour":13,"minute":24,"second":0,"nano":584000000}},"lock":{"sync":{"state":0}}},"Status":201}
```

### Transfer amount between account

```
http://localhost:4567/transfer
```

* HTTP Request:
```json
POST /transfer HTTP/1.1
Accept: application/json
Host: localhost:4567

{"fromAccountId":"73df405e-7fe9-4d4a-82ee-f7a3bf34026f","toAccountId":"9dada52d-23f6-495c-b60f-a0921aa9943f","amount":"500"}
```

* HTTP Response:
```json
{"object":{"transactionId":"75706d49-889a-47bf-a640-380cdc42bb9a","fromAccountId":"73df405e-7fe9-4d4a-82ee-f7a3bf34026f","toAccountId":"9dada52d-23f6-495c-b60f-a0921aa9943f","amount":500,"createdAt":{"date":{"year":2019,"month":6,"day":8},"time":{"hour":13,"minute":45,"second":41,"nano":906000000}},"inProcess":{"value":0}},"Status":201}
```

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



