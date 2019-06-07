package org.revolut.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountAlreadyExistsException extends RuntimeException {
	
	private String accountNumber;
	
	@Override
	public String getMessage() {
		return String.format("Account with account number %s already exists", getAccountNumber());
	}
	

}
