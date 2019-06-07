package org.revolut.bank.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountLowBalanceException extends RuntimeException{

	private String accountId;

	@Override
	public String getMessage() {
		return String.format("Account with account number %s does not have enough balance", accountId);	
	}
}
