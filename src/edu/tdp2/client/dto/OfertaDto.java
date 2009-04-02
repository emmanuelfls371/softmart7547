package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class OfertaDto implements IValidatable, Serializable,Dto{

	private static final long serialVersionUID = 111116217892037317L;
	

	private int monto;


	private int dias;
	
	
	@NotNull (message = "Debe ingresar la notificacion")
	private String notificacion;
	
	private String Descripcion;

	private String proyecto;
	
	private String usuario;
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public String getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}
	
}
