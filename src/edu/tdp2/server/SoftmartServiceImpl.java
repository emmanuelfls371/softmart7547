package edu.tdp2.server;

import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.tdp2.client.SoftmartService;
import edu.tdp2.server.model.HibernateUtil;
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
		sess.save(moncho);
		sess.flush();
		sess.close();
		// TODO Auto-generated method stub
		return null;
	}
}
