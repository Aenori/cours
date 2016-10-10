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


import com.simplon.java_hibernate.simple.CreneauHoraire;
import com.simplon.java_hibernate.simple.Reservation;
import com.simplon.java_hibernate.simple.Ressource;

import junit.framework.TestCase;


public abstract class TestUnitInMemorySession extends TestCase {
	protected SessionFactory sessionFactory;
	protected Session session;
	protected Transaction transaction;

	public void setUp() {
		sessionFactory = createSessionFactory();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	public void tearDown()
	{
		// Called after each test
	}
		
	protected SessionFactory createSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Ressource.class);
		configuration.addAnnotatedClass(Reservation.class);
		configuration.addAnnotatedClass(CreneauHoraire.class);
		
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
