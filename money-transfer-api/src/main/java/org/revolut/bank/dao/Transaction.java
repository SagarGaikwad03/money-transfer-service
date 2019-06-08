package org.revolut.bank.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.Builder;
import lombok.Data;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Transaction Model
 * */
@Data
@Builder
public class Transaction {

    private final String transactionId;
    private final String fromAccountId;
    private final String toAccountId;
    private BigDecimal amount;
    private final LocalDateTime createdAt;
	private final AtomicBoolean inProcess = new AtomicBoolean(true);

}
