package edu.tdp2.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.validation.client.InvalidConstraint;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.dto.Dto;

public abstract class FormWidget extends FormPanel
{

	protected String nombreWidget;
	protected String anchoWidget;
	protected String anchoTabla;
	protected String url;

	protected Dto dto;
	protected Map<FormFields, Widget> widgets = new HashMap<FormFields, Widget>();

	protected List<String> errMsgs;

	protected native void reload() /*-{
	   $wnd.location.reload();
	  }-*/;

	protected void buildWidget()
	{
		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		FlexTable table = getTable();
		panel.add(table);
		add(panel);
	}

	protected abstract FormFields[] values();

	protected FlexTable getTable()
	{
		FlexTable table = new FlexTable();
		table.setWidget(0, 0, new HTML(nombreWidget));
		int row = 1;
		table.getWidget(0, 0).setWidth(anchoWidget);
		for (FormFields field : this.values())
		{
			table.setWidget(row, 0, new HTML("<b>" + field.getDescription() + "</b>"));
			table.setWidget(row, 1, widgets.get(field));
			row++;
		}
		table.setWidget(row, 1, getSubmitPanel());
		table.setWidth(anchoTabla);
		return table;
	}

	protected void init()
	{
		setMethod(METHOD_POST);
		setEncoding(FormPanel.ENCODING_MULTIPART);
		setAction(GWT.getModuleBaseURL() + url);
		populateWidgets();
		buildWidget();
		errMsgs = new ArrayList<String>();
	}

	protected abstract void populateWidgets();

	protected HorizontalPanel getSubmitPanel()
	{
		HorizontalPanel submitPanel = new HorizontalPanel();
		submitPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		submitPanel.setWidth("100%");
		Button submit = new Button("Entrar", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				submit();
			}
		});
		submitPanel.add(submit);
		return submitPanel;
	}

	protected void createErrorMessage(Class<?> dtoClass)
	{
		errMsgs.clear();

		IValidator<Dto> validator = GWT.create(dtoClass);
		for (InvalidConstraint<Dto> error : validator.validate(dto))
			errMsgs.add(error.getMessage());
	}

	protected boolean buildErrorMessage()
	{
		if (errMsgs.size() > 0)
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < errMsgs.size(); i++)
			{
				if (i > 0)
					sb.append('\n');
				sb.append(errMsgs.get(i));
			}
			Window.alert(sb.toString());
			return false;
		}
		return true;
	}

	protected abstract boolean validate();
}
