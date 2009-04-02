package edu.tdp2.server.model;

import java.util.ArrayList;
import java.util.List;

public final class Presupuesto
{
	private static int[] rangos = { 100, 500, 1000, 2000, 5000, 10000, 50000, 100000 };
	private static String separador = " a ";
	private static String rangoFinal = "Mayor a ";
	public static int infinito = -1;

	public static List<String> armarRangos()
	{
		List<String> lista = new ArrayList<String>();
		for (int i = 0; i < rangos.length; i++)
		{
			String rango = new String();
			if (i + 1 != rangos.length)
			{
				rango = rango.concat(String.valueOf(rangos[i]));
				rango = rango.concat(separador);
				rango = rango.concat(String.valueOf(rangos[i + 1]));

				lista.add(rango);
			}
		}
		String rango = new String();
		rango = rango.concat(rangoFinal);
		rango = rango.concat(String.valueOf(rangos[rangos.length - 1]));
		lista.add(rango);
		return lista;
	}

	public static List<Integer> desarmarRango(String rango)
	{
		if (rango != null)
			if (rango.contains(rangoFinal))
			{
				String resultados[] = rango.split(rangoFinal);
				List<Integer> lista = new ArrayList<Integer>();
				if (Integer.parseInt(resultados[1]) == rangos[rangos.length - 1])
				{
					try
					{
						lista.add(Integer.parseInt(resultados[1]));
					}
					catch (NumberFormatException e)
					{
						return null;
					}
					lista.add(infinito);
					return lista;
				}
			}
			else if (rango.contains(separador))
			{
				String[] resultados = rango.split(separador);
				List<Integer> lista = new ArrayList<Integer>();
				for (String result : resultados)
					try
					{
						lista.add(Integer.parseInt(result));
					}
					catch (NumberFormatException e)
					{
						return null;
					}
				return lista;
			}
		return null;
	}
}
