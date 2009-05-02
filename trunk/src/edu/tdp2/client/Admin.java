package edu.tdp2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import edu.tdp2.client.widgets.AdminProjectsWidget;
import edu.tdp2.client.widgets.AdminWidget;

public class Admin implements EntryPoint
{
	private SimplePanel centerPanel;
	private HTML statusMessage = new HTML();

	public void onModuleLoad()
	{
		if (RootPanel.get("AdminIdentifierDiv") == null)
			return;
		DockPanel dPanel = new DockPanel();
		centerPanel = new SimplePanel();
		centerPanel.setSize("100%", "450px");
		statusMessage.setHeight("50px");
		dPanel.add(centerPanel, DockPanel.CENTER);

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(dPanel);
		hPanel.setWidth("100%");
		hPanel.setCellHorizontalAlignment(dPanel, HasHorizontalAlignment.ALIGN_CENTER);

		RootPanel.get().add(hPanel);

		AdminWidget w = AdminProjectsWidget.getInstance();
		w.setStatusMessage(statusMessage);
		w.setContainer(centerPanel);
		w.load();
	}
}
