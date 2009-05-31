package edu.tdp2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.widgets.AdminProjectsWidget;
import edu.tdp2.client.widgets.AdminUsersWidget;
import edu.tdp2.client.widgets.AdminWidget;

public class Admin implements EntryPoint
{
	private DecoratedTabPanel centerPanel;
	private I18nConstants i18nConstants;
	private SoftmartConstants constants;

	public void onModuleLoad()
	{
		constants = GWT.create(SoftmartConstants.class);
		i18nConstants = GWT.create(I18nConstants.class);

		if (RootPanel.get("AdminIdentifierDiv") == null)
			return;
		DockPanel dPanel = new DockPanel();
		centerPanel = new DecoratedTabPanel();
		centerPanel.setSize("100%", "450px");

		dPanel.add(centerPanel, DockPanel.CENTER);
		centerPanel.setAnimationEnabled(true);

		dPanel.add(getSouthPanel(), DockPanel.SOUTH);

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

	private Widget getSouthPanel()
	{
		HorizontalPanel panel = new HorizontalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setWidth("100%");

		final ListBox lisIdiomas = new ListBox();
		for (int i = 0; i < i18nConstants.idiomas().length; i++)
		{
			lisIdiomas.addItem(i18nConstants.idiomas()[i], i18nConstants.locales()[i]);
			if (constants.idioma().equals(i18nConstants.idiomas()[i]))
				lisIdiomas.setSelectedIndex(i);
		}
		lisIdiomas.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				Window.open("Admin.html" + lisIdiomas.getValue(lisIdiomas.getSelectedIndex()), "_self", "");
			}
		});
		panel.add(lisIdiomas);

		return panel;
	}
}
