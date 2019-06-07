package org.revolut.bank.controller;

import static org.junit.Assert.assertEquals;

import org.junit.ClassRule;
import org.junit.Test;
import org.revolut.bank.repository.AccountRepository;
import org.revolut.bank.repository.IAccountRepository;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PostMethod;
import com.despegar.http.client.PutMethod;
import com.despegar.sparkjava.test.SparkServer;

import spark.servlet.SparkApplication;

public class AccountControllerTest {

	public static class AccountControllerTestSpark implements SparkApplication {
		@Override
		public void init() {
			IAccountRepository accountRepository = new AccountRepository();
			new AccountController(accountRepository);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ClassRule
	public static SparkServer<AccountControllerTestSpark> testServer = new SparkServer(
			AccountControllerTest.AccountControllerTestSpark.class, 4567);

	@Test
	public void testAccountGetService() throws Exception {
		GetMethod request = testServer.get("/account?accountId=1", false);
		HttpResponse httpResponse = testServer.execute(request);
		assertEquals(200, httpResponse.code());
	}

	@Test
	public void testAccountCreateService() throws Exception {
		PostMethod request = testServer.post("/account",
				"{\"userName\":\"Test Account\",\"balance\":1000,\"currency\":\"INR\"}\r\n" + "", false);
		HttpResponse httpResponse = testServer.execute(request);
		assertEquals(200, httpResponse.code());
	}

	@Test
	public void testAccountDepositService() throws Exception {
		PutMethod request = testServer.put("/account?accountId=1&transaction=Deposit&amount=500", "", false);
		HttpResponse httpResponse = testServer.execute(request);
		assertEquals(200, httpResponse.code());
	}

	@Test
	public void testAccountWithdrawService() throws Exception {
		PutMethod request = testServer.put("/account?accountId=1&transaction=Withdraw&amount=500", "", false);

		HttpResponse httpResponse = testServer.execute(request);
		assertEquals(200, httpResponse.code());
	}

}
