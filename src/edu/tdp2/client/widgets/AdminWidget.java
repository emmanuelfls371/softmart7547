package edu.tdp2.client.widgets;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

public abstract class AdminWidget
{
	protected HTML statusMessage;
	protected SimplePanel container;

	public void setStatusMessage(HTML statusMessage)
	{
		this.statusMessage = statusMessage;
	}

	public void setContainer(SimplePanel container)
	{
		this.container = container;
	}

	public abstract void load();
}
