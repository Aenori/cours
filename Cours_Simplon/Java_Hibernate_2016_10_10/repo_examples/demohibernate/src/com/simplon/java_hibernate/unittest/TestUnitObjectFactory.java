package com.simplon.java_hibernate.unittest;

import org.hibernate.Session;

import com.simplon.java_hibernate.simple.Ressource;

public class TestUnitObjectFactory {

	public static void createRessource1(Session session)
	{
		session.save(new Ressource("Salle 1",14,true));
	}

	public static void createRessource2(Session session)
	{
		session.save(new Ressource("Ordi_1",null,false));
	}

}
