package org.revolut.bank.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.revolut.bank.dao.Account;
import org.revolut.bank.dao.Transaction;
import org.revolut.bank.exception.AccountDoesNotExistsException;
import org.revolut.bank.exception.AccountLowBalanceException;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Transaction Repository Test
 * */
public class TransactionRepositoryTest {

	private ITransactionRepository transactionRepository;

	private IAccountRepository accountRepository;

	@Before
	public void setUp() {
		accountRepository = new AccountRepository();
		transactionRepository = new TransactionRepository(accountRepository);
	}

	@Test(expected = AccountDoesNotExistsException.class)
	public void testInvalidTransaction() throws Exception {
		Account fromAccount = createFromAccount();
		Account toAccount = createToAccount();
		Transaction transaction = createTransaction(fromAccount.getAccountId(), toAccount.getAccountId(),
				new BigDecimal("500"));

		Transaction newTransaction = transactionRepository.createTransaction(transaction);

		assertEquals(transaction, newTransaction);
	}

	@Test(expected = AccountLowBalanceException.class)
	public void testTransactionWithLowBalance() throws Exception {
		Account fromAccount = createFromAccount();
		Account toAccount = createToAccount();

		accountRepository.createAccount(fromAccount);
		accountRepository.createAccount(toAccount);

		Transaction transaction = createTransaction(fromAccount.getAccountId(), toAccount.getAccountId(),
				new BigDecimal("5000"));
		Transaction newTransaction = transactionRepository.createTransaction(transaction);

		assertEquals(transaction, newTransaction);
	}

	@Test
	public void testValidTransaction() throws Exception {
		Account fromAccount = createFromAccount();
		Account toAccount = createToAccount();

		accountRepository.createAccount(fromAccount);
		accountRepository.createAccount(toAccount);

		Transaction transaction = createTransaction(fromAccount.getAccountId(), toAccount.getAccountId(),
				new BigDecimal("500"));
		Transaction newTransaction = transactionRepository.createTransaction(transaction);

		assertEquals(transaction, newTransaction);
	}

	@Test
	public void testGetTransaction() throws Exception {
		Account fromAccount = createFromAccount();
		Account toAccount = createToAccount();

		accountRepository.createAccount(fromAccount);
		accountRepository.createAccount(toAccount);

		Transaction t1 = createTransaction(fromAccount.getAccountId(), toAccount.getAccountId(), new BigDecimal("500"));
		Transaction t2 = transactionRepository.createTransaction(t1);
		Optional<Transaction> t3 = transactionRepository.getTransactionbyId(t2.getTransactionId());

		assertTrue(t3.isPresent());
	}

	@Test
	public void testGetInvalidTransaction() throws Exception {
		Optional<Transaction> t3 = transactionRepository.getTransactionbyId("invalidTransactionId");

		assertFalse(t3.isPresent());
	}

	@Test
	public void testGetAllTransaction() throws Exception {
		Account fromAccount = createFromAccount();
		Account toAccount = createToAccount();

		accountRepository.createAccount(fromAccount);
		accountRepository.createAccount(toAccount);

		Transaction t1 = createTransaction(fromAccount.getAccountId(), toAccount.getAccountId(), new BigDecimal("500"));
		Transaction t2 = transactionRepository.createTransaction(t1);
		Transaction t3 = transactionRepository.createTransaction(t1);

		transactionRepository.getTransactionbyId(t2.getTransactionId());
		transactionRepository.getTransactionbyId(t3.getTransactionId());

		Queue<Transaction> queue = transactionRepository.getAllTransactions();

		assertFalse(queue.isEmpty());
	}

	@Test
	public void testGetAllInvalidTransaction() throws Exception {
		Queue<Transaction> queue = transactionRepository.getAllTransactions();

		assertTrue(queue.isEmpty());
	}

	private Transaction createTransaction(String fromAccountId, String toAccountId, BigDecimal amount) {
		return Transaction.builder().transactionId(UUID.randomUUID().toString()).fromAccountId(fromAccountId)
				.toAccountId(toAccountId).amount(amount).build();
	}

	private Account createFromAccount() {
		return Account.builder().accountId(UUID.randomUUID().toString()).userName("from account")
				.balance(new BigDecimal("1000")).currency("INR").createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now()).build();
	}

	private Account createToAccount() {
		return Account.builder().accountId(UUID.randomUUID().toString()).userName("to account")
				.balance(new BigDecimal("1000")).currency("INR").createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now()).build();
	}
}
