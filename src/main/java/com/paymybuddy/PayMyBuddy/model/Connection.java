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
@Table(name = "connections")
public class Connection {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "id")
	private User userId;
	
    @ManyToOne()
    @JoinColumn(name = "connectionId", referencedColumnName = "id")
	private User connectionId;

}
