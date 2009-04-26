package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.validation.client.interfaces.IValidatable;

public class FiltroDto implements IValidatable, Serializable, Dto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fechaDesde;
	private Date fechaHasta;
	private String presupuestoDesde;
	private String presupuestoHasta;
	private String moneda;
	private String tamanio;
	private String complejidad;
	private String reputacion;
	private String usuario;
	
	public String getPresupuestoDesde() {
		return presupuestoDesde;
	}
	public void setPresupuestoDesde(String presupuestoDesde) {
		this.presupuestoDesde = presupuestoDesde;
	}
	public String getPresupuestoHasta() {
		return presupuestoHasta;
	}
	public void setPresupuestoHasta(String presupuestoHasta) {
		this.presupuestoHasta = presupuestoHasta;
	}
		
	public Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getTamanio() {
		return tamanio;
	}
	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}
	public String getReputacion() {
		return reputacion;
	}
	public void setReputacion(String reputacion) {
		this.reputacion = reputacion;
	}
	public void setComplejidad(String complejidad) {
		this.complejidad = complejidad;
	}
	public String getComplejidad() {
		return complejidad;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getUsuario() {
		return usuario;
	}
	
	
}
