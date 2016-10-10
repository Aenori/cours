package com.simplon.java_hibernate.unittest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import org.junit.Before;
import org.junit.Test;


import com.simplon.java_hibernate.UnittestReservation;
import com.simplon.java_hibernate.simple.Ressource;

import junit.framework.TestCase;


public class JavaSimplonFullUnitTest extends TestUnitInMemorySession {
	@Override
	public void setUp() {
		sessionFactory = createSessionFactory();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		UnittestReservation.setSession(session);
		UnittestReservation.setTransaction(transaction);
	}

	public void tearDown()
	{
		UnittestReservation.setSession(null);
		UnittestReservation.setTransaction(null);
	}

	public void test_01_CreerUtilisateur()
	{
		Long id1 =  UnittestReservation.CreerUtilisateur("Dupont");
		Long id2 =  UnittestReservation.CreerUtilisateur("Dupond");
		
		assertNotNull(id1);
		assertNotNull(id2);
		assertTrue(id1 < id2);
	}

	public void test_02_CreerSalle()
	{
		Long id1 =  UnittestReservation.CreerSalle("Salle 1");
		Long id2 =  UnittestReservation.CreerSalle("Salle 2");
		
		assertNotNull(id1);
		assertNotNull(id2);
		assertTrue(id1 < id2);
	}
		
	public void test_03_CreerOrdinateur()
	{
		Long id1 =  UnittestReservation.CreerOrdinateur("Ordi 1");
		Long id2 =  UnittestReservation.CreerOrdinateur("Ordi 2");
		
		assertNotNull(id1);
		assertNotNull(id2);
		assertTrue(id1 < id2);
	}		
	
	public void test_04_05_CreerEtAccederSalleEtOrdinateur()
	{
		Long id1 =  UnittestReservation.CreerOrdinateur("Ordi 1");
		Long id2 =  UnittestReservation.CreerOrdinateur("Ordi 2");
		Long id3 =  UnittestReservation.CreerSalle("Salle 1");
		Long id4 =  UnittestReservation.CreerSalle("Salle 2");
		Long id5 = new Long(id4 + 1);
		
		assertNull(   UnittestReservation.GetNomDeLaSalle(id5) );
		assertEquals( "Salle 2", UnittestReservation.GetNomDeLaSalle(id4) );
		assertEquals( "Salle 1", UnittestReservation.GetNomDeLaSalle(id3) );
		assertNull(   UnittestReservation.GetNomDeLaSalle(id2) );
		assertNull(   UnittestReservation.GetNomDeLaSalle(id1) );
		
		assertNull(   UnittestReservation.GetNomDeLOrdinateur(id5) );
		assertEquals( "Ordi 2", UnittestReservation.GetNomDeLOrdinateur(id2) );
		assertEquals( "Ordi 1", UnittestReservation.GetNomDeLOrdinateur(id1) );
		assertNull(   UnittestReservation.GetNomDeLOrdinateur(id4) );
		assertNull(   UnittestReservation.GetNomDeLOrdinateur(id3) );
	}
	
	public void test_06_CreerReservation()
	{
		Long userid1 =  UnittestReservation.CreerUtilisateur("Dupont");
		Long userid2 =  UnittestReservation.CreerUtilisateur("Dupond");
		
		Long salleid1 =  UnittestReservation.CreerSalle("Salle 1");
		Long salleid2 =  UnittestReservation.CreerSalle("Salle 2");
	
		Long unknown_id = Math.max(userid2, salleid2) + 1;
		
		for(int i = 0; i < 4; ++i)
		{
			Long userid  = (i % 2) == 0 ? userid1 : userid2;
			Long salleid = (i / 2) == 0 ? salleid1 : salleid2;
			LocalDateTime futureDateD = LocalDateTime.of(
					2030 + i,10,10,9,0); 
			LocalDateTime futureDateF = LocalDateTime.of(
					2030 + i,10,10,10,0); 
			LocalDateTime pastDate   = LocalDateTime.of(
					2010,10,10,9,0); 	
			
			assertNotNull( UnittestReservation.CreerReservation(
					salleid,
					userid,
					futureDateD, futureDateF ) );
			
			assertNull( UnittestReservation.CreerReservation(
					salleid,
					unknown_id,
					futureDateD, futureDateF) );
			assertNull( UnittestReservation.CreerReservation(
					unknown_id,
					userid,
					futureDateD, futureDateF) );
			assertNull( UnittestReservation.CreerReservation(
					salleid,
					userid,
					futureDateF, futureDateD) );
			assertNull( UnittestReservation.CreerReservation(
					salleid,
					userid,
					pastDate, futureDateD) );
		}
	}
		
