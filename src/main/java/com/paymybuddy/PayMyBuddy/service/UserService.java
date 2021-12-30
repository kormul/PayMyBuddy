package com.paymybuddy.PayMyBuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.UsersRepository;

@Service
public class UserService {

	@Autowired
	private UsersRepository usersRepository;
	
	public User getUserAuthen() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return usersRepository.getByPseudo(authentication.getName());
	}
	
	public boolean addMoney(double Currency) {
		User user = this.getUserAuthen();
		user.setCurrency(user.getCurrency()+Currency);
		usersRepository.save(user);
		return true;
	}

	public boolean takeMoney(double Currency) {
		User user = this.getUserAuthen();
		user.setCurrency(user.getCurrency()-Currency);
		usersRepository.save(user);
		return true;
	}
}
