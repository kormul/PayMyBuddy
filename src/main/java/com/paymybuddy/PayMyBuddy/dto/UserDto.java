package com.paymybuddy.PayMyBuddy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UserDto {
	
	@NonNull
	private String pseudo;
	
	@NonNull
	private String mail;
	
	@NonNull
	private String password;
	private String matchingPassword;

}
