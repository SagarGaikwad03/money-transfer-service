package org.revolut.bank.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.revolut.bank.dao.Account;
import org.revolut.bank.dao.Transaction;

import net.jodah.concurrentunit.Waiter;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Transaction Concurrency Test
 * */
public class ConcurrentTransactionRepositoryTest {

	private ITransactionRepository transactionRepository;

	private IAccountRepository accountRepository;

	ExecutorService executorService;
	Waiter waiter;

	@Before
	public void setUp() {
		accountRepository = new AccountRepository();
		transactionRepository = new TransactionRepository(accountRepository);

		waiter = new Waiter();

		executorService = Executors.newFixedThreadPool(100);

	}

	@After
	public void tearDown() {
		executorService.shutdown();
	}

	@Test
	public void testConcurrentTransaction() throws Throwable {

		Account fromAccount = createFromAccount();
		Account toAccount = createToAccount();

		accountRepository.createAccount(fromAccount);
		accountRepository.createAccount(toAccount);

		/*Transaction t1 = createTransaction(fromAccount.getAccountId(), toAccount.getAccountId(), new BigDecimal("50"));
		Transaction t2 = createTransaction(fromAccount.getAccountId(), toAccount.getAccountId(), new BigDecimal("50"));
		Transaction t3 = createTransaction(fromAccount.getAccountId(), toAccount.getAccountId(), new BigDecimal("50"));
		*/for(int i =0 ; i<100; i++) {
			executorService.submit(() -> processTransactions(createTransaction(fromAccount.getAccountId(), toAccount.getAccountId(), new BigDecimal("10"))));

		}

		/*executorService.submit(() -> processTransactions(t1));
		executorService.submit(() -> processTransactions(t2));
		executorService.submit(() -> processTransactions(t3));
*/
		waiter.await(3, TimeUnit.SECONDS, 100);

		Account updatedFromAcccount = accountRepository.getAccountById(fromAccount.getAccountId());
		Account updatedToAccount = accountRepository.getAccountById(toAccount.getAccountId());

		assertEquals(fromAccount.getBalance().subtract(new BigDecimal("1000")), updatedFromAcccount.getBalance());
		assertEquals(toAccount.getBalance().add(new BigDecimal("1000")), updatedToAccount.getBalance());
	}

	private void processTransactions(Transaction transaction) {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(3000));
			waiter.assertNotNull(transaction);

			transactionRepository.createTransaction(transaction);
			waiter.resume();

		} catch (Exception e) {
			e.getMessage();
		}

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
