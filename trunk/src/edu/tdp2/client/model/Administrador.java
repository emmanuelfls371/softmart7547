package edu.tdp2.client.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Admin")
public class Administrador extends AbstractDomainObject
{
	private static final long serialVersionUID = -4991786482118294352L;

	@Column(name = "Login", length = 50, nullable = false)
	private String login;

	@Column(name = "PasswordHash", length = 32, nullable = false)
	private String passwordHash;

	public String getLogin()
	{
		return login;
	}

	public String getPasswordHash()
	{
		return passwordHash;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public void setPasswordHash(String passwordHash)
	{
		this.passwordHash = passwordHash;
	}

}