	public void test_07_GetListeDeReservationPourUtilisateur()
	{
		Long userid1 =  UnittestReservation.CreerUtilisateur("Dupont");
		Long userid2 =  UnittestReservation.CreerUtilisateur("Dupond");
		
		Long salleid =  UnittestReservation.CreerSalle("Salle 1");
		List<Long> userid1_resas = new ArrayList<Long>();
		Set<Long> all_resas = new HashSet<Long>();
		
		for(int i = 0; i < 10; ++i)
		{
			Long userid  = (i % 2) == 0 ? userid1 : userid2;
			
			LocalDateTime futureDateD = LocalDateTime.of(2030,10,10+i, 9,0); 
			LocalDateTime futureDateF = LocalDateTime.of(2030,10,10+i,10,0);  	
			
			Long newResa = UnittestReservation.CreerReservation(
					salleid,
					userid,
					futureDateD, futureDateF );
			assertNotNull( newResa );
			assertFalse( all_resas.contains( newResa ) );
			
			if( (i % 2) == 0 )
			{
				userid1_resas.add( newResa );
			}
			all_resas.add(newResa);
			
			List<Long> liste_resa_to_test = UnittestReservation.GetListeDeReservationPourUtilisateur(userid1);
		
			assertEquals( userid1_resas.size(), liste_resa_to_test.size() );
			for(int j = 0; j < userid1_resas.size(); ++j)
			{
				assertEquals( userid1_resas.get(j), liste_resa_to_test.get(j));
			}
		}
	}
		
	public void test_08_GetListeDeReservationPourUtilisateurAvecDate()
	{
		Long userid1 =  UnittestReservation.CreerUtilisateur("Dupont");
		Long userid2 =  UnittestReservation.CreerUtilisateur("Dupond");
		
		Long salleid1 =  UnittestReservation.CreerSalle("Salle 1");
		Long salleid2 =  UnittestReservation.CreerSalle("Salle 2");
		
		List<Long> userid1_resas = new ArrayList<Long>();
		List<Long> userid2_resas = new ArrayList<Long>();
		List<Long> all_resas = new ArrayList<Long>();
		
		for(int i = 0; i < 8; ++i)
		{	
			LocalDateTime futureDateD = LocalDateTime.of(2030,10,10, 9+i,0); 
			LocalDateTime futureDateF = LocalDateTime.of(2030,10,10,10+i,0);  	
			
			Long newResa1 = UnittestReservation.CreerReservation(
					salleid1, userid1, futureDateD, futureDateF );
			Long newResa2 = UnittestReservation.CreerReservation(
					salleid2, userid2, futureDateD, futureDateF );
			
			assertNotNull( newResa1 );
			assertNotNull( newResa2 );
			
			userid1_resas.add( newResa1 );
			userid2_resas.add( newResa2 );
			all_resas.add( newResa1 );
			all_resas.add( newResa2 );
		}
		
		for(int i = 0; i < 17; ++i)
		{
			for(int j = i + 1; j < 17; ++j)
			{
				LocalDateTime futureDateD = LocalDateTime.of(2030,10,10,i / 2,30*(i%2)); 
				LocalDateTime futureDateF = LocalDateTime.of(2030,10,10,j / 2,30*(j%2));
				
				int nb_resas = (j+1)/2 - (i/2);
				
				List<Long> liste_resas = UnittestReservation.GetListeDeReservationPourUtilisateur( 
						userid1, futureDateD, futureDateF );
				
				assertEquals( liste_resas.size(), nb_resas );
				for(int k = i/2; k < (j+1)/2; ++k)
				{
					assertEquals( userid1_resas.get(k), liste_resas.get(k - (i/2)) );
				}
			}
		}
	}
		
