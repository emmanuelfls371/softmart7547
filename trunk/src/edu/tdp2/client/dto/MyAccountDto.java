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

	public NivelReputacion getNivel()
	{
		return nivel;
	}

	public void setNivel(NivelReputacion nivel)
	{
		this.nivel = nivel;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
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

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	public MyVendedorAccount getDatosVendedor()
	{
		return datosVendedor;
	}

	public MyCompradorAccount getDatosComprador()
	{
		return datosComprador;
	}

	public void setDatosVendedor(MyVendedorAccount datosVendedor)
	{
		this.datosVendedor = datosVendedor;
	}

	public void setDatosComprador(MyCompradorAccount datosComprador)
	{
		this.datosComprador = datosComprador;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public String getApellido()
	{
		return apellido;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setCodigoPostal(String codigoPostal)
	{
		this.codigoPostal = codigoPostal;
	}

	public String getCodigoPostal()
	{
		return codigoPostal;
	}
}
