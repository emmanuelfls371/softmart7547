package edu.tdp2.client.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import edu.tdp2.client.dto.CalificacionDto;

@Entity
@Table(name = "Calificacion")
public class Calificacion extends AbstractDomainObject
{
	private static final long serialVersionUID = 7230161324210837294L;

	@Column(name = "Calificacion", length = 2, nullable = false)
	private int calificacion;

	@Column(name = "Comentario")
	private String comentario;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "califComprador")
	private Contrato contratoComprador;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "califVendedor")
	private Contrato contratoVendedor;

	public Contrato getContrato()
	{
		if (contratoVendedor == null)
			return contratoComprador;
		return contratoVendedor;
	}

	public void setContrato(Contrato contrato)
	{
		this.contratoVendedor = this.contratoComprador = contrato;
	}

	public Calificacion()
	{
	}

	public Calificacion(CalificacionDto dto, Contrato c)
	{
		this.setCalificacion(dto.getCalificacion());
		this.setComentario(dto.getComentario());
		this.setContrato(c);
	}

	private void setComentario(String comentario)
	{
		this.comentario = comentario;
	}

	public String getComentario()
	{
		return comentario;
	}

	private void setCalificacion(int calificacion)
	{
		this.calificacion = calificacion;
	}

	public int getCalificacion()
	{
		return calificacion;
	}

}
