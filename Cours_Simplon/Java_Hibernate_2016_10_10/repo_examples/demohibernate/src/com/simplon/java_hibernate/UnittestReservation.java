package com.simplon.java_hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UnittestReservation {
	protected static Session session;
	protected static Transaction transaction;
	
	public static Session getSession() {
		return session;
	}

	public static void setSession(Session session) {
		UnittestReservation.session = session;
	}

	public static Transaction getTransaction() {
		return transaction;
	}

	public static void setTransaction(Transaction transaction) {
		UnittestReservation.transaction = transaction;
	}

	// Fonction 1
	// Crée une salle dont le nom est "nom", et renvoit son Id
	public static Long CreerUtilisateur(String nom) {
		throw new UnsupportedOperationException();
	}
	
	// Fonction 2
	// Crée une salle dont le nom est "nom", et renvoit son Id
	public static Long CreerSalle(String nom) {
		throw new UnsupportedOperationException();
	}
	
	// Fonction 3
	// Crée un ordinateur dont le nom est "name", et renvoit son Id
	public static Long CreerOrdinateur(String nom) {
		throw new UnsupportedOperationException();
	}
		
	// Fonction 4
	// Renvoit le nom d'une salle à partir de son Id.
	// Renvoit null si il n'existe pas de salle de cet id (ce qui inclue le 
	// cas où l'id correspond à un ordinateur)
	public static String GetNomDeLaSalle(Long id) {
		throw new UnsupportedOperationException();
	}

	// Fonction 5
	// Renvoit le nom d'une ordinateur à partir de son Id.
	// Renvoit null si il n'existe pas d'ordinateur de cet id (ce qui inclue le 
	// cas où l'id correspond à une salle)
	public static String GetNomDeLOrdinateur(Long id) {
		throw new UnsupportedOperationException();
	}
	
	// Fonction 6
	// Créé  une réservation et renvoit son id.  
	// Renvoit null si la création de réservation n'est pas
	// possible, entre autre :
	//   - parce la ressource ressource_id n'existe pas
	//   - parce que l'utilisateur user_id n'existe pas
	//   - parce que la date de début est passée
	//   - parce que la date de fin est antérieur à la date
	//		de début
	// Cette fonction ne vérifie pas nécessairement que
	// la ressource est disponible
	public static Long CreerReservation(
			Long ressource_id, 
			Long user_id, 
			LocalDateTime dateDebut, 
			LocalDateTime dateFin )
	{
		throw new UnsupportedOperationException();
	}
	
	// Fonction 7
	// Renvoit la liste des ids des réservations pour un utilisateur donné
	public static List<Long> GetListeDeReservationPourUtilisateur(Long user_id)
	{
		throw new UnsupportedOperationException();
	}
	
	// Fonction 8
	// Renvoit la liste des ids des réservations pour un utilisateur donné,
	// qui recouvre la plage indiqué par début et fin.
	// Par exemple si la plage est du 10/10 à 10h au 10/10 à 12h, l'id d'une
	// réservation qui irait du 10/10 à 11h30 au 10/10 à 13h serait renvoyé
	// mais pas celui d'une réservation allant du 10/10 12h au 10/10 14h
	public static List<Long> GetListeDeReservationPourUtilisateur(Long user_id, LocalDateTime debut, LocalDateTime fin)
	{
		throw new UnsupportedOperationException();
	}
	
	// Fonction 9
	// Même chose, mais pour une ressource donnée
	public static List<Long> GetListeDeReservationPourRessource(Long ressource_id)
	{
		throw new UnsupportedOperationException();
	}
	
	// Fonction 10
	// Même chose, mais pour une ressource donnée
	public static List<Long> GetListeDeReservationPourRessource(Long ressource_id, LocalDateTime debut, LocalDateTime fin)
	{
		throw new UnsupportedOperationException();
	}
	
	// Fonction 11
	// Pour un utilisateur donnée, renvoit la liste (sans duplication) des ressources réservées dans une ou plusieur
	// réservations, quelque soit la date  
	public static Set<Long> GetListeDeRessourcesReservees(Long user_id)
	{
		throw new UnsupportedOperationException();
	}
		
	// Fonction 12
	// Pour un utilisateur donné et une ressource donnée, supprime l'ensemble des réservations 
	// recouvrant (et non pas strictement comprise dans) une plage donnée.
	// Renvoit la liste des id des réservations supprimées.
	// Renvoit une liste vide si aucune réservation n'a été supprimée.
	// (y compris si l'utilisateur ou la ressource n'existe pas)
	public static List<Long> SupprimerReservations(Long user_id, Long ressource_id, LocalDateTime debut, LocalDateTime fin)
	{
		throw new UnsupportedOperationException();
	}
	
	// Fonction 13
	// Cherche, pour une plage donnée, l'ensemble des ressources disponibles
	//  - de type salle si salleSinonOrdinateur vaut True
	//  - de type ordinateur sinon
	public static List<Long> TrouverRessourceDisponible(LocalDateTime debut, LocalDateTime fin, boolean salleSinonOrdinateur)
	{
		throw new UnsupportedOperationException();
	}

	// Fonction 14
	// Pour un ensemble de ressources donné, trouve le premier créneau, compris 
	// entre 9h et 17h - nb_hours disponible, à partir de dateDebut, où toutes les 
	// ressources sont disponibles
	public static LocalDateTime TrouverCreneauDisponible(List<Long> ressource_id_list, LocalDateTime dateDebut, int nb_hours)
	{
		throw new UnsupportedOperationException();
	}
	
	// Fonction 15
	// Créé  une réservation en verifiant que la ressource est disponible
	//   et que les dates sont dans le futur. Renvoit null si la reservation
	//   n'est pas possible
	public static Long CreerReservationVerifiee(
			Long ressource_id, 
			Long user_id, 
			LocalDateTime dateDebut, 
			LocalDateTime dateFin )
	{
		throw new UnsupportedOperationException();
	}
}
