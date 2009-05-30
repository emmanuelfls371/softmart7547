package edu.tdp2.client.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import edu.tdp2.client.dto.OfertaDto;

@Entity
@Table(name = "Oferta")
public class Oferta extends AbstractDomainObject
{

	private static final long serialVersionUID = -4981789655579048475L;

	@Column(name = "Monto", nullable = false)
	private float monto;

	@Column(name = "CantDias", nullable = false)
	private int dias;

	@Column(name = "Descripcion")
	private String descripcion;

	@Column(name = "Notificacion", nullable = false)
	private String notificacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Proyecto", nullable = false)
	private Proyecto proyecto;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Usuario", nullable = false)
	private Usuario usuario;

	@OneToOne(mappedBy = "ofertaGanadora", fetch = FetchType.LAZY)
	private Contrato contrato;

	@ManyToOne()
	@JoinColumn(name = "Moneda", nullable = false)
	private Moneda moneda;

	public Oferta()
	{

	}

	public Oferta(OfertaDto dto, Proyecto proyecto, Usuario us, Moneda moneda)
	{
		setMonto(dto.getMonto());
		setDias(dto.getDias());
		setDescripcion(dto.getDescripcion());
		setNotificacion(dto.getNotificacion());
		setProyecto(proyecto);
		setUsuario(us);
		setMoneda(moneda);
	}

	/** Compara ofertas de un mismo proyecto */
	public boolean compare(Oferta o)
	{

		if (o.getDias() != getDias())
			return false;
		if (o.getMonto() != getMonto())
			return false;
		if (!o.getMoneda().equals(getMoneda()))
			return false;
		if (o.getUsuario().getId().longValue() != getUsuario().getId().longValue())
			return false;

		return true;

	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj != null && obj instanceof Oferta)
		{
			Oferta o = (Oferta) obj;
			if (o.getDias() != getDias())
				return false;
			if (o.getMonto() != getMonto())
				return false;
			if (!o.getMoneda().equals(getMoneda()))
				return false;
			if (o.getUsuario().getId() != getUsuario().getId())
				return false;
			if (o.getProyecto().getId() != getProyecto().getId())
				return false;
			return true;
		}
		else
			return false;
	}

	public Contrato getContrato()
	{
		return contrato;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public int getDias()
	{
		return dias;
	}

	public Moneda getMoneda()
	{
		return moneda;
	}

	public float getMonto()
	{
		return monto;
	}

	public String getNotificacion()
	{
		return notificacion;
	}

	public Proyecto getProyecto()
	{
		return proyecto;
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	@Override
	public void prune()
	{
		proyecto = null;
		usuario.prune();
		contrato = null;
	}

	public void setContrato(Contrato contrato)
	{
		this.contrato = contrato;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public void setDias(int dias)
	{
		this.dias = dias;
	}

	public void setMoneda(Moneda moneda)
	{
		this.moneda = moneda;
	}

	public void setMonto(float monto)
	{
		this.monto = monto;
	}

	public void setNotificacion(String notificacion)
	{
		this.notificacion = notificacion;
	}

	public void setProyecto(Proyecto proyecto)
	{
		this.proyecto = proyecto;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}
}