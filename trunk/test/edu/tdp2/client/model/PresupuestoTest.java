package edu.tdp2.client.model;

import java.util.List;

import junit.framework.TestCase;

public class PresupuestoTest extends TestCase
{
	private static String j[] = { "100 a 500", "500 a 1000", "1000 a 2000", "2000 a 5000", "5000 a 10000",
			"10000 a 50000", "50000 a 100000", "Mayor a 100000" };

	public void testArmarRangos()
	{
		List<String> lista = Presupuesto.armarRangos(new Float(1));
		assertNotNull(lista);
		int pos = 0;
		for (String i : lista)
		{
			assertNotNull(i);
			assertTrue(i.compareTo(j[pos]) == 0);
			pos++;
		}
	}

	public void testDesarmarRango()
	{
		List<Integer> lista = Presupuesto.desarmarRango("100 a 500");
		assertEquals(100, lista.get(0).intValue());
		assertEquals(500, lista.get(1).intValue());
		lista = Presupuesto.desarmarRango("Mayor a 100");
		assertNull(lista);
		lista = Presupuesto.desarmarRango("Mayor a 100000");
		assertEquals(100000, lista.get(0).intValue());
		assertEquals(-1, lista.get(1).intValue());
		lista = Presupuesto.desarmarRango("100a 500");
		assertNull(lista);
		lista = Presupuesto.desarmarRango("100 a s");
		assertNull(lista);
	}
}
