package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.tdp2.client.ProjectList;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class UnassignedProjectList extends ProjectList
{
	public UnassignedProjectList()
	{
		super();
		load();
	}

	@Override
	protected void doCall(AsyncCallback<List<Proyecto>> callback)
	{
		ClientUtils.getSoftmartService().getUnassignedProjects(callback);
	}
}