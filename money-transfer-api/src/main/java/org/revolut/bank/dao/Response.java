package org.revolut.bank.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

	private final Object object;
	private final String message;
	private final int Status;

}
