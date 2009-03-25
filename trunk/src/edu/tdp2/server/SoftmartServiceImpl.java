package edu.tdp2.server;

import java.util.List;

import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.tdp2.client.SoftmartService;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.server.db.HibernateUtil;
import edu.tdp2.server.db.TransactionWrapper;
import edu.tdp2.server.exceptions.BadLoginException;
import edu.tdp2.server.model.Usuario;
import edu.tdp2.server.utils.Encrypter;

public class SoftmartServiceImpl extends RemoteServiceServlet implements SoftmartService
{
	private static final long serialVersionUID = 7772479472218006401L;

	@SuppressWarnings("unchecked")
	public String login(String userName, String passwordHash)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			List<Usuario> result = sess.createQuery("FROM Usuario WHERE Nombre = ? AND PasswordHash = ?").setString(0,
					userName).setString(1, passwordHash).list();
			if (result.size() > 0)
				return crearTicket(userName, result.get(0).getId());// Este array es 1-based
			else
				throw new BadLoginException("Login incorrecto");
		}
		finally
		{
			sess.close();
		}
	}

	private String crearTicket(String userName, long userId)
	{
		return userName + ";" + Encrypter.getInstance().encrypt("ID=" + userId);
	}

	@SuppressWarnings("unchecked")
	public List<String> getCiudades(String pais)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			return sess.createQuery("SELECT nombre FROM Ciudad WHERE pais.nombre = ?").setString(0, pais).list();
		}
		finally
		{
			sess.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getPaises()
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			return sess.createQuery("SELECT nombre FROM Pais ORDER BY nombre").list();
		}
		finally
		{
			sess.close();
		}
	}

	@Override
	public String registrar(UsuarioDto usuarioDto)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			TransactionWrapper.save(sess, new Usuario(usuarioDto, sess));
			return null;
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
		finally
		{
			sess.close();
		}
	}
}
