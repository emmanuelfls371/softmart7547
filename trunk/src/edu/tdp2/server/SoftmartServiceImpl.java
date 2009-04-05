package edu.tdp2.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.tdp2.client.SoftmartService;
import edu.tdp2.client.dto.CalificacionDto;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.server.db.HibernateUtil;
import edu.tdp2.server.db.TransactionWrapper;
import edu.tdp2.server.exceptions.BadLoginException;
import edu.tdp2.server.model.Calificacion;
import edu.tdp2.server.model.Contrato;
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
		final Session sess = HibernateUtil.getSession();
		try
		{
			final Usuario us = getUsuario(sess, proyecto.getUsuario());
			if (us != null)
			{
				final Proyecto nuevo = new Proyecto(proyecto, us);
				if (us.addProyecto(nuevo))
					TransactionWrapper.execute(sess, new TransactionWrapper.Action()
					{
						public void execute()
						{
							sess.save(nuevo);
							sess.save(us);
						}
					});
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

	@SuppressWarnings("unchecked")
	private Proyecto getProyecto(Session sess, String proyName)
	{
		List<Proyecto> result = sess.createQuery("FROM Proyecto WHERE nombre = ?").setString(0, proyName).list();
		if (result.size() > 0)
			return result.get(0);
		else
			return null;
	}

	public String ofertar(OfertaDto ofertaDto)
	{
		final Session sess = HibernateUtil.getSession();
		try
		{
			final Proyecto proy = getProyecto(sess, ofertaDto.getProyecto());
			Usuario us = getUsuario(sess, ofertaDto.getUsuario());
			if (proy != null && us != null)
			{
				final Oferta nueva = new Oferta(ofertaDto, proy, us);
				if (proy.addOferta(nueva) && us.addOferta(nueva))
					TransactionWrapper.execute(sess, new TransactionWrapper.Action()
					{
						public void execute()
						{
							sess.save(nueva);
							sess.save(proy);
						}
					});
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

	@SuppressWarnings("unchecked")
	private Contrato getContrato(Session sess, String proyName)
	{
		List<Contrato> result = sess.createQuery("FROM Contrato WHERE proyecto.nombre = ?").setString(0, proyName)
				.list();
		if (result.size() > 0)
			return result.get(0);
		else
			return null;
	}

	public String calificar(CalificacionDto dto)
	{
		final Session sess = HibernateUtil.getSession();
		try
		{
			final Contrato c = getContrato(sess, dto.getProyecto());
			if (c != null)
			{
				final Calificacion calif = new Calificacion(dto, c);
				if (calif != null)
				{
					if (c.getProyecto().getUsuario().getLogin().compareTo(dto.getUsuario()) == 0)
						c.setCalifComprador(calif);
					else if (c.getOfertaGanadora().getUsuario().getLogin().compareTo(dto.getUsuario()) == 0)
						c.setCalifVendedor(calif);
					else
						return null;
					TransactionWrapper.execute(sess, new TransactionWrapper.Action()
					{
						public void execute()
						{
							sess.save(calif);
							sess.save(c);
						}
					});
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Proyecto> getUnassignedProjects()
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			return sess.createQuery("FROM Proyecto WHERE contrato IS NULL AND fecha >= current_date").list();
		}
		finally
		{
			sess.close();
		}
	}
}