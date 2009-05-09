package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.List;

import edu.tdp2.client.model.Proyecto;

public abstract class MySpecificAccount implements Serializable
{
	private static final long serialVersionUID = -1998544287124768279L;

	private double reputacion;

	/**
	 * Como comprador: Total de ofertas aceptadas sin recibir calificación.
	 * <p>
	 * Como vendedor: Total de proyectos adjudicados sin recibir calificación.
	 */
	private List<Proyecto> proyectosSinRecibirCalif;

	/**
	 * Como comprador: Ofertantes adjudicados pendientes a calificar.
	 * <p>
	 * Como vendedor: Pendientes a calificar
	 */
	private List<Proyecto> proyectosSinCalificar;

	private List<Proyecto> proyectosCerrados;
	private List<Proyecto> proyectosCancelados;

	public double getReputacion()
	{
		return reputacion;
	}

	public void setReputacion(double reputacion)
	{
		this.reputacion = reputacion;
	}

	public List<Proyecto> getProyectosSinRecibirCalif()
	{
		return proyectosSinRecibirCalif;
	}

	public void setProyectosSinRecibirCalif(List<Proyecto> proyectosSinRecibirCalif)
	{
		this.proyectosSinRecibirCalif = prune(proyectosSinRecibirCalif);
	}

	public List<Proyecto> getProyectosSinCalificar()
	{
		return proyectosSinCalificar;
	}

	public void setProyectosSinCalificar(List<Proyecto> contratosSinCalificar)
	{
		proyectosSinCalificar = prune(contratosSinCalificar);
	}

	public List<Proyecto> getProyectosCerrados()
	{
		return proyectosCerrados;
	}

	public void setProyectosCerrados(List<Proyecto> proyectosCerrados)
	{
		this.proyectosCerrados = prune(proyectosCerrados);
	}

	public List<Proyecto> getProyectosCancelados()
	{
		return proyectosCancelados;
	}

	public void setProyectosCancelados(List<Proyecto> proyectosCancelados)
	{
		this.proyectosCancelados = prune(proyectosCancelados);
	}

	protected List<Proyecto> prune(List<Proyecto> proyectos)
	{
		for (Proyecto proyecto : proyectos)
			proyecto.prune();
		return proyectos;
	}
}