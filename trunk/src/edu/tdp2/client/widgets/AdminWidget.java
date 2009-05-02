package edu.tdp2.client.widgets;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

public interface AdminWidget
{
	void setStatusMessage(HTML statusMessage);
	void setContainer(SimplePanel container);
	void load();
}
