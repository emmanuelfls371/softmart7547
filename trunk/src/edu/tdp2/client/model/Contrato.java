package edu.tdp2.client.model;

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
	private Calificacion califAlVendedor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CalifComprador")
	private Calificacion califAlComprador;

	public Contrato()
	{

	}

	public Calificacion getCalifAlComprador()
	{
		return califAlComprador;
	}

	public Calificacion getCalifAlVendedor()
	{
		return califAlVendedor;
	}

	public Oferta getOfertaGanadora()
	{
		return ofertaGanadora;
	}

	public Proyecto getProyecto()
	{
		return proyecto;
	}

	public void setCalifAlComprador(Calificacion califComprador)
	{
		califAlComprador = califComprador;
	}

	public void setCalifAlVendedor(Calificacion califVendedor)
	{
		califAlVendedor = califVendedor;
	}

	public void setOfertaGanadora(Oferta ofertaGanadora)
	{
		this.ofertaGanadora = ofertaGanadora;
	}

	public void setProyecto(Proyecto proyecto)
	{
		this.proyecto = proyecto;
	}

}
