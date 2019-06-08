package org.revolut.bank.exception;

import lombok.AllArgsConstructor;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Account Low Balance Exception
 * */

@AllArgsConstructor
public class AccountLowBalanceException extends RuntimeException{

	private String accountId;

	@Override
	public String getMessage() {
		return String.format("Account with account number %s does not have enough balance", accountId);	
	}
}
