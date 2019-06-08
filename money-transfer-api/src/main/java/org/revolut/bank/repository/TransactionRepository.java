package org.revolut.bank.repository;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import org.revolut.bank.dao.Account;
import org.revolut.bank.dao.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Transaction Repository Implementation
 * */
public class TransactionRepository implements ITransactionRepository {
	
	private Logger LOGGER = LoggerFactory.getLogger(TransactionRepository.class);

	private final Queue<Transaction> transactions = new LinkedList<Transaction>();
	private IAccountRepository accountRepository;

	public TransactionRepository(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Transaction createTransaction(Transaction transaction) throws Exception {
		LOGGER.info("Transation started.");
		Account fromAccount;
		Account toAccount;
		while (transaction.getInProcess().get()) {
			synchronized (this) {
				fromAccount = accountRepository.getAccountById(transaction.getFromAccountId());
				toAccount = accountRepository.getAccountById(transaction.getToAccountId());
			}
			if (fromAccount.getLock().tryLock()) { // try to acquire lock if available on sender object
				try {
					if (toAccount.getLock().tryLock()) {// try to acquire lock if available on receiver object
						try {
							accountRepository.withdrawMoney(fromAccount, transaction.getAmount());
							accountRepository.depositMoney(toAccount, transaction.getAmount());
							transactions.add(transaction);
							transaction.getInProcess().set(false); // Mark out of transaction
						} finally {
							toAccount.getLock().unlock(); // Release lock
						}
					}
				} finally {
					fromAccount.getLock().unlock(); // Release lock
					LOGGER.info("Transation completed.");
				}

			}
		}
		return transaction;
	}

	@Override
	public Optional<Transaction> getTransactionbyId(final String transactionId) {
		LOGGER.info("Get transaction by transaction id : "+transactionId);
		return transactions.stream().filter(transaction -> transaction.getTransactionId().equals(transactionId))
				.findFirst();
	}

	@Override
	public Queue<Transaction> getAllTransactions() {
		return transactions;
	}

}
