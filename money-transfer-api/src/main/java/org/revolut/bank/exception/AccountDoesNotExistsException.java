package org.revolut.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountDoesNotExistsException extends RuntimeException {

	private String accountId;

	@Override
	public String getMessage() {
		return String.format("Account with account number %s does not exists", accountId);

	}
}
