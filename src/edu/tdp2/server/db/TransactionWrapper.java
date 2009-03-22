package edu.tdp2.server.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactionWrapper
{
	public static void save(Session session, Object o)
	{
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.save(o);
			tx.commit();
		}
		catch (HibernateException he)
		{
			if (tx != null)
				tx.rollback();
			throw he;
		}
/*		finally
		{
			session.close();
		}*/
	}
}
