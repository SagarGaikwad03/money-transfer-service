package org.revolut.bank.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
	
	private final String accountId;
    private final String userName;
    private BigDecimal balance;
    private final String currency;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Lock lock = new ReentrantLock();

}
