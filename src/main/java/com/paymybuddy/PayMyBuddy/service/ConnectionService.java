package com.paymybuddy.PayMyBuddy.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paymybuddy.PayMyBuddy.dto.ConnectionDTO;
import com.paymybuddy.PayMyBuddy.model.Connection;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.ConnectionsRepository;
import com.paymybuddy.PayMyBuddy.repository.UsersRepository;

@Service
public class ConnectionService {
	
	@Autowired
	ConnectionsRepository connectionsRepository;
	
	@Autowired
	UsersRepository usersRepository;
	
	public boolean addConnection(String userName) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User connectionUser = usersRepository.getByPseudo(userName);
		User user = usersRepository.getByPseudo(authentication.getName());
		if(connectionUser == null)
			return false;
		if(connectionUser.getPseudo().equalsIgnoreCase(user.getPseudo()))
			return false;
		if(connectionsRepository.getByUserIdAndConnectionId(user.getId(), connectionUser.getId()) != null) {
			return false;
		}
		Connection connection = new Connection();
		connection.setUserId(user.getId());
		connection.setConnectionId(connectionUser.getId());
		connectionsRepository.save(connection);
		return true;
	}

	public List<ConnectionDTO> getConnection() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = usersRepository.getByPseudo(authentication.getName());
		List<Connection> connections =  connectionsRepository.findByUserId(user.getId());
		List<ConnectionDTO> connectionDTOs = new ArrayList<>();
		for(Connection connection : connections){
			connectionDTOs.add(new ConnectionDTO(connection.getId(), usersRepository.getById(connection.getConnectionId()).getPseudo(), usersRepository.getById(connection.getConnectionId()).getId()));
		}
		return connectionDTOs;
	}

}
