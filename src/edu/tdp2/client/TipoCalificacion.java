package edu.tdp2.client;

public enum TipoCalificacion
{

	Recibida("De"), Hecha("A");

	private String description;

	private TipoCalificacion()
	{
		description = name();
	}

	private TipoCalificacion(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

}
