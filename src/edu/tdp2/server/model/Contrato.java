package edu.tdp2.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "Contrato")
public class Contrato extends AbstractDomainObject{

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Proyecto", nullable = false)
	private Proyecto proyecto;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Oferta", nullable = false)
	private Oferta ofertaGanadora;
	
	@OneToOne(mappedBy = "contrato", fetch = FetchType.LAZY)
	@JoinColumn(name="CalifVendedor")
	private Calificacion califVendedor;

    @OneToOne(mappedBy = "contrato", fetch = FetchType.LAZY)
    @JoinColumn(name="CalifComprador")
	private Calificacion califComprador;
	
	
	public Contrato(){
		
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Oferta getOfertaGanadora() {
		return ofertaGanadora;
	}

	public void setOfertaGanadora(Oferta ofertaGanadora) {
		this.ofertaGanadora = ofertaGanadora;
	}

	public void setCalifVendedor(Calificacion califVendedor) {
		this.califVendedor = califVendedor;
	}

	public Calificacion getCalifVendedor() {
		return califVendedor;
	}

	public void setCalifComprador(Calificacion califComprador) {
		this.califComprador = califComprador;
	}

	public Calificacion getCalifComprador() {
		return califComprador;
	}
	
}
