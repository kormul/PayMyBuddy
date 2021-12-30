package com.paymybuddy.PayMyBuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHistoryDTO {

	private String connections;
	
	private String description;
	
	private double amount;
}
