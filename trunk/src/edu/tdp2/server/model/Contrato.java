package edu.tdp2.server.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Contrato")
public class Contrato extends AbstractDomainObject
{
	private static final long serialVersionUID = -4794226795305191623L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Proyecto", nullable = false)
	private Proyecto proyecto;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Oferta", nullable = false)
	private Oferta ofertaGanadora;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CalifVendedor")
	private Calificacion califVendedor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CalifComprador")
	private Calificacion califComprador;

	public Contrato()
	{

	}

	public Proyecto getProyecto()
	{
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto)
	{
		this.proyecto = proyecto;
	}

	public Oferta getOfertaGanadora()
	{
		return ofertaGanadora;
	}

	public void setOfertaGanadora(Oferta ofertaGanadora)
	{
		this.ofertaGanadora = ofertaGanadora;
	}

	public void setCalifVendedor(Calificacion califVendedor)
	{
		this.califVendedor = califVendedor;
	}

	public Calificacion getCalifVendedor()
	{
		return califVendedor;
	}

	public void setCalifComprador(Calificacion califComprador)
	{
		this.califComprador = califComprador;
	}

	public Calificacion getCalifComprador()
	{
		return califComprador;
	}

}
