package com.paymybuddy.PayMyBuddy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.paymybuddy.PayMyBuddy.model.User;


public interface UsersRepository extends CrudRepository<User, Integer>{

	User getByMail(String mail);
	User getByPseudo(String pseudo);
	User getById(int id);
	List<User> findByMail(String mail);
	List<User> findByPseudo(String pseudo);
	
}
