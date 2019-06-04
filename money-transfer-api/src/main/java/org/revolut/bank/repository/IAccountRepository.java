package org.revolut.bank.repository;

import java.math.BigDecimal;

import org.revolut.bank.dao.Account;

public interface IAccountRepository {
	
	Account createAccount(Account account) throws Exception;
	Account updateAccount(Account account) throws Exception;
	Account getAccountById(String accountId) throws Exception;
	void withdrawMoney(Account account, BigDecimal amount) throws Exception;
	void depositMoney(Account account, BigDecimal amount) throws Exception;
}
