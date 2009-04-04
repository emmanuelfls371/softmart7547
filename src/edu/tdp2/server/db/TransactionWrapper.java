package edu.tdp2.server.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactionWrapper
{
	/**
	 * Graba el objeto o usando la sesion pasada por parametros, envolviendo la operacion en una transaccion que incluye
	 * esta operacion sola. La sesion no se cierra.
	 * 
	 * @param session
	 * @param o
	 */
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
	}
}
