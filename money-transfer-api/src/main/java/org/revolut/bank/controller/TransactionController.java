package org.revolut.bank.controller;

import static spark.Spark.get;
import static spark.Spark.post;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

import org.eclipse.jetty.util.StringUtil;
import org.revolut.bank.dao.Account;
import org.revolut.bank.dao.Transaction;
import org.revolut.bank.repository.ITransactionRepository;

import com.google.gson.Gson;

import spark.Request;

public class TransactionController {

	@SuppressWarnings("static-access")
	public TransactionController(ITransactionRepository transactionRepository) {
		post("/transfer", (request, response) -> {
			response.type("application/json");

			try {
				Transaction transaction = transactionRepository.createTransaction(transactionBuilder(request));
				return new Gson().toJson(transaction);
			} catch (Exception e) {
				response.status(400);
				response.body(e.getLocalizedMessage());
			}
			return new Gson().toJson(request);
		});

		get("/transaction", (request, response) -> {
			response.type("application/json");

			try {
				if (!StringUtil.isBlank(request.queryParams("transactionId"))) {
					return new Gson()
							.toJson(transactionRepository.getTransactionbyId(request.queryParams("transactionId")));

				} else
					return new Gson().toJson(transactionRepository.getAllTransactions());

			} catch (Exception e) {
				response.status(400);
				response.body(e.getLocalizedMessage());
			}
			return new Gson().toJson(request);
		});
	}

	private Transaction transactionBuilder(Request request) {
		Transaction transaction = new Gson().fromJson(request.body(), Transaction.class);
		return Transaction.builder().transactionId(UUID.randomUUID().toString())
				.fromAccountId(transaction.getFromAccountId()).toAccountId(transaction.getToAccountId())
				.amount(transaction.getAmount()).createdAt(LocalDateTime.now()).build();
	}
}
