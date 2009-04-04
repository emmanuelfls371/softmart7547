package edu.tdp2.client.dto;

import java.io.Serializable;

import com.google.gwt.validation.client.Max;
import com.google.gwt.validation.client.Min;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class CalificacionDto implements IValidatable, Serializable, Dto
{
	private static final long serialVersionUID = -2846072920110853529L;

	@Min(minimum = -1, message = "La calificacion está por debajo del mínimo")
	@Max(maximum = 11, message = "La calificacion supera el máximo")
	private int calificacion;

	private String comentario;

	private String usuario;

	private String proyecto;

	public int getCalificacion()
	{
		return calificacion;
	}

	public void setCalificacion(int calificacion)
	{
		this.calificacion = calificacion;
	}

	public String getComentario()
	{
		return comentario;
	}

	public void setComentario(String comentario)
	{
		this.comentario = comentario;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	public String getProyecto()
	{
		return proyecto;
	}

	public void setProyecto(String proyecto)
	{
		this.proyecto = proyecto;
	}
}