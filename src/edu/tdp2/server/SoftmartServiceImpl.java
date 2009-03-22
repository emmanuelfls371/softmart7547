package edu.tdp2.server;

import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.tdp2.client.SoftmartService;
import edu.tdp2.server.db.HibernateUtil;
import edu.tdp2.server.db.TransactionWrapper;
import edu.tdp2.server.model.Proyecto;
import edu.tdp2.server.model.Usuario;

public class SoftmartServiceImpl extends RemoteServiceServlet implements SoftmartService
{
	private static final long serialVersionUID = 7772479472218006401L;

	@Override
	public String login(String userName, String passwordHash)
	{
		Session sess = HibernateUtil.getSession();

		Usuario moncho = new Usuario();
		moncho.setNombre("Moncho");
		TransactionWrapper.save(sess, moncho);
		Proyecto proy = new Proyecto();
		proy.setNombre("Ir a la luna");
		TransactionWrapper.save(sess, proy);
		sess.close();

		/*
		 * moncho = null; sess = HibernateUtil.getSession(); for (Object u :
		 * sess.createQuery("from Usuario where Nombre = ?").setString(0, "Moncho").list())
		 * System.out.println(((Usuario) u).getNombre()); // TODO Auto-generated method stub sess.close();
		 */
		return "1";
	}
}
