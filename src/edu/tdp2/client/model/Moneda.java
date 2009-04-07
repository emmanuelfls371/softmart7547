package edu.tdp2.client.model;

public enum Moneda {
	Peso, Dolar, Euro, Yen("Miles de Yenes");
	
	private Moneda(String description)
	{
		this.description = description;
	}

	private String description;

	private Moneda()
	{
		this.description = name();
	}

	public String getDescription()
	{
		return description;
	}
}
