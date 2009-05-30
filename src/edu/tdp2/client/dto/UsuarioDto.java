package edu.tdp2.client.dto;

import java.io.Serializable;

import com.google.gwt.validation.client.Email;
import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

import edu.tdp2.client.utils.MD5;

public class UsuarioDto implements IValidatable, Serializable, Dto
{
	private static final long serialVersionUID = 3538926217892037317L;

	@NotEmpty(message = "Debe ingresar el nombre")
	@NotNull
	private String nombre;

	@NotEmpty(message = "Debe ingresar el apellido")
	@NotNull
	private String apellido;

	@NotEmpty(message = "Debe ingresar el email")
	@Email(message = "El email no tiene el formato adecuado")
	private String email;

	@NotEmpty(message = "Debe ingresar el nombre de usuario")
	@NotNull
	private String usuario;

	@NotEmpty(message = "Debe ingresar la clave")
	// En realidad solo se guarda el hash de la clave, no la clave
	private String clave;

	@NotEmpty(message = "Debe ingresar el pa�s")
	@NotNull
	private Serializable pais;

	@NotEmpty(message = "Debe ingresar la ciudad")
	@NotNull
	private String ciudad;

	@NotEmpty(message = "Debe ingresar el c�digo postal")
	@NotNull
	private String codPostal;

	private String descripPerfil;

	private String logo;

	public String getApellido()
	{
		return apellido;
	}

	public String getCiudad()
	{
		return ciudad;
	}

	public String getClave()
	{
		return clave;
	}

	public String getCodPostal()
	{
		return codPostal;
	}

	public String getDescripPerfil()
	{
		return descripPerfil;
	}

	public String getEmail()
	{
		return email;
	}

	public String getLogo()
	{
		return logo;
	}

	public String getNombre()
	{
		return nombre;
	}

	public Serializable getPais()
	{
		return pais;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public void setCiudad(String ciudad)
	{
		this.ciudad = ciudad;
	}

	public void setClave(String clave)
	{
		this.clave = clave.isEmpty() ? "" : MD5.md5(clave);
	}

	public void setCodPostal(String codigoPostal)
	{
		codPostal = codigoPostal;
	}

	public void setDescripPerfil(String descripPerfil)
	{
		this.descripPerfil = descripPerfil;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setLogo(String logo)
	{
		this.logo = logo;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public void setPais(Serializable pais)
	{
		this.pais = pais;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}
}
