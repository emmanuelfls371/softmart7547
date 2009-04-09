package edu.tdp2.client.dto;

import java.io.Serializable;

import com.google.gwt.validation.client.interfaces.IValidatable;



public class ContratoDto implements IValidatable, Serializable, Dto
{
	private static final long serialVersionUID = -2846072920110853529L;
	
	private long idContrato;
	
	private ProyectoDto proyecto;
	
	private OfertaDto oferta;
	
	private CalificacionDto calif;


	public long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}

	public ProyectoDto getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoDto proyecto) {
		this.proyecto = proyecto;
	}

	public CalificacionDto getCalif() {
		return calif;
	}

	public void setCalif(CalificacionDto calif) {
		this.calif = calif;
	}

	public void setOferta(OfertaDto oferta) {
		this.oferta = oferta;
	}

	public OfertaDto getOferta() {
		return oferta;
	}


}
