package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class QualifiableProjectList extends ProjectList
{
	private String user;

	public QualifiableProjectList(String user)
	{
		super();
		this.user = user;
		load();
	}

	@Override
	protected void doCall(AsyncCallback<List<Proyecto>> callback)
	{
		ClientUtils.getSoftmartService().getQualifiableProjects(user, callback);
	}
}