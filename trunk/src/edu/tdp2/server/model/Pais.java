package edu.tdp2.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Pais")
public class Pais extends AbstractDomainObject
{
	private static final long serialVersionUID = 3734668652120242566L;

	@Column(name = "Nombre", length = 50)
	private String nombre;

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
}
