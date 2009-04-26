package edu.tdp2.client.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Anchor;

import edu.tdp2.client.ProjectList;
import edu.tdp2.client.SoftmartService;
import edu.tdp2.client.SoftmartServiceAsync;
import edu.tdp2.client.model.Proyecto;

public class ClientUtils
{
	public static SoftmartServiceAsync getSoftmartService()
	{
		final SoftmartServiceAsync softmartService = (SoftmartServiceAsync) GWT.create(SoftmartService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) softmartService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "softmart");
		return softmartService;
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> deserializeResults(List serialized)
	{
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<String> serializedStrs = (List<String>) serialized;
		for (String serializedStr : serializedStrs)
		{
			String[] fieldsWithValue = serializedStr.split(";");
			Map<String, String> map = new HashMap<String, String>();
			for (String fieldWithValue : fieldsWithValue)
			{
				String[] fieldAndValue = fieldWithValue.split("=", 2);
				map.put(fieldAndValue[0], fieldAndValue[1]);
			}
			result.add(map);
		}
		return result;
	}

	public static Anchor getBackAnchor()
	{
		Anchor back = new Anchor("Volver");
		back.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				History.back();
			}
		});
		return back;
	}

	public static <T> T coalesce(T... ts)
	{
		for (T t : ts)
			if (t != null)
				return t;
		return null;
	}

	public static ClickHandler getHandlerForProjects(final ProjectList projects,
			final OneParamDelegate<Proyecto> delegate)
	{
		return new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Proyecto proyecto = projects.getSelectedItem();
				if (proyecto == null)
					Window.alert("Debe seleccionar un proyecto");
				else
					delegate.invoke(proyecto);
			}
		};
	}
}
