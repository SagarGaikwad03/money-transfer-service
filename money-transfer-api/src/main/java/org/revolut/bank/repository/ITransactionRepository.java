package org.revolut.bank.repository;

import java.util.Optional;
import java.util.Queue;

import org.revolut.bank.dao.Transaction;

public interface ITransactionRepository {

	Transaction createTransaction(Transaction transaction) throws Exception;

	Optional<Transaction> getTransactionbyId(final String transactionId);

	Queue<Transaction> getAllTransactions();

}
