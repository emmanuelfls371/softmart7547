package edu.tdp2.client.dto;

import java.io.Serializable;

import com.google.gwt.validation.client.interfaces.IValidatable;

import edu.tdp2.client.model.NivelReputacion;

public class MyAccountDto implements Serializable, Dto, IValidatable
{
	private static final long serialVersionUID = 2281603093697764090L;

	private NivelReputacion nivel;
	private String nombre;
	private String apellido;
	private String pais;
	private String ciudad;
	private String email;
	private String usuario;
	private String codigoPostal;
	private String descripcion;
	private MyVendedorAccount datosVendedor = new MyVendedorAccount();
	private MyCompradorAccount datosComprador = new MyCompradorAccount();

	public String getApellido()
	{
		return apellido;
	}

	public String getCiudad()
	{
		return ciudad;
	}

	public String getCodigoPostal()
	{
		return codigoPostal;
	}

	public MyCompradorAccount getDatosComprador()
	{
		return datosComprador;
	}

	public MyVendedorAccount getDatosVendedor()
	{
		return datosVendedor;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public String getEmail()
	{
		return email;
	}

	public NivelReputacion getNivel()
	{
		return nivel;
	}

	public String getNombre()
	{
		return nombre;
	}

	public String getPais()
	{
		return pais;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public void setCiudad(String ciudad)
	{
		this.ciudad = ciudad;
	}

	public void setCodigoPostal(String codigoPostal)
	{
		this.codigoPostal = codigoPostal;
	}

	public void setDatosComprador(MyCompradorAccount datosComprador)
	{
		this.datosComprador = datosComprador;
	}

	public void setDatosVendedor(MyVendedorAccount datosVendedor)
	{
		this.datosVendedor = datosVendedor;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setNivel(NivelReputacion nivel)
	{
		this.nivel = nivel;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public void setPais(String pais)
	{
		this.pais = pais;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}
}
