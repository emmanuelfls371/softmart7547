package edu.tdp2.client.dto;

import java.io.Serializable;

import com.google.gwt.validation.client.Max;
import com.google.gwt.validation.client.Min;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class CalificacionDto implements IValidatable, Serializable, Dto
{
	private static final long serialVersionUID = -2846072920110853529L;

	@Min(minimum = -1, message = "La calificacion est� por debajo del m�nimo")
	@Max(maximum = 11, message = "La calificacion supera el m�ximo")
	private int calificacion;

	private String comentario;

	private String usuario;

	private long proyecto;

	public int getCalificacion()
	{
		return calificacion;
	}

	public String getComentario()
	{
		return comentario;
	}

	public long getProyecto()
	{
		return proyecto;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setCalificacion(int calificacion)
	{
		this.calificacion = calificacion;
	}

	public void setComentario(String comentario)
	{
		this.comentario = comentario;
	}

	public void setProyecto(long proyecto)
	{
		this.proyecto = proyecto;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}
}