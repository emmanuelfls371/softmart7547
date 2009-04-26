package edu.tdp2.server;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import edu.tdp2.client.dto.FiltroDto;
import edu.tdp2.client.dto.MyAccountDto;
import edu.tdp2.client.dto.MyCompradorAccount;
import edu.tdp2.client.dto.MyVendedorAccount;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.NivelReputacion;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.model.Usuario;
import edu.tdp2.server.db.HibernateUtil;
import edu.tdp2.server.db.TransactionWrapper;
import edu.tdp2.server.exceptions.SoftmartServerException;

import junit.framework.TestCase;

public class SoftmartServiceTest extends TestCase
{
	
	private static String nombreProy="lala";
	private static String descripcion="lalala";
	private static String presupuesto="100 a 500";
	private static String nivel="Normal";
	private static String tam="Chico";
	private static String dif="Simple";
	private static String moneda="Peso";
	
	private static String usProy="chechu";
	private static String usOf="ariel";
	private static String login="chechu";
	
	private static String descOf="prueba unitaria";
	private static int dias=10;
	private static int monto=200;
	private static String notif="Si";
	
	public void testPublicar()
	{
		Session sess = HibernateUtil.getSession();
		Usuario us = getUsuario(sess,usProy );
		assertNotNull(us);
		ProyectoDto dto = new ProyectoDto();
		dto.setNombre(nombreProy);
		dto.setDescripcion(descripcion);
		dto.setPresupuesto(presupuesto);
		dto.setNivel(nivel);
		dto.setTamanio(tam);
		dto.setDificultad(dif);
		dto.setMoneda(moneda);
		dto.setFecha(Calendar.getInstance().getTime());
		Proyecto nuevo = new Proyecto(dto, us, buscarMoneda(dto.getMoneda()));
		assertTrue(us.addProyecto(nuevo));
			try{
				TransactionWrapper.save(sess, nuevo);
				TransactionWrapper.save(sess, us);
			}catch(Exception e){
				assertFalse(false);
			}
		sess.close();
	}
	
	Moneda buscarMoneda(String nombre){
		Session sess = HibernateUtil.getSession();

		try
		{
			List<Moneda> monedas= sess.createQuery("FROM Moneda WHERE nombre = ?").setString(0,nombre).list();
			return monedas.get(0);
		}
		finally
		{
			sess.close();
		}
	}

	@SuppressWarnings("unchecked")
	Usuario getUsuario(Session sess, String userName)
	{
		try{
		List<Usuario> result = sess.createQuery("FROM Usuario WHERE login = ?").setString(0, userName).list();
		if (result.size() > 0)
			return result.get(0);
		else
			return null;
		}catch(Exception e){
			assertFalse(false);
			return null;
		}
	}
	
	private Proyecto getProyecto(Session sess, long projectId)
	{
		try{
		List<Proyecto> result = sess.createQuery("FROM Proyecto WHERE id = ?").setLong(0, projectId).list();
		if (result.size() > 0)
			return result.get(0);
		else
			return null;
		}catch(Exception e){
			assertFalse(false);
			return null;
		}
	}

	private long getIdProyecto(Session sess, String nombre){
		try{
		List<Proyecto> result = sess.createQuery("FROM Proyecto WHERE nombre = ?").setString(0, nombre).list();
		if (result.size() > 0)
			return result.get(0).getId();
		else
			return -1;
		}catch(Exception e){
			assertFalse(false);
			return -1;
		}
	}
	
