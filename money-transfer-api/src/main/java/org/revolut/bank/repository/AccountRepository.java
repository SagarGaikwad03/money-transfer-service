package org.revolut.bank.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.revolut.bank.dao.Account;
import org.revolut.bank.exception.AccountAlreadyExistsException;
import org.revolut.bank.exception.AccountDoesNotExistsException;
import org.revolut.bank.exception.AccountLowBalanceException;

public class AccountRepository implements IAccountRepository {

	private final Map<String, Account> accounts = new HashMap<String, Account>();

	@Override
	public Account createAccount(Account account) throws AccountDoesNotExistsException {
		if (accounts.containsKey(account.getAccountId()))
			throw new AccountAlreadyExistsException(account.getAccountId());
		accounts.put(account.getAccountId(), account);
		return account;
	}

	@Override
	public Account updateAccount(Account account) throws AccountDoesNotExistsException {
		if (!accounts.containsKey(account.getAccountId()))
			throw new AccountDoesNotExistsException(account.getAccountId());

		final Account updatedAccount = Account.builder().accountId(account.getAccountId())
				.userName(account.getUserName()).balance(account.getBalance()).currency(account.getCurrency())
				.createdAt(account.getCreatedAt()).updatedAt(LocalDateTime.now()).build();
		accounts.put(account.getAccountId(), updatedAccount);
		return account;
	}

	@Override
	public Account getAccountById(String accountId) throws AccountDoesNotExistsException {
		if (!accounts.containsKey(accountId))
			throw new AccountDoesNotExistsException(accountId);
		return accounts.get(accountId);
	}

	@Override
	public Account withdrawMoney(Account account, BigDecimal amount) throws Exception {
		if (!accounts.containsKey(account.getAccountId()))
			throw new AccountDoesNotExistsException(account.getAccountId());
		if (account.getBalance().compareTo(amount) < 0)
			throw new AccountLowBalanceException(account.getAccountId());

		final Account updatedAccount = Account.builder().accountId(account.getAccountId())
				.userName(account.getUserName()).balance(account.getBalance().subtract(amount))
				.currency(account.getCurrency()).createdAt(account.getCreatedAt()).updatedAt(LocalDateTime.now())
				.build();
		accounts.put(account.getAccountId(), updatedAccount);
		return updatedAccount;
	}

	@Override
	public Account depositMoney(Account account, BigDecimal amount) throws Exception {
		if (!accounts.containsKey(account.getAccountId()))
			throw new AccountDoesNotExistsException(account.getAccountId());

		final Account updatedAccount = Account.builder().accountId(account.getAccountId())
				.userName(account.getUserName()).balance(account.getBalance().add(amount))
				.currency(account.getCurrency()).createdAt(account.getCreatedAt()).updatedAt(LocalDateTime.now())
				.build();
		accounts.put(account.getAccountId(), updatedAccount);
		return updatedAccount;
	}

}
