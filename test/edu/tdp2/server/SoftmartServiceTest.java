package edu.tdp2.server;

import java.util.List;

import org.hibernate.Session;

import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.model.Usuario;
import edu.tdp2.server.db.HibernateUtil;
import edu.tdp2.server.db.TransactionWrapper;

import junit.framework.TestCase;

public class SoftmartServiceTest extends TestCase
{
	public void testPublicar()
	{
		Session sess = HibernateUtil.getSession();
		Usuario us = getUsuario(sess, "chechu");
		assertNotNull(us);
		ProyectoDto dto = new ProyectoDto();
		dto.setNombre("lala");
		dto.setDescripcion("lalala");
		dto.setPresupuesto("100 a 500");
		dto.setNivel("Normal");
		dto.setTamanio("Chico");
		dto.setDificultad("Simple");
		Proyecto nuevo = new Proyecto(dto, us);
		if (us.addProyecto(nuevo))
		{
			TransactionWrapper.save(sess, nuevo);
			TransactionWrapper.save(sess, us);
		}
	}

	@SuppressWarnings("unchecked")
	Usuario getUsuario(Session sess, String userName)
	{
		List<Usuario> result = sess.createQuery("FROM Usuario WHERE login = ?").setString(0, userName).list();
		if (result.size() > 0)
			return result.get(0);
		else
			return null;
	}
}
