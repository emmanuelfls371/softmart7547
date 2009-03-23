package edu.tdp2.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario extends AbstractDomainObject
{
	private static final long serialVersionUID = -4991786482118294352L;

	@Column(name = "Nombre", length = 50)
	private String nombre;
	
	@Column(name = "Apellido", length = 50)
	private String apellido;

	@Column(name = "Email", length = 255)
	private String email;
	
	@Column(name = "Login", length = 50)
	private String login;
	
	@Column(name = "PasswordHash", length = 32)
	private String passwordHash;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Ciudad", nullable = false)
	private Ciudad ciudad;
	
	@Column(name = "CodPostal", length = 10)
	private String codPostal;
	
	@Column(name = "DescripPerfil")
	private String descripPerfil;

	@Column(name = "PathLogo", length = 255)
	private String pathLogo;
	
	public String getApellido()
	{
		return apellido;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public Ciudad getCiudad()
	{
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad)
	{
		this.ciudad = ciudad;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public void setPasswordHash(String passwordHash)
	{
		this.passwordHash = passwordHash;
	}

	public String getPasswordHash()
	{
		return passwordHash;
	}

	public void setCodPostal(String codPostal)
	{
		this.codPostal = codPostal;
	}

	public String getCodPostal()
	{
		return codPostal;
	}

	public void setDescripPerfil(String descripPerfil)
	{
		this.descripPerfil = descripPerfil;
	}

	public String getDescripPerfil()
	{
		return descripPerfil;
	}

	public void setPathLogo(String pathLogo)
	{
		this.pathLogo = pathLogo;
	}

	public String getPathLogo()
	{
		return pathLogo;
	}
}
