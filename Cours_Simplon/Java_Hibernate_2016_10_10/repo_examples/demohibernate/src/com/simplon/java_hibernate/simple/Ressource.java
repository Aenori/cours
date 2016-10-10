package com.simplon.java_hibernate.simple;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ressources")
public class Ressource {
	
	@Id
	@GeneratedValue
	private Long id; // Identifiant formation
	
	@Column(name = "name")
	private String name; // Nom de la ressource
	
	@Column(name = "capacite")
	private Long capacite; // Capacite pour une salle (null pour un ordinateur)
	
	@Column(name = "est_salle")
	private boolean est_salle;
	
	@OneToMany(mappedBy="ressource")
	private Set<Reservation> reservations = new HashSet<Reservation>();
	
	
		
	// Constructeur
	public Ressource(String name, Long capacite, boolean est_salle) {
		
		this.name = name;
		this.capacite = capacite;
		this.est_salle = est_salle;
	}
	
	// Constructeur
	public Ressource(String name, int capacite, boolean est_salle) {
		this(name, new Long(capacite), est_salle);
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

	public Long getCapacite() {
		return capacite;
	}

	public void setCapacite(Long capacite) {
		this.capacite = capacite;
	}

	public boolean isEst_salle() {
		return est_salle;
	}

	public void setEst_salle(boolean est_salle) {
		this.est_salle = est_salle;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}	
}
