package edu.tdp2.client;

public enum TipoCalificacion
{

	Recibida("De"), Hecha("A");

	private TipoCalificacion(String description)
	{
		this.description = description;
	}

	private String description;

	private TipoCalificacion()
	{
		this.description = name();
	}

	public String getDescription()
	{
		return description;
	}

}