	public void test_09_GetListeDeReservationPourRessource()
	{
		Long userid =  UnittestReservation.CreerUtilisateur("Dupont");
		
		Long salleid1 =  UnittestReservation.CreerSalle("Salle 1");
		Long salleid2 =  UnittestReservation.CreerSalle("Salle 2");
		
		List<Long> salle1_resas = new ArrayList<Long>();
		Set<Long> all_resas = new HashSet<Long>();
		
		for(int i = 0; i < 10; ++i)
		{
			Long salleid  = (i % 2) == 0 ? salleid1 : salleid2;
			
			LocalDateTime futureDateD = LocalDateTime.of(2030,10,10+i, 9,0); 
			LocalDateTime futureDateF = LocalDateTime.of(2030,10,10+i,10,0);  	
			
			Long newResa = UnittestReservation.CreerReservation(
					salleid,
					userid,
					futureDateD, futureDateF );
			assertNotNull( newResa );
			assertFalse( all_resas.contains( newResa ) );
			
			if( (i % 2) == 0 )
			{
				salle1_resas.add( newResa );
			}
			all_resas.add(newResa);
			
			List<Long> liste_resa_to_test = UnittestReservation.GetListeDeReservationPourRessource(salleid1);
		
			assertEquals( salle1_resas.size(), liste_resa_to_test.size() );
			for(int j = 0; j < salle1_resas.size(); ++j)
			{
				assertEquals( salle1_resas.get(j), liste_resa_to_test.get(j));
			}
		}
	}	
		// Fonction 10
		// Même chose, mais pour une ressource donnée
		// public static List<Long> GetListeDeReservationPourRessource(Long ressource_id, LocalDateTime debut, LocalDateTime fin)
		
		// Fonction 11
		// Pour un utilisateur donnée, renvoit la liste (sans duplication) des ressources réservées dans une ou plusieur
		// réservations, quelque soit la date  
		// public static Set<Long> GetListeDeRessourcesReservees(Long user_id)
			
		// Fonction 12
		// Pour un utilisateur donné et une ressource donnée, supprime l'ensemble des réservations 
		// recouvrant (et non pas strictement comprise dans) une plage donnée.
		// Renvoit la liste des id des réservations supprimées.
		// Renvoit une liste vide si aucune réservation n'a été supprimée.
		// (y compris si l'utilisateur ou la ressource n'existe pas)
		// public static List<Long> SupprimerReservations(Long user_id, Long ressource_id, LocalDateTime debut, LocalDateTime fin)
		
		
		// Fonction 13
		// Cherche, pour une plage donnée, l'ensemble des ressources disponibles
		//  - de type salle si salleSinonOrdinateur vaut True
		//  - de type ordinateur sinon
		// public static List<Long> TrouverRessourceDisponible(LocalDateTime debut, LocalDateTime fin, boolean salleSinonOrdinateur)
		

		// Fonction 14
		// Pour un ensemble de ressources donné, trouve le premier créneau, compris 
		// entre 9h et 17h - nb_hours disponible, à partir de dateDebut, où toutes les 
		// ressources sont disponibles
		// public static LocalDateTime TrouverCreneauDisponible(List<Long> ressource_id_list, LocalDateTime dateDebut, int nb_hours)
		
		
		// Fonction 15
		// Créé  une réservation en verifiant que la ressource est disponible
		//   et que les dates sont dans le futur. Renvoit null si la reservation
		//   n'est pas possible
		/*public static Long CreerReservationVerifiee(
				Long ressource_id, 
				Long user_id, 
				LocalDateTime dateDebut, 
				LocalDateTime dateFin )*/
	
	

}
