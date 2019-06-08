package org.revolut.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Account Does Not Exists Exception
 * */
@AllArgsConstructor
@Getter
public class AccountDoesNotExistsException extends RuntimeException {

	private String accountId;

	@Override
	public String getMessage() {
		return String.format("Account with account number %s does not exists", accountId);

	}
}
