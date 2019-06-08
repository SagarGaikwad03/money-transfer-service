package org.revolut.bank.exception;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Account Number Blank or Emoty Exception
 * */
public class AccountNumberBlankOrEmptyException extends RuntimeException {

	@Override
	public String getMessage() {
		return String.format("Account with account number is Blank or Empty");
	}

}
