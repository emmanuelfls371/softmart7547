package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;

import edu.tdp2.server.model.Proyecto;

public abstract class ProjectList extends ListBox
{
	public ProjectList()
	{
		super();
		load();
	}

	protected void load()
	{
		AsyncCallback<List<Proyecto>> callback = new AsyncCallback<List<Proyecto>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudieron recuperar los proyectos");
			}
	
			public void onSuccess(List<Proyecto> projects)
			{
				clear();
				for (Proyecto project : projects)
					addItem(project.getDescripcion(), project.getId().toString());
			}
		};
		doCall(callback);
	}

	protected abstract void doCall(AsyncCallback<List<Proyecto>> callback);
}