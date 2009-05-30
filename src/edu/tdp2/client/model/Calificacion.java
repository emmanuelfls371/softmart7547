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

	@Column(name = "Calificacion", nullable = false)
	private int calificacion;

	@Column(name = "Comentario")
	private String comentario;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "califAlComprador")
	private Contrato contratoComprador;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "califAlVendedor")
	private Contrato contratoVendedor;

	public Calificacion()
	{
	}

	public Calificacion(CalificacionDto dto, Contrato c)
	{
		setCalificacion(dto.getCalificacion());
		setComentario(dto.getComentario());
		setContrato(c);
	}

	public int getCalificacion()
	{
		return calificacion;
	}

	public String getComentario()
	{
		return comentario;
	}

	public Contrato getContrato()
	{
		if (contratoVendedor == null)
			return contratoComprador;
		return contratoVendedor;
	}

	public void setContrato(Contrato contrato)
	{
		contratoVendedor = contratoComprador = contrato;
	}

	private void setCalificacion(int calificacion)
	{
		this.calificacion = calificacion;
	}

	private void setComentario(String comentario)
	{
		this.comentario = comentario;
	}

}
