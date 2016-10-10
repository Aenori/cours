package com.simplon.java_hibernate.simple;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {

	@Id
	@GeneratedValue
	private Long id; // Identifiant formation
	
	@Column(name = "user_id")
	private Long user_id; 
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="ressource_id")
	private Ressource ressource; // Capacite pour une salle (null pour un ordinateur)
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="reservations")
	private Set<CreneauHoraire> creneaux_horaires = new HashSet<CreneauHoraire>();
	
	public Reservation() {
		// TODO Auto-generated constructor stub
	}

	public Reservation(int user_id, Ressource ressource) {
		this( new Long(user_id), ressource );
	}
	
	public Reservation(Long user_id, Ressource ressource) {
		this.user_id = user_id;
		this.ressource = ressource;
		if( ressource != null )
		{
			ressource.getReservations().add(this);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Ressource getRessource() {
		return ressource;
	}

	public void setRessource(Ressource ressource) {
		this.ressource = ressource;
	}
}
