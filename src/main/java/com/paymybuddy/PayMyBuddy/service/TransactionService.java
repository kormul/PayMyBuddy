package com.paymybuddy.PayMyBuddy.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.paymybuddy.PayMyBuddy.dto.TransactionHistoryDTO;
import com.paymybuddy.PayMyBuddy.model.Transaction;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.TransactionsRepository;
import com.paymybuddy.PayMyBuddy.repository.UsersRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionsRepository transactionsRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	public boolean addTransaction(double amount, String description, int receiver) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User userTransmitter = usersRepository.getByPseudo(authentication.getName());
		User userReceiver = usersRepository.getById(receiver);
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription(description);
		transaction.setTransmitter(userTransmitter.getId());
		transaction.setReceiver(userReceiver.getId());
		transaction.setFee(0.5);
		userTransmitter.setCurrency(userTransmitter.getCurrency()-amount);
		userReceiver.setCurrency(userReceiver.getCurrency()+(amount*0.95));
		usersRepository.save(userTransmitter);
		usersRepository.save(userReceiver);
		transactionsRepository.save(transaction);

		return true;
	}
	
	public List<TransactionHistoryDTO> getPageTransaction(int page) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = usersRepository.getByPseudo(authentication.getName());
		List<TransactionHistoryDTO> historyResult = new ArrayList<>();
		List<Transaction> allTransaction = transactionsRepository.findByTransmitter(user.getId());
		Collections.reverse(allTransaction);
		int id = (page-1)*3;
		for(int i = 0; i<3;i++) {
			if(id+i<allTransaction.size()) {
				historyResult.add(new TransactionHistoryDTO(usersRepository.getById(allTransaction.get(id+i).getReceiver()).getPseudo(), allTransaction.get(id+i).getDescription(), allTransaction.get(id+i).getAmount()));
			}
			else {
				break;
			}
		}
		return historyResult;
		
	}

	public int getAllPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = usersRepository.getByPseudo(authentication.getName());
		List<Transaction> allTransaction = transactionsRepository.findByTransmitter(user.getId());
		int number = allTransaction.size()/3;
		if(allTransaction.size() %3>0) number++;
		return number;
	}
	
}
