package edu.tdp2.server.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Session;

import edu.tdp2.client.dto.UsuarioDto;

@Entity
@Table(name = "Usuario")
public class Usuario extends AbstractDomainObject
{
	private static final long serialVersionUID = -4991786482118294352L;

	@Column(name = "Nombre", length = 50, nullable = false)
	private String nombre;

	@Column(name = "Apellido", length = 50, nullable = false)
	private String apellido;

	@Column(name = "Email", length = 255, nullable = false)
	private String email;

	@Column(name = "Login", length = 50, nullable = false)
	private String login;

	@Column(name = "PasswordHash", length = 32, nullable = false)
	private String passwordHash;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Ciudad")
	private Ciudad ciudad;

	@Column(name = "CodPostal", length = 10, nullable = false)
	private String codPostal;

	@Column(name = "DescripPerfil")
	private String descripPerfil;

	@Column(name = "PathLogo", length = 255)
	private String pathLogo;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Proyecto> proyectos;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Oferta> ofertas;
	
	public boolean existe(Oferta of)
	{
		if (of != null)
		{
			for (Oferta oferta : ofertas)
				if (oferta.id == of.id)
					return true;
			return false;
		}
		else
			return false;
	}

	public boolean addOferta(Oferta of)
	{
		if (of != null && !existe(of))
		{
			ofertas.add(of);
			return true;
		}
		else
			return false;
	}

	public boolean existe(Proyecto proy)
	{
		if (proy != null)
		{
			for (Proyecto proyecto : proyectos)
				if (proyecto.id == proy.id)
					return true;
			return false;
		}
		else
			return false;
	}

	public boolean addProyecto(Proyecto proy)
	{
		if (proy != null && !existe(proy))
		{
			proyectos.add(proy);
			return true;
		}
		else
			return false;
	}

	public Usuario()
	{
	}

	public Usuario(UsuarioDto dto, Session sess)
	{
		nombre = dto.getNombre();
		apellido = dto.getApellido();
		email = dto.getEmail();
		login = dto.getUsuario();
		if (sess.createQuery("FROM Usuario WHERE login = ?").setString(0, login).uniqueResult() != null)
			throw new NonUniqueResultException("El nombre de usuario \"" + login + "\" ya existe");
		passwordHash = dto.getClave();

		ciudad = (Ciudad) sess.createQuery("FROM Ciudad WHERE nombre = ? AND pais.nombre = ?").setString(0,
				dto.getCiudad()).setString(1, dto.getPais()).uniqueResult();

		codPostal = dto.getCodPostal();
		descripPerfil = dto.getDescripPerfil();
		pathLogo = dto.getLogo().isEmpty() ? null : dto.getLogo();
	}

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
