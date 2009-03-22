package edu.tdp2.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario extends AbstractDomainObject
{
	private static final long serialVersionUID = -4991786482118294352L;

	@Column(name = "Nombre", length = 50)
	private String nombre;
	
	@Column(name = "PasswordHash", length = 32)
	private String passwordHash;

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public void setPasswordHash(String passwordHash)
	{
		this.passwordHash = passwordHash;
	}

	public String getPasswordHash()
	{
		return passwordHash;
	}
}
