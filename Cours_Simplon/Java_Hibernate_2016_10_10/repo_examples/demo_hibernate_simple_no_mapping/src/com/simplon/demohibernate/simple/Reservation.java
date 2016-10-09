package com.simplon.demohibernate.simple;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name")
	private String name; // Thème formation
	
	public Reservation() {
	}
	
	// Constructeur
	public Reservation(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
