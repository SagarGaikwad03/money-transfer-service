package org.revolut.bank.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.util.StringUtil;
import org.revolut.bank.dao.Account;
import org.revolut.bank.exception.AccountAlreadyExistsException;
import org.revolut.bank.exception.AccountDoesNotExistsException;
import org.revolut.bank.exception.AccountLowBalanceException;
import org.revolut.bank.exception.AccountNumberBlankOrEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Account Repository Implementation
 * */
public class AccountRepository implements IAccountRepository {
	
	private Logger LOGGER = LoggerFactory.getLogger(AccountRepository.class);


	private final Map<String, Account> accounts = new ConcurrentHashMap<String, Account>();

	@Override
	public Account createAccount(Account account) throws AccountDoesNotExistsException {
		LOGGER.info("Create account");
		if (accounts.containsKey(account.getAccountId()))
			throw new AccountAlreadyExistsException(account.getAccountId());
		accounts.put(account.getAccountId(), account);
		return account;
	}

	@Override
	public Account updateAccount(Account account) throws AccountDoesNotExistsException {
		LOGGER.info("Update account");
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
		LOGGER.info("Withdraw transaction");
		if (!accounts.containsKey(account.getAccountId()))
			throw new AccountDoesNotExistsException(account.getAccountId());
		if (account.getBalance().compareTo(amount) < 0) // Check if account have enough balance
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
		LOGGER.info("Deposit transaction");
		if (!accounts.containsKey(account.getAccountId()))
			throw new AccountDoesNotExistsException(account.getAccountId());

		final Account updatedAccount = Account.builder().accountId(account.getAccountId())
				.userName(account.getUserName()).balance(account.getBalance().add(amount))
				.currency(account.getCurrency()).createdAt(account.getCreatedAt()).updatedAt(LocalDateTime.now())
				.build();
		accounts.put(account.getAccountId(), updatedAccount);
		return updatedAccount;
	}
	
	@Override
	public void delete(String accountId) throws Exception {
		LOGGER.info("Delete account");
		if(StringUtil.isBlank(accountId))
			throw new AccountNumberBlankOrEmptyException();
		
		if (!accounts.containsKey(accountId))
			throw new AccountDoesNotExistsException(accountId);

		accounts.remove(accountId);
	}

}
