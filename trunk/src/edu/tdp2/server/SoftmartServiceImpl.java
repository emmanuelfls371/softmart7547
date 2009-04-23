package edu.tdp2.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NonUniqueResultException;

import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.tdp2.client.SoftmartService;
import edu.tdp2.client.TipoCalificacion;
import edu.tdp2.client.dto.CalificacionDto;
import edu.tdp2.client.dto.ContratoDto;
import edu.tdp2.client.dto.FiltroDto;
import edu.tdp2.client.dto.MyAccountDto;
import edu.tdp2.client.dto.MyCompradorAccount;
import edu.tdp2.client.dto.MyVendedorAccount;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.client.model.Calificacion;
import edu.tdp2.client.model.Ciudad;
import edu.tdp2.client.model.Contrato;
import edu.tdp2.client.model.DificultadProyecto;
import edu.tdp2.client.model.Moneda;
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
		List<Contrato> result = sess.createQuery("FROM Contrato WHERE proyecto.id = ?").setLong(0, projectId).list();
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
	public List<Proyecto> getUnassignedProjects(String usuario)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			List<Proyecto> projects = (List<Proyecto>) sess.createQuery(
					"FROM Proyecto AS proy WHERE proy NOT IN (SELECT proyecto FROM Contrato) AND "
							+ "fecha >= current_date() AND usuario.login != ? AND cancelado = false").setString(0,
					usuario).list();
			for (Proyecto project : projects)
			{
				project.prune();
			}
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
					+ "AND contrato.califAlVendedor IS NULL)) AND cancelado = false";
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
					+ "AND fecha >= current_date() AND proy.usuario.login = ? AND proy.cancelado = false";
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

	public List<ContratoDto> getCalificacionesHechas(String user)
	{
		String sql = "FROM Contrato WHERE (califVendedor IS NOT NULL AND proyecto.usuario.login=?) OR (califComprador IS NOT NULL AND ofertaGanadora.usuario.login=?)";
		return getCalificaciones(sql, user, TipoCalificacion.Hecha);
	}

	public List<ContratoDto> getCalificacionesRecibidas(String user)
	{
		String sql = "FROM Contrato WHERE (califComprador IS NOT NULL AND proyecto.usuario.login=?) OR (califVendedor IS NOT NULL AND ofertaGanadora.usuario.login=?)";
		return getCalificaciones(sql, user, TipoCalificacion.Recibida);
	}

	private void setCalifVendedor(Contrato c, CalificacionDto calif)
	{
		calif.setCalificacion(c.getCalifAlVendedor().getCalificacion());
		calif.setComentario(c.getCalifAlVendedor().getComentario());
	}

	private void setCalifComprador(Contrato c, CalificacionDto calif)
	{
		calif.setCalificacion(c.getCalifAlComprador().getCalificacion());
		calif.setComentario(c.getCalifAlComprador().getComentario());
	}

	private CalificacionDto getCalif(TipoCalificacion tipo, Contrato c, String user)
	{
		CalificacionDto calif = new CalificacionDto();
		if (tipo.name().compareTo(TipoCalificacion.Recibida.toString()) == 0)
		{
			if (c.getProyecto().getUsuario().getLogin().compareTo(user) == 0)
			{
				setCalifComprador(c, calif);
				calif.setUsuario(c.getOfertaGanadora().getUsuario().getLogin());
			}
			else if (c.getOfertaGanadora().getUsuario().getLogin().compareTo(user) == 0)
			{
				setCalifVendedor(c, calif);
				calif.setUsuario(c.getProyecto().getUsuario().getLogin());
			}
		}
		else if (tipo.name().compareTo(TipoCalificacion.Hecha.toString()) == 0)
		{
			if (c.getProyecto().getUsuario().getLogin().compareTo(user) == 0)
			{
				setCalifVendedor(c, calif);
				calif.setUsuario(c.getOfertaGanadora().getUsuario().getLogin());
			}
			else if (c.getOfertaGanadora().getUsuario().getLogin().compareTo(user) == 0)
			{
				setCalifComprador(c, calif);
				calif.setUsuario(c.getProyecto().getUsuario().getLogin());
			}
		}
		else
			return null;
		return calif;
	}

	@SuppressWarnings("unchecked")
	private List<ContratoDto> getCalificaciones(String sql, String user, TipoCalificacion tipo)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			List<Contrato> contratos = (List<Contrato>) sess.createQuery(sql).setString(0, user).setString(1, user)
					.list();
			List<ContratoDto> dtos = new ArrayList<ContratoDto>();
			for (Contrato c : contratos)
			{
				ContratoDto dto = new ContratoDto();
				dto.setCalif(getCalif(tipo, c, user));
				dto.setIdContrato(c.getId());
				dto.setProyecto(getProyectoDto(c.getProyecto()));
				dto.setOferta(getOfertaDto(c.getOfertaGanadora()));
				dtos.add(dto);
			}
			return dtos;
		}
		finally
		{
			sess.close();
		}
	}

	private ProyectoDto getProyectoDto(Proyecto p)
	{
		ProyectoDto dto = new ProyectoDto();
		dto.setArchivo(p.getPathArchivo());
		dto.setDescripcion(p.getDescripcion());
		dto.setDificultad(p.getDificultad());
		dto.setTamanio(p.getTamanio());
		dto.setFecha(p.getFecha());
		dto.setMoneda(p.getMoneda());
		dto.setNivel(p.getNivel());
		dto.setNombre(p.getNombre());
		dto.setPresupuesto(Presupuesto.armarRango(p.getMinPresupuesto(), p.getMaxPresupuesto()));
		dto.setUsuario(p.getUsuario().getLogin());
		return dto;
	}

	private OfertaDto getOfertaDto(Oferta o)
	{
		OfertaDto dto = new OfertaDto();
		dto.setDescripcion(o.getDescripcion());
		dto.setDias(o.getDias());
		dto.setMonto(o.getMonto());
		dto.setMoneda(o.getMoneda());
		dto.setUsuario(o.getUsuario().getLogin());
		return dto;
	}

	public String cancelarProyecto(Long projectId)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			Proyecto project = (Proyecto) sess.get(Proyecto.class, projectId);
			if (project == null)
				throw new SoftmartServerException("No se encuentra el proyecto con id: " + projectId);
			if (project.isCancelado())
				throw new SoftmartServerException("El proyecto ya esta cancelado");
			project.setCancelado(true);
			TransactionWrapper.save(sess, project);
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
	public MyAccountDto getMyAccountData(String login)
	{
		Session sess = HibernateUtil.getSession();
		MyAccountDto dto = new MyAccountDto();

		try
		{
			Usuario usuario = (Usuario) sess.createQuery("FROM Usuario WHERE login = ?").setString(0, login)
					.uniqueResult();
			if (usuario == null)
				throw new SoftmartServerException("No se encuentra el usuario con login: " + login);
			dto.setNombre(usuario.getNombre());
			dto.setApellido(usuario.getApellido());
			dto.setPais(usuario.getCiudad().getPais().getNombre());
			dto.setEmail(usuario.getEmail());
			dto.setUsuario(login);
			dto.setCiudad(usuario.getCiudad().getNombre());
			dto.setNivel(NivelReputacion.valueOf(usuario.getNivel()));

			MyCompradorAccount comprador = dto.getDatosComprador();
			comprador.setReputacion((Double) sess.createQuery(
					"SELECT AVG(califAlComprador.calificacion) FROM Contrato WHERE proyecto.usuario = ?").setParameter(
					0, usuario).uniqueResult());
			comprador.setProyectosSinRecibirCalif((List<Proyecto>) sess.createQuery(
					"FROM Proyecto WHERE usuario = ? AND contrato.califAlComprador IS NULL").setParameter(0, usuario)
					.list());
			comprador.setProyectosSinCalificar((List<Proyecto>) sess.createQuery(
					"FROM Proyecto WHERE usuario = ? AND contrato.califAlVendedor IS NULL").setParameter(0, usuario)
					.list());
			comprador.setProyectosCerrados((List<Proyecto>) sess.createQuery(
					"FROM Proyecto WHERE usuario = ? AND contrato.califAlComprador IS NOT NULL "
							+ "AND contrato.califAlVendedor IS NOT NULL").setParameter(0, usuario).list());
			comprador.setProyectosCancelados((List<Proyecto>) sess.createQuery(
					"FROM Proyecto WHERE usuario = ? AND cancelado = true").setParameter(0, usuario).list());
			comprador.setProyectosAbiertos((List<Proyecto>) sess.createQuery(
					"FROM Proyecto WHERE usuario = ? AND (contrato IS NULL OR contrato.califAlComprador IS NULL "
							+ "OR contrato.califAlVendedor IS NULL)").setParameter(0, usuario).list());

			MyVendedorAccount vendedor = dto.getDatosVendedor();
			vendedor.setReputacion((Double) sess.createQuery(
					"SELECT AVG(califAlVendedor.calificacion) FROM Contrato WHERE ofertaGanadora.usuario = ?")
					.setParameter(0, usuario).uniqueResult());
			vendedor.setProyectosSinRecibirCalif((List<Proyecto>) sess.createQuery(
					"FROM Proyecto WHERE contrato.ofertaGanadora.usuario = ? AND contrato.califAlComprador IS NULL")
					.setParameter(0, usuario).list());
			vendedor.setProyectosSinCalificar((List<Proyecto>) sess.createQuery(
					"FROM Proyecto WHERE contrato.ofertaGanadora.usuario = ? AND contrato.califAlVendedor IS NULL")
					.setParameter(0, usuario).list());
			vendedor.setProyectosCerrados((List<Proyecto>) sess.createQuery(
					"FROM Proyecto WHERE contrato.ofertaGanadora.usuario = ? AND contrato.califAlComprador IS NOT NULL "
							+ "AND contrato.califAlVendedor IS NOT NULL").setParameter(0, usuario).list());
			vendedor.setProyectosCancelados((List<Proyecto>) sess.createQuery(
					"FROM Proyecto WHERE contrato.ofertaGanadora.usuario = ? AND cancelado = true").setParameter(0,
					usuario).list());
			vendedor.setProyectosConOfertasAbiertas((List<Proyecto>) sess.createQuery(
					"FROM Proyecto proy JOIN proy.ofertas AS oferta "
							+ "WHERE oferta.usuario = ? AND oferta.contrato IS NULL").setParameter(0, usuario).list());
			List<Object[]> ganancia = (List<Object[]>) sess.createQuery(
					"SELECT contrato.ofertaGanadora.moneda, SUM(contrato.ofertaGanadora.monto) AS monto FROM Proyecto "
							+ "WHERE contrato.ofertaGanadora.usuario = ? AND contrato.califAlComprador IS NOT NULL "
							+ "AND contrato.califAlVendedor IS NOT NULL GROUP BY contrato.ofertaGanadora.moneda")
					.setParameter(0, usuario).list();
			Map<Moneda, Long> gananciaAcumulada = new HashMap<Moneda, Long>();
			for (Object[] row : ganancia)
				gananciaAcumulada.put(Moneda.valueOf((String) row[0]), (Long) row[1]);
			vendedor.setGananciaAcumulada(gananciaAcumulada);

			return dto;
		}
		finally
		{
			sess.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Proyecto> filterProject(FiltroDto filtro) {
		
		Session sess = HibernateUtil.getSession();

		try
		{
			String consulta=new String();
			if(filtro.getPresupuesto()!=null&&filtro.getMoneda()!=null){
	
			}
			if(filtro.getComplejidad()!=null){
				consulta+=" AND dificultad = '"+filtro.getComplejidad()+"'";
			}
			if(filtro.getFechaDesde()!=null){
				consulta+=" AND fecha >= :fecha_desde";
			}else{
				consulta+=" AND fecha >= current_date()";
			}			
			if(filtro.getFechaHasta()!=null){
				consulta+=" AND fecha <= :fecha_hasta";
			}
			if(filtro.getReputacion()!=null && !filtro.getReputacion().isEmpty()){
				consulta+=" AND nivel = '"+filtro.getReputacion()+"'";
			}
			if(filtro.getTamanio()!=null){
				consulta+=" AND tamanio = '"+filtro.getTamanio()+"'";
			}
			List<Proyecto> projects = null;
			if(filtro.getFechaDesde()!=null&&filtro.getFechaHasta()==null){
				
				projects = (List<Proyecto>) sess.createQuery(
						"FROM Proyecto AS proy WHERE proy NOT IN (SELECT proyecto FROM Contrato) "
								+ "AND usuario.login != ? AND cancelado = false"+consulta).setString(0,
						filtro.getUsuario()).setDate("fecha_desde",filtro.getFechaDesde()).list();
				
			}
			if(filtro.getFechaHasta()!=null&&filtro.getFechaDesde()==null){
				projects = (List<Proyecto>) sess.createQuery(
						"FROM Proyecto AS proy WHERE proy NOT IN (SELECT proyecto FROM Contrato) "
								+ "AND usuario.login != ? AND cancelado = false"+consulta).setString(0,
						filtro.getUsuario()).setDate("fecha_hasta",filtro.getFechaHasta()).list();
			}
			if(filtro.getFechaDesde()!=null&&filtro.getFechaHasta()!=null){
				projects = (List<Proyecto>) sess.createQuery(
						"FROM Proyecto AS proy WHERE proy NOT IN (SELECT proyecto FROM Contrato) "
								+ "AND usuario.login != ? AND cancelado = false"+consulta).setString(0,
						filtro.getUsuario()).setDate("fecha_desde",filtro.getFechaDesde()).setDate("fecha_hasta",filtro.getFechaHasta()).list();
			
			}			
			if(filtro.getFechaHasta()==null&&filtro.getFechaDesde()==null){
				projects = (List<Proyecto>) sess.createQuery(
						"FROM Proyecto AS proy WHERE proy NOT IN (SELECT proyecto FROM Contrato) "
								+ "AND usuario.login != ? AND cancelado = false"+consulta).setString(0,
						filtro.getUsuario()).list();
			}
			
			
			for (Proyecto project : projects)
			{
				project.prune();
			}
			return projects;
		}
		finally
		{
			sess.close();
		}
	}
}