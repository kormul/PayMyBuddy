package com.paymybuddy.PayMyBuddy.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.paymybuddy.PayMyBuddy.model.Connection;

public interface ConnectionsRepository extends CrudRepository<Connection, Integer>{

	
	Connection getByUserIdAndConnectionId(int id, int connectionId);
	List<Connection> findByUserId(int id);
}
