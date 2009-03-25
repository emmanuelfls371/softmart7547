package edu.tdp2.client.dto;

import java.io.Serializable;

import com.google.gwt.validation.client.Email;
import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

import edu.tdp2.client.utils.MD5;

public class UsuarioDto implements IValidatable, Serializable
{
	private static final long serialVersionUID = 3538926217892037317L;

	@NotEmpty(message = "Debe ingresar el nombre")
	@NotNull
	private String nombre;

	@NotEmpty(message = "Debe ingresar el apellido")
	@NotNull
	private String apellido;

	@NotEmpty(message = "Debe ingresar el email")
	@NotNull
	@Email(message = "El email no tiene el formato adecuado")
	private String email;

	@NotEmpty(message = "Debe ingresar el nombre de usuario")
	@NotNull
	private String usuario;

	// En realidad solo se guarda el hash de la clave, no la clave
	@NotEmpty(message = "Debe ingresar la clave")
	@NotNull
	private String clave;

	@NotEmpty(message = "Debe ingresar el país")
	@NotNull
	private String pais;

	@NotEmpty(message = "Debe ingresar la ciudad")
	@NotNull
	private String ciudad;

	@NotEmpty(message = "Debe ingresar el código postal")
	@NotNull
	private String codPostal;

	private String descripPerfil;

	private String logo;

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getApellido()
	{
		return apellido;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	public String getClave()
	{
		return clave;
	}

	public void setClave(String clave)
	{
		this.clave = clave.isEmpty() ? "" : MD5.md5(clave);
	}

	public String getPais()
	{
		return pais;
	}

	public void setPais(String pais)
	{
		this.pais = pais;
	}

	public String getCiudad()
	{
		return ciudad;
	}

	public void setCiudad(String ciudad)
	{
		this.ciudad = ciudad;
	}

	public String getCodPostal()
	{
		return codPostal;
	}

	public void setCodPostal(String codigoPostal)
	{
		this.codPostal = codigoPostal;
	}

	public String getDescripPerfil()
	{
		return descripPerfil;
	}

	public void setDescripPerfil(String descripPerfil)
	{
		this.descripPerfil = descripPerfil;
	}

	public String getLogo()
	{
		return logo;
	}

	public void setLogo(String logo)
	{
		this.logo = logo;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getEmail()
	{
		return email;
	}
}
