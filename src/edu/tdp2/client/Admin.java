package edu.tdp2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import edu.tdp2.client.widgets.AdminProjectsWidget;
import edu.tdp2.client.widgets.AdminUsersWidget;
import edu.tdp2.client.widgets.AdminWidget;

public class Admin implements EntryPoint
{
	private DecoratedTabPanel centerPanel;

	public void onModuleLoad()
	{
		if (RootPanel.get("AdminIdentifierDiv") == null)
			return;
		DockPanel dPanel = new DockPanel();
		centerPanel = new DecoratedTabPanel();
		centerPanel.setSize("100%", "450px");

		dPanel.add(centerPanel, DockPanel.CENTER);
		centerPanel.setAnimationEnabled(true);

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(dPanel);
		hPanel.setWidth("100%");
		hPanel.setCellHorizontalAlignment(dPanel, HasHorizontalAlignment.ALIGN_CENTER);

		RootPanel.get().add(hPanel);

		AdminWidget w = AdminProjectsWidget.getInstance();
		w.setContainer(centerPanel);
		w.load();

		AdminWidget w2 = AdminUsersWidget.getInstance();
		w2.setContainer(centerPanel);
		w2.load();

		centerPanel.selectTab(0);

	}
}
