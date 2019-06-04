package org.revolut.bank.repository;

import java.util.LinkedList;
import java.util.Queue;

import org.revolut.bank.dao.Account;
import org.revolut.bank.dao.Transaction;

public class TransactionRepository implements ITransactionRepository {

	private final Queue<Transaction> transactions = new LinkedList<Transaction>();
	private AccountRepository accountRepository;

	public void createTransaction(Transaction transaction) throws Exception {
		Account fromAccount = accountRepository.getAccountById(transaction.getFromAccountId());
		Account toAccount = accountRepository.getAccountById(transaction.getToAccountId());
				
		accountRepository.withdrawMoney(fromAccount, transaction.getAmount());
		accountRepository.depositMoney(toAccount, transaction.getAmount());
		
		transactions.add(transaction);
	}

}
