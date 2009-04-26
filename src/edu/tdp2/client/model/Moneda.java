package edu.tdp2.client.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "Moneda")
public class Moneda  extends AbstractDomainObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "Nombre", nullable = false)
	private String description;
	@Column(name = "Conversion", nullable = false)
	private float conversion;
	
	public Moneda(){
		
	}
	
	public Moneda(String description, float conversion)
	{
		this.description = description;
		this.conversion=conversion;
	}

	public float getConversion() {
		return conversion;
	}

	public String getDescription()
	{
		return description;
	}
}
