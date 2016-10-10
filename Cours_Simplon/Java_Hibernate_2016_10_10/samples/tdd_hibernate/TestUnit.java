package com.simplon.java_hibernate.unittest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import org.junit.Before;
import org.junit.Test;


import com.simplon.java_hibernate.simple.Ressource;

import junit.framework.TestCase;


public class TestUnit extends TestUnitInMemorySession {
	public void testSimpleRessource1() {
		TestUnitObjectFactory.createRessource1(session);
		
		Query q = session.createQuery("from Ressource as r");
		List<Ressource> ressources = q.list();
		
		assertEquals( ressources.size(), 1 );
		Ressource r = ressources.get(0);
		assertEquals( r.getId()  , new Long(1) );
		assertEquals( r.getName(), "Salle 1" );
		assertEquals( r.getCapacite(), new Long(14) );
		transaction.rollback();
	}
	
	public void testSimpleRessource2() {
		TestUnitObjectFactory.createRessource2(session);
		TestUnitObjectFactory.createRessource1(session);
		
		Query q = session.createQuery("from Ressource as r");
		List<Ressource> ressources = q.list();
		
		assertEquals( ressources.size(), 2 );
		Ressource r = ressources.get(0);
		assertEquals( r.getId()  , new Long(1) );
		assertEquals( r.getName(), "Ordi_1" );
		assertNull( r.getCapacite() );
		r = ressources.get(1);
		assertEquals( r.getId()  , new Long(2) );
		assertEquals( r.getName(), "Salle 1" );
		assertEquals( r.getCapacite(), new Long(14) );
		transaction.rollback();
	}

}
