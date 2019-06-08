package org.revolut.bank.controller;

import static spark.Spark.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.eclipse.jetty.http.HttpStatus;
import org.revolut.bank.dao.Account;
import org.revolut.bank.dao.Response;
import org.revolut.bank.exception.AccountDoesNotExistsException;
import org.revolut.bank.exception.AccountLowBalanceException;
import org.revolut.bank.exception.AccountNumberBlankOrEmptyException;
import org.revolut.bank.repository.IAccountRepository;

import com.google.gson.Gson;

import spark.Request;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Account Service
 * */
public class AccountController {

	private static String DEPOSIT = "Deposit";
	private static String WITHDRAW = "Withdraw";

	@SuppressWarnings("static-access")
	public AccountController(final IAccountRepository accountRepository) {

		/**
		 * Create account
		 * 
		 * @param - account
		 * @return custom response
		 */
		post("/account", (request, response) -> {
			response.type("application/json");
			Response res;

			try {
				Account account = accountRepository.createAccount(accountBuilder(request));
				res = Response.builder().object(account).Status(HttpStatus.CREATED_201).build();

			} catch (Exception e) {
				String errorMessage = e.getMessage();
				if (e.getClass().isInstance(AccountDoesNotExistsException.class))
					errorMessage = ((AccountDoesNotExistsException) e).getMessage();

				res = Response.builder().message(errorMessage).Status(HttpStatus.BAD_REQUEST_400).build();

			}
			return new Gson().toJson(res);
		});

		/**
		 * Update account
		 * 
		 * @param - accountId, amount, transaction
		 * @return custom response
		 */
		put("/account", (request, response) -> {
			response.type("application/json");
			Response res;

			try {
				Account account = accountRepository.getAccountById(request.queryParams("accountId"));
				BigDecimal amount = new BigDecimal(request.queryParams("amount"));

				if (DEPOSIT.equalsIgnoreCase(request.queryParams("transaction")))
					account = accountRepository.depositMoney(account, amount);
				else if (WITHDRAW.equalsIgnoreCase(request.queryParams("transaction")))
					account = accountRepository.withdrawMoney(account, amount);

				res = Response.builder().object(account).Status(HttpStatus.CREATED_201).build();
			} catch (Exception e) {

				String errorMessage = e.getMessage();
				if (e.getClass().isInstance(AccountDoesNotExistsException.class))
					errorMessage = ((AccountDoesNotExistsException) e).getMessage();
				else if (e.getClass().isInstance(AccountLowBalanceException.class))
					errorMessage = ((AccountLowBalanceException) e).getMessage();

				res = Response.builder().message(errorMessage).Status(HttpStatus.BAD_REQUEST_400).build();
			}
			return new Gson().toJson(res);
		});

		/**
		 * Find account
		 * 
		 * @param - account id
		 * @return custom response
		 */

		get("/account", (request, response) -> {
			response.type("application/json");
			Response res;

			try {
				Account account = accountRepository.getAccountById(request.queryParams("accountId"));

				res = Response.builder().object(account).Status(HttpStatus.OK_200).build();

			} catch (Exception e) {

				String errorMessage = e.getMessage();
				if (e.getClass().isInstance(AccountDoesNotExistsException.class))
					errorMessage = ((AccountDoesNotExistsException) e).getMessage();

				res = Response.builder().message(errorMessage).Status(HttpStatus.BAD_REQUEST_400).build();

			}
			return new Gson().toJson(res);
		});

		/**
		 * Delete account
		 * 
		 * @param - account id
		 * @return custom response
		 */

		delete("/account", (request, response) -> {
			response.type("application/json");
			Response res;

			try {
				accountRepository.delete(request.queryParams("accountId"));

				res = Response.builder().Status(HttpStatus.OK_200).build();

			} catch (Exception e) {

				String errorMessage = e.getMessage();
				if (e.getClass().isInstance(AccountDoesNotExistsException.class))
					errorMessage = ((AccountDoesNotExistsException) e).getMessage();
				else if (e.getClass().isInstance(AccountNumberBlankOrEmptyException.class))
					errorMessage = ((AccountNumberBlankOrEmptyException) e).getMessage();

				res = Response.builder().message(errorMessage).Status(HttpStatus.BAD_REQUEST_400).build();

			}
			return new Gson().toJson(res);
		});

	}

	private Account accountBuilder(Request request) {

		Account account = new Gson().fromJson(request.body(), Account.class);

		return Account.builder().accountId(UUID.randomUUID().toString()).userName(account.getUserName())
				.balance(account.getBalance()).currency(account.getCurrency()).createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now()).build();
	}

}
