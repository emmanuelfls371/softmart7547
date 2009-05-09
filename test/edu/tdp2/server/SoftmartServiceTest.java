package edu.tdp2.server;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;

import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.model.AbstractDomainObject;
import edu.tdp2.client.model.Contrato;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Pais;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.model.Usuario;
import edu.tdp2.server.db.HibernateUtil;
import edu.tdp2.server.db.TransactionWrapper;
import edu.tdp2.server.db.TransactionWrapper.Action;
import edu.tdp2.server.exceptions.SoftmartServerException;

public class SoftmartServiceTest extends TestCase
{
	private SoftmartServiceImpl impl = new SoftmartServiceImpl();
	private Proyecto y, y2;
	private Pais p;
	private Moneda m;
	private Usuario uComprador, uVendedor;
	private Oferta o, o2;
	private Contrato c;
	private Session sess;

	public SoftmartServiceTest()
	{
		HibernateUtil.getSession().close();
	}

	public void testOfertar()
	{
		final Session sess = HibernateUtil.getSession();
		Oferta nueva = null;
		try
		{
			OfertaDto o = new OfertaDto();
			o.setDescripcion("prueba unitaria");
			o.setDias(10);
			o.setMoneda(m.getDescription());
			o.setMonto(200);
			o.setNotificacion("Si");
			o.setProyecto(y.getId());
			o.setUsuario(uVendedor.getLogin());
			Usuario us = getUsuario(sess, o.getUsuario());
			assertNotNull(us);
			impl.ofertar(o);

			nueva = null;
			List<Oferta> ofertas = impl.getOffers(y);
			for (Oferta oferta : ofertas)
				if (oferta.getDescripcion().equals(o.getDescripcion()))
				{
					nueva = oferta;
					break;
				}
			assertNotNull(nueva);
		}
		catch (SoftmartServerException e)
		{
			assertFalse(false);
		}
		finally
		{
			if (nueva != null)
				TransactionWrapper.delete(sess, (AbstractDomainObject) sess.get(Oferta.class, nueva.getId()));
			sess.close();
		}
	}

