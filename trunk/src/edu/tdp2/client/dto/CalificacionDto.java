package edu.tdp2.client.dto;

import java.io.Serializable;

import com.google.gwt.validation.client.Max;
import com.google.gwt.validation.client.Min;
import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class CalificacionDto implements IValidatable, Serializable,Dto{
	
	@Min(minimum=-1, message = "La calificacion est� por debajo del m�nimo")
	@Max(maximum=11, message = "La calificacion supera el m�ximo")
	private int calificacion;
	
	private String comentario;
	
	private String usuario;
	
	private String proyecto;

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}

}
