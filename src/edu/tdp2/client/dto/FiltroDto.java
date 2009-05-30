package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.validation.client.interfaces.IValidatable;

public class FiltroDto implements IValidatable, Serializable, Dto
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fechaDesde;
	private Date fechaHasta;
	private String presupuestoDesde;
	private String presupuestoHasta;
	private String moneda;
	private List<String> tamanio;
	private List<String> complejidad;
	private String reputacion;
	private String usuario;

	public FiltroDto()
	{
		complejidad = new ArrayList<String>();
		tamanio = new ArrayList<String>();
	}

	public void addComplejidad(String complejidad)
	{
		this.complejidad.add(complejidad);
	}

	public void addTamanio(String tamanio)
	{
		this.tamanio.add(tamanio);
	}

	public List<String> getComplejidad()
	{
		return complejidad;
	}

	public Date getFechaDesde()
	{
		return fechaDesde;
	}

	public Date getFechaHasta()
	{
		return fechaHasta;
	}

	public String getMoneda()
	{
		return moneda;
	}

	public String getPresupuestoDesde()
	{
		return presupuestoDesde;
	}

	public String getPresupuestoHasta()
	{
		return presupuestoHasta;
	}

	public String getReputacion()
	{
		return reputacion;
	}

	public List<String> getTamanio()
	{
		return tamanio;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setFechaDesde(Date fechaDesde)
	{
		this.fechaDesde = fechaDesde;
	}

	public void setFechaHasta(Date fechaHasta)
	{
		this.fechaHasta = fechaHasta;
	}

	public void setMoneda(String moneda)
	{
		this.moneda = moneda;
	}

	public void setPresupuestoDesde(String presupuestoDesde)
	{
		this.presupuestoDesde = presupuestoDesde;
	}

	public void setPresupuestoHasta(String presupuestoHasta)
	{
		this.presupuestoHasta = presupuestoHasta;
	}

	public void setReputacion(String reputacion)
	{
		this.reputacion = reputacion;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

}
