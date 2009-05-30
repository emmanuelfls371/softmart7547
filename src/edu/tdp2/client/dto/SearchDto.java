package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.validation.client.interfaces.IValidatable;

import edu.tdp2.client.model.Proyecto;

public class SearchDto implements Serializable, Dto, IValidatable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1998544287124768279L;

	private List<Proyecto> proyectosBuscados;

	public SearchDto()
	{
		proyectosBuscados = new ArrayList<Proyecto>();
	}

	public SearchDto(List<Proyecto> proyectosBuscados)
	{
		setProyectosBuscados(proyectosBuscados);
	}

	public List<Proyecto> getProyectosBuscados()
	{
		return proyectosBuscados;
	}

	public void setProyectosBuscados(List<Proyecto> proyectosBuscados)
	{
		this.proyectosBuscados = proyectosBuscados;
	}

}
