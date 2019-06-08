package org.revolut.bank.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Account Already Exists Exception
 * */
@AllArgsConstructor
@Getter
public class AccountAlreadyExistsException extends RuntimeException {
	
	private String accountNumber;
	
	@Override
	public String getMessage() {
		return String.format("Account with account number %s already exists", getAccountNumber());
	}
	

}
