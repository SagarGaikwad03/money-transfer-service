package org.revolut.bank.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.revolut.bank.dao.Account;
import org.revolut.bank.exception.AccountDoesNotExistsException;
import org.revolut.bank.exception.AccountLowBalanceException;

public class AccountRepositoryTest {

	private IAccountRepository accountRepository;

	@Before
	public void setUp() {
		accountRepository = new AccountRepository();
	}

	@Test
	public void testCreateAccount() throws Exception {
		Account account = createAccount();
		Account newAccount = accountRepository.createAccount(account);
		assertEquals(account, newAccount);
	}

	@Test
	public void testAccountById() throws Exception {
		Account account = createAccount();
		accountRepository.createAccount(account);
		Account newAccount = accountRepository.getAccountById(account.getAccountId());
		assertEquals(account, newAccount);
	}

	@Test(expected = AccountDoesNotExistsException.class)
	public void testAccountByInvalidId() throws Exception {
		accountRepository.getAccountById("invalidId");
	}

	@Test
	public void testAccountDepositMoney() throws Exception {
		Account account = createAccount();
		BigDecimal amount = new BigDecimal("500");
		accountRepository.createAccount(account);
		Account updatedAccount = accountRepository.depositMoney(account, amount);
		assertEquals(account.getBalance().add(amount), updatedAccount.getBalance());
	}

	@Test
	public void testAccountWithdrawMoney() throws Exception {
		Account account = createAccount();
		BigDecimal amount = new BigDecimal("500");
		accountRepository.createAccount(account);
		Account updatedAccount = accountRepository.withdrawMoney(account, amount);
		assertEquals(account.getBalance().subtract(amount), updatedAccount.getBalance());

	}

	@Test(expected = AccountLowBalanceException.class)
	public void testAccountWithdrawMoneyLowBalance() throws Exception {
		Account account = createAccount();
		BigDecimal amount = new BigDecimal("5000");
		accountRepository.createAccount(account);
		accountRepository.withdrawMoney(account, amount);
	}

	private Account createAccount() {
		return Account.builder().accountId(UUID.randomUUID().toString()).userName("test account")
				.balance(new BigDecimal("1000")).currency("INR").createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now()).build();
	}

}
