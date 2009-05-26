package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.validation.client.interfaces.IValidatable;


import edu.tdp2.client.model.Proyecto;

public class SearchDto implements Serializable, Dto, IValidatable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1998544287124768279L;
	
	private List<Proyecto> proyectosBuscados;
	
	public SearchDto(List<Proyecto> proyectosBuscados){
		this.setProyectosBuscados(proyectosBuscados);
	}
	
	public SearchDto()
	{
		 this.proyectosBuscados=new ArrayList<Proyecto>();
	}

	public void setProyectosBuscados(List<Proyecto> proyectosBuscados) {
		this.proyectosBuscados = proyectosBuscados;
	}

	public List<Proyecto> getProyectosBuscados() {
		return proyectosBuscados;
	}

	
}
