package com.paymybuddy.PayMyBuddy.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UsersRepository usersRepository;
	
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = usersRepository.getByMail(username);

		if(user == null) {
			user = usersRepository.getByPseudo(username);
			if(user == null) throw new UsernameNotFoundException(username);
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		
		return new org.springframework.security.core.userdetails.User(user.getPseudo(), user.getPassword(), grantedAuthorities);
	}
	
	public boolean transferToApplication(double money) {
		
		if(money <0)
			return false;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user= usersRepository.getByPseudo(authentication.getName());
		user.setCurrency(user.getCurrency()+money);
		usersRepository.save(user);
		
		return true;
	}
	
	public boolean transferToBank(double money) {
		if(money <0)
			return false;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user= usersRepository.getByPseudo(authentication.getName());
		
		if(user.getCurrency() < money)
			return false;
		
		user.setCurrency(user.getCurrency()-money);
		usersRepository.save(user);
		
		return true;
	}
	
}
