package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.List;

import edu.tdp2.client.model.Proyecto;

public class MyCompradorAccount extends MySpecificAccount implements Serializable
{
	private static final long serialVersionUID = -739806195271426185L;

	private List<Proyecto> proyectosAbiertos;

	public List<Proyecto> getProyectosAbiertos()
	{
		return proyectosAbiertos;
	}

	public void setProyectosAbiertos(List<Proyecto> proyectosAbiertos)
	{
		this.proyectosAbiertos = proyectosAbiertos;
	}
}