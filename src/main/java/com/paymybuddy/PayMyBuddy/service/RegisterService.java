package com.paymybuddy.PayMyBuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.PayMyBuddy.dto.UserDto;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.UsersRepository;

@Service
@Transactional
public class RegisterService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsersRepository usersRepository;
	
	public void register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCurrency(0);
	}

	public User registerUser(UserDto userDto) throws Exception {
		if (mailExist(userDto.getMail())) {
			throw new Exception("There is an account with that email address: " + userDto.getMail()); 
		}
		if (pseudoExist(userDto.getPseudo())) {
			throw new Exception("There is an account with that pseudo: " + userDto.getPseudo()); 
		}
		
		User user = new User();
		user.setPseudo(userDto.getPseudo());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setMail(userDto.getMail());
		user.setCurrency(0);

		return usersRepository.save(user);
	}
	
	private boolean mailExist(String mail) {
		return usersRepository.getByMail(mail) != null;
	}
	
	private boolean pseudoExist(String pseudo) {
		return usersRepository.getByPseudo(pseudo) != null;

	}
	
}