	public void testOfertar(){
		final Session sess = HibernateUtil.getSession();
		try
		{
			OfertaDto o=new OfertaDto();
			o.setDescripcion(descOf);
			o.setDias(dias);
			o.setMoneda(moneda);
			o.setMonto(monto);
			o.setNotificacion(notif);
			assertNotSame(getIdProyecto(sess,nombreProy),-1);
			o.setProyecto(getIdProyecto(sess,nombreProy));
			o.setUsuario(usOf);
			final Proyecto proy = getProyecto(sess, o.getProyecto());
			assertNotNull(proy);
			Usuario us = getUsuario(sess, o.getUsuario());
			assertNotNull(us);
				final Oferta nueva = new Oferta(o, proy, us, buscarMoneda(o.getMoneda()));
				assertTrue(proy.addOferta(nueva));
				assertTrue(us.addOferta(nueva));
					TransactionWrapper.execute(sess, new TransactionWrapper.Action()
					{
						public void execute()
						{
							sess.save(nueva);
							sess.save(proy);
						}
					});
			
		}
		catch (Exception e)
		{
			assertFalse(false);
		}
		sess.close();
	}
	
	
	public void testGetMyAccountData()
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
			Double d=(Double) sess.createQuery("SELECT AVG(califAlComprador.calificacion) FROM Contrato WHERE proyecto.usuario = ?").setParameter(
					0, usuario).uniqueResult();
					if(d!=null)
						comprador.setReputacion(d);	
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
			/*vendedor.setReputacion((Double) sess.createQuery(
					"SELECT AVG(califAlVendedor.calificacion) FROM Contrato WHERE ofertaGanadora.usuario = ?")
					.setParameter(0, usuario).uniqueResult());*/
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
					"SELECT contrato.ofertaGanadora.moneda.description, SUM(contrato.ofertaGanadora.monto) AS monto FROM Proyecto "
							+ "WHERE contrato.ofertaGanadora.usuario = ? AND contrato.califAlComprador IS NOT NULL "
							+ "AND contrato.califAlVendedor IS NOT NULL GROUP BY contrato.ofertaGanadora.moneda.description")
					.setParameter(0, usuario).list();
			Map<Moneda, Long> gananciaAcumulada = new HashMap<Moneda, Long>();
			for (Object[] row : ganancia)
				gananciaAcumulada.put(buscarMoneda((String) row[0]), (Long) row[1]);
			vendedor.setGananciaAcumulada(gananciaAcumulada);
			
			
		}
		finally
		{
			sess.close();
		}
		
	}
	
	public List<Moneda> buscarMonedas(){
		Session sess = HibernateUtil.getSession();

		try
		{
			return (List<Moneda>) sess.createQuery("FROM Moneda").list();
			
		}
		finally
		{
			sess.close();
		}
	}
	
	Moneda buscarMoneda(String nombre,List<Moneda>  monedas){
		for(Moneda m: monedas){
			if(m.getDescription().equals(nombre))
				return m;
		}
		return null;
	}
	
	public void testFilterProject(){
		Session sess = HibernateUtil.getSession();
		String consulta=new String();
		List<Moneda> monedas=buscarMonedas();
		FiltroDto filtro=new FiltroDto();
		filtro.setPresupuestoDesde("4");
		filtro.setMoneda("Peso");
		filtro.setUsuario(login);
		if(filtro.getPresupuestoDesde()!=null&&!filtro.getPresupuestoDesde().isEmpty()){
				consulta+=" AND (";
				int pos=0;
				for(Moneda m: monedas){
					int rango=(int) (Float.parseFloat(filtro.getPresupuestoDesde())*m.getConversion()/(buscarMoneda(filtro.getMoneda(), monedas).getConversion()));
					consulta+="((presupuestoMin >= '"+String.valueOf(rango)+"') AND (moneda.description = '"+m.getDescription()+"'))";
					pos++;
					if(pos!=monedas.size())
						consulta+=" OR ";
				}
				consulta+= ")";
		}
		List<Proyecto> projects = null;
				projects = (List<Proyecto>) sess.createQuery(
						"FROM Proyecto AS proy WHERE proy NOT IN (SELECT proyecto FROM Contrato) "
								+ "AND usuario.login != :us AND cancelado = false"+consulta).setString("us",
						filtro.getUsuario()).list();
		}
	
	
}
