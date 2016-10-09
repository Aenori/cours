package com.simplon.demohibernate.simple;

public class Reservation {
	
	private Long id;
	
	private String name; // Thème formation
	
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

	public Reservation() {
	}
	
	// Constructeur
	public Reservation(String name) {
		super();
		this.name = name;
	}	
}
