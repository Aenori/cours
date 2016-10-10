package com.simplon.java_hibernate.simple;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "creneaux_horaires")
public class CreneauHoraire {

	@Id
	@GeneratedValue
	private Long id; // Identifiant formation
	
	@Column(name="debut")
	private LocalDateTime debut;
	
	@ManyToMany
	@JoinTable(name = "reservations_creneaux", joinColumns = {
			@JoinColumn(name = "creneaux_horaires_id", referencedColumnName="id", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "reservations_id", referencedColumnName="id",
					nullable = false, updatable = false) })
	private Set<Reservation> reservations = new HashSet<Reservation>();
	
	public CreneauHoraire() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDebut() {
		return debut;
	}

	public void setDebut(LocalDateTime debut) {
		this.debut = debut;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

}
