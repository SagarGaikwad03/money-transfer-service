package org.revolut.bank.dao;

import lombok.Builder;
import lombok.Data;

/*
 * @Author : Sagar Gaikwad
 * @Created On 8/06/2019
 * 
 * Response Model
 * */
@Data
@Builder
public class Response {

	private final Object object;
	private final String message;
	private final int Status;

}
