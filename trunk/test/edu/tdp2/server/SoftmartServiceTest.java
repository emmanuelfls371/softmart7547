package edu.tdp2.server;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;

import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.model.Usuario;
import edu.tdp2.server.db.HibernateUtil;
import edu.tdp2.server.db.TransactionWrapper;

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
		Proyecto nuevo = new Proyecto(dto, us);
		assertTrue(us.addProyecto(nuevo));
			try{
				TransactionWrapper.save(sess, nuevo);
				TransactionWrapper.save(sess, us);
			}catch(Exception e){
				assertFalse(false);
			}
		sess.close();
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
				final Oferta nueva = new Oferta(o, proy, us);
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
}
