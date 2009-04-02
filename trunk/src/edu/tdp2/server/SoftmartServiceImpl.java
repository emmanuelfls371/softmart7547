package edu.tdp2.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.tdp2.client.SoftmartService;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.server.db.HibernateUtil;
import edu.tdp2.server.db.TransactionWrapper;
import edu.tdp2.server.exceptions.BadLoginException;
import edu.tdp2.server.model.DificultadProyecto;
import edu.tdp2.server.model.NivelReputacion;
import edu.tdp2.server.model.Oferta;
import edu.tdp2.server.model.Presupuesto;
import edu.tdp2.server.model.Proyecto;
import edu.tdp2.server.model.TamanioProyecto;
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
			List<Usuario> result = sess.createQuery("FROM Usuario WHERE login = ? AND passwordHash = ?").setString(0,
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

	@SuppressWarnings("unchecked")
	private Usuario getUsuario(Session sess, String userName)
	{
		List<Usuario> result = sess.createQuery("FROM Usuario WHERE login = ?").setString(0, userName).list();
		if (result.size() > 0)
			return result.get(0);
		else
			return null;
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
		catch (Exception e)
		{
			return e.getMessage();
		}
		finally
		{
			sess.close();
		}
	}

	public String publicar(ProyectoDto proyecto)
	{
		Session sess = HibernateUtil.getSession();
		try
		{
			Usuario us = getUsuario(sess, proyecto.getUsuario());
			if (us != null)
			{
				Proyecto nuevo = new Proyecto(proyecto, us);
				if (us.addProyecto(nuevo))
				{
					TransactionWrapper.save(sess, nuevo);
					TransactionWrapper.save(sess, us);
				}
			}
			return null;
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
		finally
		{
			sess.close();
		}
	}

	public List<String> getNiveles()
	{
		List<String> lista = new ArrayList<String>();
		for (NivelReputacion n : NivelReputacion.values())
			lista.add(n.name());
		return lista;
	}

	public List<String> getDificultades()
	{
		List<String> lista = new ArrayList<String>();
		for (DificultadProyecto n : DificultadProyecto.values())
			lista.add(n.name());
		return lista;
	}

	public List<String> getTamanios()
	{
		List<String> lista = new ArrayList<String>();
		for (TamanioProyecto n : TamanioProyecto.values())
			lista.add(n.name());
		return lista;
	}

	public List<String> getPresupuestos()
	{
		return Presupuesto.armarRangos();
	}
	
	private Proyecto getProyecto(Session sess, String proyName){
		List<Proyecto> result = sess.createQuery("FROM Proyecto WHERE Nombre = ?").setString(0, proyName).list();
		if (result.size() > 0)
			return result.get(0);
		else
			return null;		
	}
	
	public String ofertar(OfertaDto ofertaDto){
		Session sess = HibernateUtil.getSession();
		try
		{
			Proyecto proy = getProyecto(sess, ofertaDto.getProyecto());
			Usuario us = getUsuario(sess, ofertaDto.getUsuario());
			if (proy != null&&us !=null)
			{
				Oferta nueva=new Oferta(ofertaDto, proy, us);
				if (proy.addOferta(nueva)&&us.addOferta(nueva))
				{
					TransactionWrapper.save(sess, nueva);
					TransactionWrapper.save(sess, proy);
				}
			}
			return null;
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
		finally
		{
			sess.close();
		}
	}
	
}