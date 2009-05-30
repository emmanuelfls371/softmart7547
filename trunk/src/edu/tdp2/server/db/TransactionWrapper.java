package edu.tdp2.server.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.tdp2.client.model.AbstractDomainObject;

public class TransactionWrapper
{
	public interface Action
	{
		void execute();
	}

	public static void delete(final Session session, final AbstractDomainObject o)
	{
		execute(session, new Action()
		{
			public void execute()
			{
				session.delete(o);
			}
		});
	}

	public static void execute(Session session, Action action)
	{
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			action.execute();
			tx.commit();
		}
		catch (HibernateException he)
		{
			if (tx != null)
				tx.rollback();
			throw he;
		}
	}

	/**
	 * Graba el objeto o usando la sesion pasada por parametros, envolviendo la operacion en una transaccion que incluye
	 * esta operacion sola. La sesion no se cierra.
	 * 
	 * @param session
	 * @param o
	 */
	public static void save(final Session session, final AbstractDomainObject o)
	{
		execute(session, new Action()
		{
			public void execute()
			{
				session.save(o);
			}
		});
	}

	public static void update(final Session session, final AbstractDomainObject o)
	{
		execute(session, new Action()
		{
			public void execute()
			{
				session.update(o);
			}
		});
	}
}
