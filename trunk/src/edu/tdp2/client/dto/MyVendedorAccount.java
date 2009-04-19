package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Proyecto;

public class MyVendedorAccount extends MySpecificAccount implements Serializable
{
	private static final long serialVersionUID = -6866869107363384714L;

	private HashMap<Moneda, Float> gananciaAcumulada;
	private List<Proyecto> proyectosConOfertasAbiertas;

	public HashMap<Moneda, Float> getGananciaAcumulada()
	{
		return gananciaAcumulada;
	}

	public void setGananciaAcumulada(HashMap<Moneda, Float> gananciaAcumulada)
	{
		this.gananciaAcumulada = gananciaAcumulada;
	}

	public List<Proyecto> getProyectosConOfertasAbiertas()
	{
		return proyectosConOfertasAbiertas;
	}

	public void setProyectosConOfertasAbiertas(List<Proyecto> ofertasAbiertas)
	{
		this.proyectosConOfertasAbiertas = ofertasAbiertas;
	}
}