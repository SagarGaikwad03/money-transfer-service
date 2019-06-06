package org.revolut.bank.controller;

import static spark.Spark.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.revolut.bank.dao.Account;
import org.revolut.bank.repository.IAccountRepository;

import com.google.gson.Gson;

import spark.Request;

public class AccountController {
	private static String DEPOSIT = "Deposit";
	private static String WITHDRAW = "Withdraw";

	@SuppressWarnings("static-access")
	public AccountController(final IAccountRepository accountRepository) {

		post("/account", (request, response) -> {
			response.type("application/json");

			try {
				return new Gson().toJson(accountRepository.createAccount(accountBuilder(request)));
			} catch (Exception e) {
				response.status(400);
				response.body(e.getLocalizedMessage());
			}
			return new Gson().toJson(request);
		});

		put("/account", (request, response) -> {
			response.type("application/json");

			try {
				Account account = accountRepository.getAccountById(request.queryParams("accountId"));
				BigDecimal amount = new BigDecimal(request.queryParams("amount"));

				if (DEPOSIT.equalsIgnoreCase(request.queryParams("transaction")))
					account = accountRepository.depositMoney(account, amount);
				else if (WITHDRAW.equalsIgnoreCase(request.queryParams("transaction")))
					account = accountRepository.withdrawMoney(account, amount);

				return new Gson().toJson(account);
			} catch (Exception e) {
				response.status(400);
				response.body(e.getLocalizedMessage());
			}
			return new Gson().toJson(request);
		});

		get("/account", (request, response) -> {
			response.type("application/json");

			try {
				Account account = accountRepository.getAccountById(request.queryParams("accountId"));
				return new Gson().toJson(account);
			} catch (Exception e) {
				response.status(400);
				response.body(e.getLocalizedMessage());
			}
			return new Gson().toJson(request);
		});

	}

	private Account accountBuilder(Request request) {

		Account account = new Gson().fromJson(request.body(), Account.class);

		return Account.builder().accountId(UUID.randomUUID().toString()).userName(account.getUserName())
				.balance(account.getBalance()).currency(account.getCurrency()).createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now()).build();
	}

}
