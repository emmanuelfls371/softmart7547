package edu.tdp2.client.widgets;

import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;


public abstract class AdminWidget
{
	protected HTML statusMessage;
	protected DecoratedTabPanel container;


	public void setContainer(DecoratedTabPanel container)
	{
		this.container = container;
	}

	public abstract void load();
}
