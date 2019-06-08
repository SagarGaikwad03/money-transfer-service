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
### Get accounts

```
http://localhost:4567/account?accountId=73df405e-7fe9-4d4a-82ee-f7a3bf34026f
```

* HTTP Request:
```json
GET /account HTTP/1.1
Accept: application/json
Host: localhost:4567
Query Param: accountId=73df405e-7fe9-4d4a-82ee-f7a3bf34026f
```

* HTTP Response:
```json
{"object":{"accountId":"73df405e-7fe9-4d4a-82ee-f7a3bf34026f","userName":"Account holder 1","balance":500,"currency":"INR","createdAt":{"date":{"year":2019,"month":6,"day":8},"time":{"hour":13,"minute":24,"second":0,"nano":584000000}},"updatedAt":{"date":{"year":2019,"month":6,"day":8},"time":{"hour":13,"minute":45,"second":41,"nano":906000000}},"lock":{"sync":{"state":0}}},"Status":200}
```

### Update (Withdraw) accounts

```
http://localhost:4567/account?accountId=73df405e-7fe9-4d4a-82ee-f7a3bf34026f&amount=75&transaction=Withdraw
```

* HTTP Request:
```json
PUT /account HTTP/1.1
Accept: application/json
Host: localhost:4567
Query Param: accountId=73df405e-7fe9-4d4a-82ee-f7a3bf34026f
             amount=75
             transaction=Withdraw
```

* HTTP Response:
```json
{"object":{"accountId":"73df405e-7fe9-4d4a-82ee-f7a3bf34026f","userName":"Account holder 1","balance":425,"currency":"INR","createdAt":{"date":{"year":2019,"month":6,"day":8},"time":{"hour":13,"minute":24,"second":0,"nano":584000000}},"updatedAt":{"date":{"year":2019,"month":6,"day":8},"time":{"hour":13,"minute":52,"second":8,"nano":885000000}},"lock":{"sync":{"state":0}}},"Status":201}
```


Delete Account
DELETE - http://localhost:4567/account?accountId={accountId}

Get Transaction
GET - http://localhost:4567/transaction?transactionId={transactionId}

List Transactions
GET - http://localhost:4567/transaction



