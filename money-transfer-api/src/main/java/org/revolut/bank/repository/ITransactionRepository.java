package org.revolut.bank.repository;

import org.revolut.bank.dao.Transaction;

public interface ITransactionRepository {
	
	void createTransaction(Transaction transaction) throws Exception;

}
