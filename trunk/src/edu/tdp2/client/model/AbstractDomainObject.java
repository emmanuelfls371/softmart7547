package edu.tdp2.client.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Clase padre de todas las entidades del modelo.<br>
 * Se gana en simplicidad y en manejo de versionado para todas las clases como asi tambien identificacion.<br>
 * 
 * Para mas informacion: {@link http://martinfowler.com/eaaCatalog/layerSupertype.html layer supertype}
 */
@MappedSuperclass
public abstract class AbstractDomainObject implements Serializable
{
	private static final long serialVersionUID = 537831535416416425L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Version
	@Column(name = "VERSION")
	protected Long version;

	@Override
	public boolean equals(final Object other)
	{
		AbstractDomainObject rhs = (AbstractDomainObject) other;
		return (this.getId().equals(rhs.getId()));
	}

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public Long getVersion()
	{
		return this.version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Long version)
	{
		this.version = version;
	}

	/**
	 * Setea a <tt>null</tt> el valor de todas las variables que sean de tipos de Hibernate y tengan lazy fetch para
	 * evitar un error de seguridad al devolver el objeto a traves del response RPC
	 */
	public void prune()
	{
	}
}
