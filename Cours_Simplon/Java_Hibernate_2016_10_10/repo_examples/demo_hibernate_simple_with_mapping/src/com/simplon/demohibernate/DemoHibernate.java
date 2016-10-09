package com.simplon.demohibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.simplon.demohibernate.simple.Reservation;
import com.simplon.demohibernate.simple.util.HibernateUtil;

public class DemoHibernate {
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
	
	
	public static void main(String[] args) throws Exception {
		connect_mysql_driver();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction tx = session.beginTransaction();
		
		for(int i = 0; i < 3; ++i)
		{
			Long res_1Id = (Long) session.save(new Reservation("Reservation " + (i+1)));
			System.out.println("Clé primaire :" + res_1Id);
		}

		tx.commit();
		
		session.close();
	}

}
