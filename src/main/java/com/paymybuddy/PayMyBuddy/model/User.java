package com.paymybuddy.PayMyBuddy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @Column(name="mail")
	private String mail;
	
    @Column(name="pseudo")
	private String pseudo;
	
    @Column(name="password")
	private String password;
    
    @Column(name="currency")
	private double currency;
    
    @OneToMany()
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private List<Connection> connections;
    
    @OneToMany()
    @JoinColumn(name = "transmitter", referencedColumnName = "id")
    private List<Transaction> transactions;
}
