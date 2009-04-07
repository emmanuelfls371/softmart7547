package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class ProyectoDto implements IValidatable, Serializable, Dto
{

	private static final long serialVersionUID = 111116217892037317L;

	@NotEmpty(message = "Debe ingresar el nombre")
	@NotNull(message = "Debe ingresar el nombre")
	private String nombre;

	@NotEmpty(message = "Debe ingresar el rango de presupuesto")
	@NotNull(message = "Debe ingresar el rango de presupuesto")
	private String presupuesto;

	private Date Fecha;

	@NotEmpty(message = "Debe ingresar el nivel de reputación")
	@NotNull(message = "Debe ingresar el nivel de reputación")
	private String nivel;

	@NotNull(message = "Debe ingresar la dificultad")
	private String dificultad;

	@NotNull(message = "Debe ingresar el tamaño")
	private String tamanio;

	private String descripcion;

	private String archivo;

	private String usuario;
	
	@NotEmpty(message = "Debe ingresar la moneda en que se presupuesta")
	@NotNull(message = "Debe ingresar la moneda en que se presupuesta")
	private String moneda;

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public Date getFecha()
	{
		return Fecha;
	}

	public void setFecha(Date fecha)
	{
		Fecha = fecha;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getPresupuesto()
	{
		return presupuesto;
	}

	public void setPresupuesto(String presupuesto)
	{
		this.presupuesto = presupuesto;
	}

	public String getNivel()
	{
		return nivel;
	}

	public void setNivel(String nivel)
	{
		this.nivel = nivel;
	}

	public String getDificultad()
	{
		return dificultad;
	}

	public void setDificultad(String dificultad)
	{
		this.dificultad = dificultad;
	}

	public String getTamanio()
	{
		return tamanio;
	}

	public void setTamanio(String tamanio)
	{
		this.tamanio = tamanio;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public String getArchivo()
	{
		return archivo;
	}

	public void setArchivo(String archivo)
	{
		this.archivo = archivo;
	}

}
