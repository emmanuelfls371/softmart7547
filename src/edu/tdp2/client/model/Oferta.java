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
	private int monto;

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

	public Contrato getContrato()
	{
		return contrato;
	}

	public void setContrato(Contrato contrato)
	{
		this.contrato = contrato;
	}

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

	public Usuario getUsuario()
	{
		return usuario;
	}

	private void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public Proyecto getProyecto()
	{
		return proyecto;
	}

	private void setProyecto(Proyecto proyecto)
	{
		this.proyecto = proyecto;
	}

	public int getMonto()
	{
		return monto;
	}

	private void setMonto(int monto)
	{
		this.monto = monto;
	}

	public int getDias()
	{
		return dias;
	}

	private void setDias(int dias)
	{
		this.dias = dias;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	private void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public String getNotificacion()
	{
		return notificacion;
	}

	private void setNotificacion(String notificacion)
	{
		this.notificacion = notificacion;
	}

	@Override
	public void prune()
	{
		proyecto = null;
		usuario.prune();
		contrato = null;
	}

	private void setMoneda(Moneda moneda)
	{
		this.moneda = moneda;
	}

	public Moneda getMoneda()
	{
		return moneda;
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

}
