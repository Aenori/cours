package com.simplon.java_hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.simplon.java_hibernate.simple.Reservation;
import com.simplon.java_hibernate.simple.Ressource;

public class Main {
	public static void connect_mysql_driver() throws Exception
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e1) {
			e1.printStackTrace();
			throw new Exception("Could not load drivers !");
		}
	}

	public static void example1() throws Exception
	{
		connect_mysql_driver();

		// 1 : Ouverture unité de travail hibernate
		Session session = HibernateUtil.getSessionFactory().openSession();

		// 2 : Ouverture transaction 
		Transaction tx = session.beginTransaction();

		// 3 : Instanciation Objet métier
		Ressource salle_1 = new Ressource("Salle 1",14,true);

		// 4 : Persistance Objet/Relationnel : création d'un enregistrement en base
		Long salle_1Id = (Long) session.save(salle_1);
		System.out.println("Clé primaire :" + salle_1Id);

		Reservation res = new Reservation(0, salle_1);
		//salle_1.getReservations().add(res);
		session.save( res );
		res = new Reservation(0, salle_1);
		//salle_1.getReservations().add(res);
		session.save( res );
		
		session.save( new Ressource("Ordi 1",null,false) );
		// 5 : Fermeture transaction 
		tx.commit();

		Ressource ret_salle_1 = session.get(Ressource.class, new Long(1));
		
		System.out.println(ret_salle_1.getReservations().size() + " / " + ret_salle_1.getName()); 
		for(Reservation resa: ret_salle_1.getReservations())
		{
			System.out.println("Resa id : " + resa.getId());
		}
		// 6 : Fermeture unité de travail hibernate 
		session.close();
	}
	
	public static void main(String[] args) throws Exception {
		example1();
	}

}
