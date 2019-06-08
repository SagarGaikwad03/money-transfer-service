package org.revolut.bank.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.time.LocalDateTime;
import java.util.UUID;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.StringUtil;
import org.revolut.bank.dao.Response;
import org.revolut.bank.dao.Transaction;
import org.revolut.bank.exception.AccountDoesNotExistsException;
import org.revolut.bank.exception.AccountLowBalanceException;
import org.revolut.bank.repository.ITransactionRepository;

import com.google.gson.Gson;

import spark.Request;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Transaction Service
 * */
public class TransactionController {

	@SuppressWarnings("static-access")
	public TransactionController(ITransactionRepository transactionRepository) {
		/**
	     * Transfer Money
	     * @param - transaction
	     * @return custom response
	     * */
		post("/transfer", (request, response) -> {
			response.type("application/json");
			Response res;

			try {
				Transaction transaction = transactionRepository.createTransaction(transactionBuilder(request));
				
				res = Response.builder().object(transaction).Status(HttpStatus.CREATED_201).build();

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
	     * Find Transaction
	     * @param - transactionId
	     * @return custom response
	     * */
		get("/transaction", (request, response) -> {
			response.type("application/json");
			Response res;

			try {
				if (!StringUtil.isBlank(request.queryParams("transactionId")))
					res = Response.builder()
							.object(transactionRepository.getTransactionbyId(request.queryParams("transactionId")))
							.Status(HttpStatus.OK_200).build();

				else
					res = Response.builder().object(transactionRepository.getAllTransactions())
							.Status(HttpStatus.OK_200).build();

			} catch (Exception e) {
				res = Response.builder().message(e.getMessage()).Status(HttpStatus.BAD_REQUEST_400).build();
			}
			return new Gson().toJson(res);
		});
	}

	private Transaction transactionBuilder(Request request) {
		Transaction transaction = new Gson().fromJson(request.body(), Transaction.class);
		return Transaction.builder().transactionId(UUID.randomUUID().toString())
				.fromAccountId(transaction.getFromAccountId()).toAccountId(transaction.getToAccountId())
				.amount(transaction.getAmount()).createdAt(LocalDateTime.now()).build();
	}
}
