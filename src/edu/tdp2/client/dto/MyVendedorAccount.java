package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Proyecto;

public class MyVendedorAccount extends MySpecificAccount implements Serializable
{
	private static final long serialVersionUID = -6866869107363384714L;

	private Map<Moneda, Double> gananciaAcumulada;
	private List<Proyecto> proyectosPerdidos;
	private List<Proyecto> proyectosAdjudicados;

	public Map<Moneda, Double> getGananciaAcumulada()
	{
		return gananciaAcumulada;
	}

	public void setGananciaAcumulada(Map<Moneda, Double> gananciaAcumulada)
	{
		this.gananciaAcumulada = gananciaAcumulada;
	}

	public void setProyectosPerdidos(List<Proyecto> proyectosPerdidos)
	{
		this.proyectosPerdidos = proyectosPerdidos;
	}

	public List<Proyecto> getProyectosPerdidos()
	{
		return proyectosPerdidos;
	}

	public void setProyectosAdjudicados(List<Proyecto> proyectosAdjudicados)
	{
		this.proyectosAdjudicados = proyectosAdjudicados;
	}

	public List<Proyecto> getProyectosAdjudicados()
	{
		return proyectosAdjudicados;
	}
}