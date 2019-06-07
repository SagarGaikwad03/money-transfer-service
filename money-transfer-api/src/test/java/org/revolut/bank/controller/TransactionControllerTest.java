package org.revolut.bank.controller;

import static org.junit.Assert.assertEquals;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.revolut.bank.repository.AccountRepository;
import org.revolut.bank.repository.ITransactionRepository;
import org.revolut.bank.repository.TransactionRepository;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PostMethod;
import com.despegar.sparkjava.test.SparkServer;

import spark.servlet.SparkApplication;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest {

	public static class TransactionControllerTestSpark implements SparkApplication {
		@Override
		public void init() {
			ITransactionRepository transactionRepository = new TransactionRepository(new AccountRepository());
			new TransactionController(transactionRepository);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ClassRule
	public static SparkServer<TransactionControllerTestSpark> testServer = new SparkServer(
			TransactionControllerTest.TransactionControllerTestSpark.class, 4567);

	@Test
	public void testGeTransactiontService() throws Exception {
		GetMethod request = testServer.get("/transaction?transactionId=1", false);
		HttpResponse httpResponse = testServer.execute(request);
		assertEquals(200, httpResponse.code());
	}

	@Test
	public void testCreateTransactiontService() throws Exception {
		PostMethod request = testServer.post("/transfer",
				"{\"fromAccountId\":\"e447ac53-4765-4ac6-846a-6a1822b36238\",\"toAccountId\":\"f04b8a02-9551-48d0-b995-d0fe5fd49a49\",\"amount\":\"5000\"}\r\n"
						+ "",
				false);
		HttpResponse httpResponse = testServer.execute(request);
		assertEquals(200, httpResponse.code());
	}
}
