package org.revolut.bank.repository;

import java.math.BigDecimal;

import org.revolut.bank.dao.Account;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Account Repository Interface
 * */
public interface IAccountRepository {

	Account createAccount(Account account) throws Exception;

	Account updateAccount(Account account) throws Exception;

	Account getAccountById(String accountId) throws Exception;

	Account withdrawMoney(Account account, BigDecimal amount) throws Exception;

	Account depositMoney(Account account, BigDecimal amount) throws Exception;
	
	void delete(String accountId) throws Exception;

}
