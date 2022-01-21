package com.paymybuddy.PayMyBuddy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
		for(Connection connection : user.getConnections()) {
			if(connection.getConnectionId().getId() == connectionUser.getId()) {
				return false;
			}
		}
		Connection connection = new Connection();
		connection.setUserId(user);
		connection.setConnectionId(connectionUser);
		connectionsRepository.save(connection);
		return true;
	}

	public List<ConnectionDTO> getConnection() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = usersRepository.getByPseudo(authentication.getName());
		List<Connection> connections = user.getConnections();
		List<ConnectionDTO> connectionDTOs = new ArrayList<>();
		for(Connection connection : connections){
			connectionDTOs.add(new ConnectionDTO(connection.getId(), connection.getConnectionId().getPseudo(), connection.getConnectionId().getId()));
		}
		return connectionDTOs;
	}

}
