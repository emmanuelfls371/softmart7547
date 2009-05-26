package edu.tdp2.client.dto;

import java.io.Serializable;

import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.model.Usuario;

public class OfertaDto implements IValidatable, Serializable, Dto
{
	private static final long serialVersionUID = 111116217892037317L;

	private float monto;

	private int dias;

	@NotNull(message = "Debe ingresar la notificacion")
	private String notificacion;

	private String Descripcion;

	private long proyecto;

	private String usuario;

	@NotEmpty(message = "Debe ingresar la moneda en que se oferta")
	@NotNull(message = "Debe ingresar la moneda en que se oferta")
	private String moneda;

	public String getUsuario()
	{
		return usuario;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	public String getDescripcion()
	{
		return Descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		Descripcion = descripcion;
	}

	public long getProyecto()
	{
		return proyecto;
	}

	public void setProyecto(long projectId)
	{
		proyecto = projectId;
	}

	public float getMonto()
	{
		return monto;
	}

	public void setMonto(float monto)
	{
		this.monto = monto;
	}

	public int getDias()
	{
		return dias;
	}

	public void setDias(int dias)
	{
		this.dias = dias;
	}

	public String getNotificacion()
	{
		return notificacion;
	}

	public void setNotificacion(String notificacion)
	{
		this.notificacion = notificacion;
	}

	public void setMoneda(String moneda)
	{
		this.moneda = moneda;
	}

	public String getMoneda()
	{
		return moneda;
	}

	public static OfertaDto fromOferta(Oferta oferta)
	{
		OfertaDto dto = new OfertaDto();
		dto.monto = oferta.getMonto();
		dto.dias = oferta.getDias();
		dto.notificacion = oferta.getNotificacion();
		dto.Descripcion = oferta.getDescripcion();

		Proyecto proy = oferta.getProyecto();
		if (proy != null)
			dto.proyecto = proy.getId();

		Usuario usr = oferta.getUsuario();
		if (usr != null)
			dto.usuario = usr.getLogin();

		Moneda mon = oferta.getMoneda();
		if (mon != null)
			dto.moneda = mon.getDescription();
		return dto;
	}
}
