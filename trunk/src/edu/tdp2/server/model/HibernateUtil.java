package edu.tdp2.server.model;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil
{
	private static final SessionFactory sessionFactory;
	static
	{
		try
		{
			BasicConfigurator.configure();
			sessionFactory = new AnnotationConfiguration().addAnnotatedClass(Usuario.class).configure()
					.buildSessionFactory();
		}
		catch (Throwable ex)
		{
			// Log exception!
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session getSession() throws HibernateException
	{
		return sessionFactory.openSession();
	}
}