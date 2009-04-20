package edu.tdp2.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;

import edu.tdp2.client.model.Proyecto;

public class ProjectList extends ListBox
{
	private Map<String, Proyecto> projects = new HashMap<String, Proyecto>();

	public ProjectList(List<Proyecto> projects)
	{
		super();
		setWidth();
		loadProjects(projects);
	}
	
	protected ProjectList()
	{
		super();
		setWidth();
		load();
	}

	private void setWidth()
	{
		setWidth("200px");
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
				loadProjects(projects);
			}
		};
		doCall(callback);
	}

	private void loadProjects(List<Proyecto> projects)
	{
		clear();
		for (Proyecto project : projects)
			addItem(project);
	}

	/**
	 * Este metodo debe ser implementado en las subclases
	 * @param callback
	 */
	protected void doCall(AsyncCallback<List<Proyecto>> callback)
	{
	}

	private void addItem(Proyecto project)
	{
		addItem(project.getNombre(), project.getId().toString());
		projects.put(project.getId().toString(), project);
	}

	public Proyecto getSelectedItem()
	{
		int index = getSelectedIndex();
		if (index == -1)
			return null;
		else
			return projects.get(getValue(index));
	}
}