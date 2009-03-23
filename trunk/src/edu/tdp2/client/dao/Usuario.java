package edu.tdp2.client.dao;

import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class Usuario implements IValidatable
{
	@NotEmpty
	@NotNull
	private String nombre;

	@NotEmpty
	@NotNull
	private String apellido;

	@NotEmpty
	@NotNull
	private String usuario;

	@NotEmpty
	@NotNull
	private String clave;

	@NotEmpty
	@NotNull
	private String pais;

	@NotEmpty
	@NotNull
	private String ciudad;

	@NotEmpty
	@NotNull
	private String codigoPostal;

	@NotEmpty
	@NotNull
	private String descripPerfil;

	@NotEmpty
	@NotNull
	private String logo;

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getApellido()
	{
		return apellido;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	public String getClave()
	{
		return clave;
	}

	public void setClave(String clave)
	{
		this.clave = clave;
	}

	public String getPais()
	{
		return pais;
	}

	public void setPais(String pais)
	{
		this.pais = pais;
	}

	public String getCiudad()
	{
		return ciudad;
	}

	public void setCiudad(String ciudad)
	{
		this.ciudad = ciudad;
	}

	public String getCodigoPostal()
	{
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal)
	{
		this.codigoPostal = codigoPostal;
	}

	public String getDescripPerfil()
	{
		return descripPerfil;
	}

	public void setDescripPerfil(String descripPerfil)
	{
		this.descripPerfil = descripPerfil;
	}

	public String getLogo()
	{
		return logo;
	}

	public void setLogo(String logo)
	{
		this.logo = logo;
	}
}
