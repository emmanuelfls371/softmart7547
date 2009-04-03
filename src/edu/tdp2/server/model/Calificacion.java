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

import edu.tdp2.client.dto.CalificacionDto;

@Entity
@Table(name = "Calificacion")
public class Calificacion extends AbstractDomainObject {

	@Column(name = "Calificacion", length = 2, nullable = false)
	private int calificacion;
	
	@Column(name = "Comentario")
	private String comentario;

	@OneToOne(fetch = FetchType.LAZY)
	private Contrato contrato;
	
	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Calificacion(){
		
	}
	
	public Calificacion (CalificacionDto dto, Contrato c){
		this.setCalificacion(dto.getCalificacion());
		this.setComentario(dto.getComentario());
		this.contrato=c;
	}

	private void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getComentario() {
		return comentario;
	}

	private void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public int getCalificacion() {
		return calificacion;
	}
	
}
