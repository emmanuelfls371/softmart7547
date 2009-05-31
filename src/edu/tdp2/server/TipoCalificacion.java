package edu.tdp2.server;

public enum TipoCalificacion
{
	Recibida, Hecha;

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
