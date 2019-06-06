package org.revolut.bank.repository;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import org.revolut.bank.dao.Account;
import org.revolut.bank.dao.Transaction;

public class TransactionRepository implements ITransactionRepository {

	private final Queue<Transaction> transactions = new LinkedList<Transaction>();
	private IAccountRepository accountRepository;

	public TransactionRepository(IAccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public Transaction createTransaction(Transaction transaction) throws Exception {
		Account fromAccount;
		Account toAccount;
		while (transaction.getInProcess().get()) {
			synchronized (this) {

				fromAccount = accountRepository.getAccountById(transaction.getFromAccountId());
				toAccount = accountRepository.getAccountById(transaction.getToAccountId());
			}
			if (fromAccount.getLock().tryLock()) {
				try {
					if (toAccount.getLock().tryLock()) {
						try {
							accountRepository.withdrawMoney(fromAccount, transaction.getAmount());
							accountRepository.depositMoney(toAccount, transaction.getAmount());
							transactions.add(transaction);
							transaction.getInProcess().set(false);
							System.out.println("Transaction Created.");
						} finally {
							toAccount.getLock().unlock();
						}
					}
				} finally {
					System.out.println("Transaction Completed.");

					fromAccount.getLock().unlock();
				}

			}
		}
		return transaction;
	}

	@Override
	public Optional<Transaction> getTransactionbyId(final String transactionId) {
		return transactions.stream().filter(transaction -> transaction.getTransactionId().equals(transactionId))
				.findFirst();
	}

	@Override
	public Queue<Transaction> getAllTransactions() {
		return transactions;
	}

}
