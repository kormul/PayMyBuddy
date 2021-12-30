package com.paymybuddy.PayMyBuddy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.paymybuddy.PayMyBuddy.model.Transaction;

public interface TransactionsRepository extends CrudRepository<Transaction, Integer>{
	
	List<Transaction> findByTransmitter(int id);

}
