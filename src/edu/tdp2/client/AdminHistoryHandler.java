package edu.tdp2.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;

import edu.tdp2.client.widgets.AdminProjectsWidget;
import edu.tdp2.client.widgets.AdminUsersWidget;
import edu.tdp2.client.widgets.AdminWidget;

public class AdminHistoryHandler implements ValueChangeHandler<String>
{
	protected HTML statusMessage;
	protected DecoratedTabPanel container;

	public void setStatusMessage(HTML statusMessage)
	{
		this.statusMessage = statusMessage;
	}

	public void setContainer(DecoratedTabPanel container)
	{
		this.container = container;
	}

	public void onValueChange(ValueChangeEvent<String> event)
	{
		AdminHistoryToken token;
		try
		{
			token = AdminHistoryToken.valueOf(event.getValue());
		}
		catch (IllegalArgumentException e)
		{
			return;
		}

		AdminWidget w = null;

		switch (token)
		{
		case AdminProjects:
			w = AdminProjectsWidget.getInstance();
			break;
		case AdminUsers:
			w = AdminUsersWidget.getInstance();
			break;
		}
		w.setContainer(container);
		w.load();
	}
}