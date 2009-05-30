package edu.tdp2.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.validation.client.InvalidConstraint;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.utils.ClientUtils;

public abstract class FormWidget extends FormPanel
{
	protected String tituloWidget;
	protected String anchoWidget;
	protected String anchoTabla;
	protected String url;

	protected Dto dto;
	protected Map<FormFields, Widget> widgets = new HashMap<FormFields, Widget>();

	protected void buildWidget()
	{
		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		FlexTable table = getTable();
		panel.add(table);
		add(panel);
	}

	protected abstract IValidator<Dto> getValidator();

	protected void init()
	{
		setMethod(METHOD_POST);
		setEncoding(FormPanel.ENCODING_MULTIPART);
		setAction(GWT.getModuleBaseURL() + url);
		populateWidgets();
		buildWidget();
	}

	protected abstract void populateWidgets();

	protected native void reload() /*-{
	  $wnd.location.reload();
	  }-*/;

	protected final boolean validate()
	{
		List<String> errMsgs = createErrorMessage();
		validate(errMsgs);
		return buildErrorMessage(errMsgs);
	}

	protected abstract void validate(List<String> errMsgs);

	protected abstract FormFields[] values();

	private boolean buildErrorMessage(List<String> errMsgs)
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

	private List<String> createErrorMessage()
	{
		List<String> errMsgs = new ArrayList<String>();
		errMsgs.clear();

		IValidator<Dto> validator = getValidator();
		for (InvalidConstraint<Dto> error : validator.validate(dto))
			errMsgs.add(error.getMessage());
		return errMsgs;
	}

	private HorizontalPanel getSubmitPanel()
	{
		HorizontalPanel submitPanel = new HorizontalPanel();
		submitPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		submitPanel.setWidth("100%");
		Button submit = new Button("Entrar", new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				submit();
			}
		});
		submitPanel.add(submit);
		return submitPanel;
	}

	private FlexTable getTable()
	{
		FlexTable table = new FlexTable();
		table.setWidget(0, 0, new HTML(tituloWidget));

		table.setWidget(0, 1, ClientUtils.getBackAnchor());

		int row = 1;
		table.getWidget(0, 0).setWidth(anchoWidget);
		for (FormFields field : values())
		{
			table.setWidget(row, 0, new HTML("<b>" + field.getDescription() + "</b>"));
			table.setWidget(row, 1, widgets.get(field));
			row++;
		}
		table.setWidget(row, 1, getSubmitPanel());
		table.setWidth(anchoTabla);
		return table;
	}
}
