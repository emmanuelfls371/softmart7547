package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Proyecto;

public class MyVendedorAccount extends MySpecificAccount implements Serializable
{
	private static final long serialVersionUID = -6866869107363384714L;

	private Map<Moneda, Long> gananciaAcumulada;
	private List<Proyecto> proyectosConOfertasAbiertas;

	public Map<Moneda, Long> getGananciaAcumulada()
	{
		return gananciaAcumulada;
	}

	public void setGananciaAcumulada(Map<Moneda, Long> gananciaAcumulada)
	{
		this.gananciaAcumulada = gananciaAcumulada;
	}

	public List<Proyecto> getProyectosConOfertasAbiertas()
	{
		return proyectosConOfertasAbiertas;
	}

	public void setProyectosConOfertasAbiertas(List<Proyecto> ofertasAbiertas)
	{
		proyectosConOfertasAbiertas = prune(ofertasAbiertas);
	}
}