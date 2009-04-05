package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;

import edu.tdp2.client.utils.ClientUtils;
import edu.tdp2.server.model.Proyecto;

public class UnassignedProjectList extends ListBox
{
	public UnassignedProjectList()
	{
		super();
		load();
	}

	private void load()
	{
		AsyncCallback<List<Proyecto>> callback = new AsyncCallback<List<Proyecto>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudieron recuperar los proyectos sin adjudicar");
			}

			public void onSuccess(List<Proyecto> projects)
			{
				clear();
				for (Proyecto project : projects)
					addItem(project.getDescripcion(), project.getId().toString());
			}
		};
		ClientUtils.getSoftmartService().getUnassignedProjects(callback);
	}
}
