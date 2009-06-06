package edu.tdp2.client.widgets;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import edu.tdp2.client.utils.ClientUtils;

public class AdminLogFilterWidget extends AdminWidget
{
	private static AdminLogFilterWidget instance;
	private TextArea text = new TextArea();
	private DateBox dateFrom = new DateBox();
	private DateBox dateTo = new DateBox();
	private ListBox lisAdmin = new ListBox();
	private FlexTable fieldsTable = new FlexTable();
	private VerticalPanel vPanel = new VerticalPanel();
	private Button filtrarLog = new Button(constants.filter());
	private static AdminLogFilterConstants constants = GWT.create(AdminLogFilterConstants.class);

	public static AdminLogFilterWidget getInstance()
	{
		if (instance == null)
			instance = new AdminLogFilterWidget();
		return instance;
	}

	@Override
	public void load()
	{
		container.add(vPanel, constants.logFilter());

		loadPanel();
	}

	private void loadPanel()
	{
		initLisAdmins();
		text.setVisibleLines(20);
		text.setWidth("700px");

		fieldsTable.setWidget(0, 0, new HTML(constants.desde()));
		fieldsTable.setWidget(0, 1, dateFrom);
		fieldsTable.setWidget(1, 0, new HTML(constants.hasta()));
		fieldsTable.setWidget(1, 1, dateTo);
		fieldsTable.setWidget(2, 0, new HTML(constants.seleccioneAdmin()));
		fieldsTable.setWidget(2, 1, lisAdmin);
		vPanel.add(fieldsTable);

		filtrarLog.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert(caught.getMessage());
					}

					public void onSuccess(String result)
					{
						text.setText(result);
					}
				};
				Date from = dateFrom.getValue();
				Date to = dateTo.getValue();
				String admin = null;
				if (lisAdmin.getSelectedIndex() >= 0)
					admin = lisAdmin.getValue(lisAdmin.getSelectedIndex());
				ClientUtils.getSoftmartService().filterLog(from, to, admin, callback);
			}
		});
		vPanel.add(filtrarLog);
		vPanel.add(text);
	}

	private void initLisAdmins()
	{
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			public void onSuccess(List<String> result)
			{
				for (String adminLogin : result)
					lisAdmin.addItem(adminLogin, adminLogin);
			}
		};
		ClientUtils.getSoftmartService().getAdmins(callback);
	}
}
