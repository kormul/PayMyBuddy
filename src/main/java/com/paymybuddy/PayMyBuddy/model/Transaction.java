package com.paymybuddy.PayMyBuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    
    @ManyToOne()
    @JoinColumn(name = "transmitter", referencedColumnName = "id")
	private User transmitter;
    
    @ManyToOne()
    @JoinColumn(name = "receiver", referencedColumnName = "id")
	private User receiver;
    
    @Column(name="fee")
	private double fee;
}
