package com.paymybuddy.PayMyBuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @Column(name="amount")
	private double amount;
	
    @Column(name="description")
	private String description;
    
    @Column(name="transmitter")
	private int transmitter;
    
    @Column(name="receiver")
	private int receiver;
    
    @Column(name="fee")
	private double fee;
}
