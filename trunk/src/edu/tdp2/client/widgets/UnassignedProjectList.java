package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.tdp2.client.ProjectList;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class UnassignedProjectList extends ProjectList
{
	private String usuario;
	public UnassignedProjectList(String usuario)
	{
		super();
		this.usuario=usuario;
		load();
		
	}

	@Override
	protected void doCall(AsyncCallback<List<Proyecto>> callback)
	{
		ClientUtils.getSoftmartService().getUnassignedProjects(usuario, callback);
	}
}