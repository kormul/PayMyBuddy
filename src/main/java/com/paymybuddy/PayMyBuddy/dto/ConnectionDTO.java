package com.paymybuddy.PayMyBuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ConnectionDTO {
	
	private int id;
	
	@NonNull
	private String pseudo;

	private int idUser;
}
