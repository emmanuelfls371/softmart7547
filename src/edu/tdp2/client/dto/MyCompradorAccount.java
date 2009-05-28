package edu.tdp2.client.dto;

import java.io.Serializable;
import java.util.List;

import edu.tdp2.client.model.Proyecto;


public class MyCompradorAccount extends MySpecificAccount implements Serializable
{
	private static final long serialVersionUID = -739806195271426185L;

	@Override
	protected List<Proyecto> prune(List<Proyecto> proyectos)
	{
		if(proyectos!=null){
			for (Proyecto proyecto : proyectos)
				proyecto.pruneIncludingOffers();
		}
		return proyectos;
		
	}
	
	
}