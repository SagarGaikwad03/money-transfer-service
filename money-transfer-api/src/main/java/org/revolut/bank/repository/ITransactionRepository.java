package org.revolut.bank.repository;

import java.util.Optional;
import java.util.Queue;

import org.revolut.bank.dao.Transaction;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Transaction Repository Interface
 * */
public interface ITransactionRepository {

	Transaction createTransaction(Transaction transaction) throws Exception;

	Optional<Transaction> getTransactionbyId(final String transactionId);

	Queue<Transaction> getAllTransactions();

}
