package edu.tdp2.server.model;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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

	/**
	 * Atributos de la entidad que van a omitirse a la hora de realizar equals sobre la entidad
	 * 
	 * @return arreglo de string conteniendo los nombres de las propiedades a omitir
	 */
	public String[] getExcludedFields()
	{
		return new String[0];
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this, getExcludedFields());
	}

	private boolean shouldCompare(Field field)
	{
		String[] toExclude = this.getExcludedFields();
		for (int i = 0; i < toExclude.length; i++)
		{
			String fieldName = toExclude[i];

			if (field.getName().equals(fieldName))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean equals(final Object other)
	{
		if (other instanceof AbstractDomainObject == false)
		{
			return false;
		}
		if (this == other)
		{
			return true;
		}

		try
		{
			AbstractDomainObject o = (AbstractDomainObject) other;

			if (!this.getId().equals(o.getId()))
			{
				return false;
			}

			Field[] fields = this.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
			{
				Field field = fields[i];

				if (this.shouldCompare(field))
				{
					Object a = BeanUtils.getProperty(this, field.getName());
					Object b = BeanUtils.getProperty(other, field.getName());

					if (!a.equals(b))
					{
						return false;
					}
				}
			}
		} catch (Exception e)
		{
			return false;
		}

		return true;

		// AbstractDomainObject rhs = (AbstractDomainObject) other;
		// return (this.getId().equals(rhs.getId()));

		// return EqualsBuilder.reflectionEquals(this, other, getExcludedFields());
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
}
