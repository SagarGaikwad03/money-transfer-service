package org.revolut.bank;

import org.revolut.bank.controller.AccountController;
import org.revolut.bank.controller.TransactionController;
import org.revolut.bank.repository.AccountRepository;
import org.revolut.bank.repository.IAccountRepository;
import org.revolut.bank.repository.TransactionRepository;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Main class
 * */
public class MoneyTransferApp {

	public static void main(String argc[]) {
		IAccountRepository accountRepository = new AccountRepository();
		new AccountController(accountRepository);
		new TransactionController(new TransactionRepository(accountRepository));
	}
}
