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


public class TestUnit extends TestCase {
	private static SessionFactory sessionFactory;
	private static Session session;
	private static Transaction transaction;

	public void setUp() {
		sessionFactory = createSessionFactory();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	public void tearDown()
	{
		// Called after each test
	}
	
	public void testSimpleRessource1() {
		createRessource1();
		
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
		createRessource2();
		createRessource1();
		
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

	private void createRessource1()
	{
		session.save(new Ressource("Salle 1",14,true));
	}

	private void createRessource2()
	{
		session.save(new Ressource("Ordi_1",null,false));
	}
	
	private static SessionFactory createSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Ressource.class);

		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		configuration.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
		configuration.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:java_simplon");
		configuration.setProperty("hibernate.connection.username", "sa");
		configuration.setProperty("hibernate.connection.password", "");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		configuration.setProperty("show_sql", "true" );
		
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		return sessionFactory;
	}
}