	public void testPublicar()
	{
		Session sess = HibernateUtil.getSession();
		Usuario us = getUsuario(sess, "chechu");
		assertNotNull(us);
		ProyectoDto dto = new ProyectoDto();
		dto.setNombre("nombreProy");
		dto.setDescripcion("lalala");
		dto.setPresupuesto("100 a 500");
		dto.setNivel("Normal");
		dto.setTamanio("Chico");
		dto.setDificultad("Simple");
		dto.setMoneda(m.getDescription());
		dto.setFecha(Calendar.getInstance().getTime());
		Proyecto nuevo = new Proyecto(dto, us, buscarMoneda(dto.getMoneda()));
		assertTrue(us.addProyecto(nuevo));
		try
		{
			TransactionWrapper.save(sess, nuevo);
			TransactionWrapper.save(sess, us);
		}
		catch (SoftmartServerException e)
		{
			assertFalse(false);
		}
		finally
		{
			TransactionWrapper.delete(sess, nuevo);
			sess.close();
		}
	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		sess = HibernateUtil.getSession();

		p = new Pais();
		p.setNombre("Zamunda");

		m = new Moneda("Sextercio", 1f);

		uComprador = new Usuario();
		uComprador.setNombre("Nombre");
		uComprador.setApellido("Apellido");
		uComprador.setEmail("email@email.com");
		uComprador.setLogin("Comprador");
		uComprador.setPasswordHash("5f4dcc3b5aa765d61d8327deb882cf99"); // password
		uComprador.setPais(p);
		uComprador.setCiudad("Ciudad gotica");
		uComprador.setCodPostal("012345");

		uVendedor = new Usuario();
		uVendedor.setNombre("Nombre2");
		uVendedor.setApellido("Apellido2");
		uVendedor.setEmail("email@email.com2");
		uVendedor.setLogin("Vendedor");
		uVendedor.setPasswordHash("6cb75f652a9b52798eb6cf2201057c73"); // password2
		uVendedor.setPais(p);
		uVendedor.setCiudad("Ciudad gotica2");
		uVendedor.setCodPostal("012346");

		y = new Proyecto();
		y.setNombre("Nombre");
		y.setMaxPresupuesto(500);
		y.setMinPresupuesto(100);
		y.setFecha(new Date(System.currentTimeMillis()));
		y.setNivel("Normal");
		y.setDificultad("Medio");
		y.setTamanio("Mediano");
		y.setDescripcion("Descripcion");
		y.setMoneda(m);
		y.setUsuario(uComprador);

		y2 = new Proyecto();
		y2.setNombre("Nombre2");
		y2.setMaxPresupuesto(500);
		y2.setMinPresupuesto(100);
		y2.setFecha(new Date(System.currentTimeMillis()));
		y2.setNivel("Premium");
		y2.setDificultad("Simple");
		y2.setTamanio("Chico");
		y2.setDescripcion("Descripcion2");
		y2.setMoneda(m);
		y2.setUsuario(uComprador);

		o = new Oferta();
		o.setMonto(100);
		o.setDias(10);
		o.setDescripcion("Descripcion");
		o.setProyecto(y);
		o.setUsuario(uVendedor);
		o.setNotificacion("Si");
		o.setMoneda(m);

		o2 = new Oferta();
		o2.setMonto(100);
		o2.setDias(10);
		o2.setDescripcion("Descripcion2");
		o2.setProyecto(y);
		o2.setUsuario(uVendedor);
		o2.setNotificacion("Si");
		o2.setMoneda(m);

		c = new Contrato();
		c.setProyecto(y);
		c.setOfertaGanadora(o);
		/*
		 * TransactionWrapper.execute(sess, new Action() { public void execute() { sess.save(p); sess.save(m);
		 * sess.save(uComprador); sess.save(uVendedor); sess.save(y); sess.save(y2); sess.save(o); sess.save(o2);
		 * sess.save(c); } });
		 */
		TransactionWrapper.save(sess, p);
		TransactionWrapper.save(sess, m);
		TransactionWrapper.save(sess, uComprador);
		TransactionWrapper.save(sess, uVendedor);
		TransactionWrapper.save(sess, y);
		TransactionWrapper.save(sess, y2);
		TransactionWrapper.save(sess, o);
		TransactionWrapper.save(sess, o2);
		TransactionWrapper.save(sess, c);
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();

		TransactionWrapper.execute(sess, new Action()
		{
			public void execute()
			{
				sess.refresh(c);
				sess.refresh(o);
				sess.refresh(o2);
				sess.refresh(y);
				sess.refresh(y2);
				sess.refresh(uComprador);
				sess.refresh(uVendedor);
				sess.refresh(p);
				sess.refresh(m);

				sess.delete(c);
				sess.delete(o);
				sess.delete(o2);
				sess.delete(y);
				sess.delete(y2);
				sess.delete(uComprador);
				sess.delete(uVendedor);
				sess.delete(p);
				sess.delete(m);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private Moneda buscarMoneda(String nombre)
	{
		Session sess = HibernateUtil.getSession();

		try
		{
			List<Moneda> monedas = sess.createQuery("FROM Moneda WHERE nombre = ?").setString(0, nombre).list();
			return monedas.get(0);
		}
		finally
		{
			sess.close();
		}
	}

	@SuppressWarnings("unchecked")
	private long getIdProyecto(Session sess, String nombre)
	{
		try
		{
			List<Proyecto> result = sess.createQuery("FROM Proyecto WHERE nombre = ?").setString(0, nombre).list();
			if (result.size() > 0)
				return result.get(0).getId();
			else
				return -1;
		}
		catch (Exception e)
		{
			assertFalse(false);
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	private Proyecto getProyecto(Session sess, long projectId)
	{
		try
		{
			List<Proyecto> result = sess.createQuery("FROM Proyecto WHERE id = ?").setLong(0, projectId).list();
			if (result.size() > 0)
				return result.get(0);
			else
				return null;
		}
		catch (Exception e)
		{
			assertFalse(false);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private Usuario getUsuario(Session sess, String userName)
	{
		try
		{
			List<Usuario> result = sess.createQuery("FROM Usuario WHERE login = ?").setString(0, userName).list();
			if (result.size() > 0)
				return result.get(0);
			else
				return null;
		}
		catch (Exception e)
		{
			assertFalse(false);
			return null;
		}
	}

}
