package edu.tdp2.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Ciudad")
public class Ciudad extends AbstractDomainObject
{
	private static final long serialVersionUID = 3734668652120242566L;

	@Column(name = "Nombre", length = 100)
	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Pais", nullable = false)
	private Pais pais;

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public void setPais(Pais pais)
	{
		this.pais = pais;
	}

	public Pais getPais()
	{
		return pais;
	}
}
