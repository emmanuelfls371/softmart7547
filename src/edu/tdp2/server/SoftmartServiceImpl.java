package edu.tdp2.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.tdp2.client.SoftmartService;
import edu.tdp2.client.dto.CalificacionDto;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.client.model.Calificacion;
import edu.tdp2.client.model.Ciudad;
import edu.tdp2.client.model.Contrato;
import edu.tdp2.client.model.DificultadProyecto;
import edu.tdp2.client.model.NivelReputacion;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Presupuesto;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.model.TamanioProyecto;
import edu.tdp2.client.model.Usuario;
import edu.tdp2.server.db.HibernateUtil;
import edu.tdp2.server.db.TransactionWrapper;
import edu.tdp2.server.exceptions.BadLoginException;
import edu.tdp2.server.exceptions.SoftmartServerException;
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

	public String registrar(UsuarioDto usuarioDto)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			if (sess.createQuery("FROM Usuario WHERE login = ?").setString(0, usuarioDto.getUsuario()).uniqueResult() != null)
				throw new NonUniqueResultException("El nombre de usuario \"" + usuarioDto.getUsuario() + "\" ya existe");
			usuarioDto.setCiudad((Ciudad) sess.createQuery("FROM Ciudad WHERE nombre = ? AND pais.nombre = ?")
					.setString(0, (String) usuarioDto.getCiudad()).setString(1, usuarioDto.getPais()).uniqueResult());
			TransactionWrapper.save(sess, new Usuario(usuarioDto));
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
	private Proyecto getProyecto(Session sess, long projectId)
	{
		List<Proyecto> result = sess.createQuery("FROM Proyecto WHERE id = ?").setLong(0, projectId).list();
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
	private Contrato getContrato(Session sess, long projectId)
	{
		List<Contrato> result = sess.createQuery("FROM Contrato WHERE proyecto.nombre = ?").setLong(0, projectId)
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
					if (c.getProyecto().getUsuario().getLogin().equals(dto.getUsuario())) // Usuario es el que publico
						c.setCalifAlVendedor(calif); // El comprador califica al vendedor
					else if (c.getOfertaGanadora().getUsuario().getLogin().equals(dto.getUsuario())) // El que oferto
						c.setCalifAlComprador(calif); // El vendedor califica al comprador
					else
						throw new SoftmartServerException(
								"No se puede calificar si no se es el comprador ni el vendedor del proyecto");
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
	public List<Proyecto> getUnassignedProjects()
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			List<Proyecto> projects = (List<Proyecto>) sess
					.createQuery(
							"FROM Proyecto AS proy WHERE proy NOT IN (SELECT proyecto FROM Contrato) AND fecha >= current_date()")
					.list();
			for (Proyecto project : projects)
				project.prune();
			return projects;
		}
		finally
		{
			sess.close();
		}
	}

	/**
	 * Obtiene todos los proyectos en que el usuario <tt>user</tt> pasado por parametro puede calificar
	 * 
	 * @param user
	 *            El login del usuario que debe calificar
	 */
	@SuppressWarnings("unchecked")
	public List<Proyecto> getQualifiableProjects(String user)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			String sql = "FROM Proyecto WHERE ((contrato.ofertaGanadora.usuario.login = :usr "
					+ "AND contrato.califAlComprador IS NULL) OR (contrato.proyecto.usuario.login = :usr "
					+ "AND contrato.califAlVendedor IS NULL))";
			List<Proyecto> projects = (List<Proyecto>) sess.createQuery(sql).setString("usr", user).list();
			for (Proyecto project : projects)
				project.prune();
			return projects;
		}
		finally
		{
			sess.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Proyecto> getOwnOpenProjects(String user)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			String sql = "FROM Proyecto AS proy WHERE proy NOT IN (SELECT proyecto FROM Contrato) "
					+ "AND fecha >= current_date() AND proy.usuario.login = ?";
			List<Proyecto> projects = (List<Proyecto>) sess.createQuery(sql).setString(0, user).list();
			for (Proyecto project : projects)
				project.prune();
			return projects;
		}
		finally
		{
			sess.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Oferta> getOffers(Proyecto project)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			List<Oferta> offers = (List<Oferta>) sess.createQuery("FROM Oferta WHERE proyecto = ?").setParameter(0,
					project).list();
			for (Oferta offer : offers)
				offer.prune();
			return offers;
		}
		finally
		{
			sess.close();
		}
	}

	@Override
	public String chooseOffer(long offerId)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			Oferta offer = (Oferta) sess.get(Oferta.class, offerId);
			if (offer == null)
				throw new SoftmartServerException("No se encuentra a la oferta con id: " + offer);
			if (offer.getContrato() != null)
				throw new SoftmartServerException("La oferta ya tiene un contrato asociado");
			Contrato contrato = new Contrato();
			contrato.setOfertaGanadora(offer);
			contrato.setProyecto(offer.getProyecto());
			TransactionWrapper.save(sess, contrato);
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