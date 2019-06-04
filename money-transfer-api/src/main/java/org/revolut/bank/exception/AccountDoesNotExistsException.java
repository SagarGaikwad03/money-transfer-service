package org.revolut.bank.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountDoesNotExistsException extends RuntimeException{

	private String accountId;
	
	@Override
	public String getMessage() {
		return String.format("Account with account id s% does not exists", accountId);
	}
}
