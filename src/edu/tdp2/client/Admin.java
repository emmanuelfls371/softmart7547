package edu.tdp2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.utils.ClientUtils;
import edu.tdp2.client.widgets.AdminLoginWidget;
import edu.tdp2.client.widgets.AdminProjectsWidget;
import edu.tdp2.client.widgets.AdminUsersWidget;
import edu.tdp2.client.widgets.AdminWidget;
import edu.tdp2.client.widgets.LoginListener;

public class Admin implements EntryPoint, LoginListener
{
	private I18nConstants i18nConstants;
	private SoftmartConstants constants;
	private HorizontalPanel centerPanel;
	private HorizontalPanel northPanel;

	public void onLogin()
	{
		centerPanel.clear();
		northPanel.clear();

		Anchor logout = new Anchor(constants.logout());
		logout.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onLogout();
			}
		});
		northPanel.add(logout);

		DecoratedTabPanel dtPanel = new DecoratedTabPanel();
		dtPanel.setSize("100%", "450px");

		centerPanel.add(dtPanel);
		dtPanel.setAnimationEnabled(true);

		AdminWidget w = AdminProjectsWidget.getInstance();
		w.setContainer(dtPanel);
		w.load();

		AdminWidget w2 = AdminUsersWidget.getInstance();
		w2.setContainer(dtPanel);
		w2.load();

		dtPanel.selectTab(0);
	}

	public void onLogout()
	{
		Cookies.setCookie(constants.adminLoginCookieName(), "");

		ClientUtils.getSoftmartService().adminLogout(AdminLoginWidget.getCurrentUser(), new AsyncCallback<String>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			public void onSuccess(String result)
			{
				AdminLoginWidget.setCurrentUser(null);
				northPanel.clear();
				showWelcome();
			}
		});
	}

	public void onModuleLoad()
	{
		if (RootPanel.get("AdminIdentifierDiv") == null)
			return;

		constants = GWT.create(SoftmartConstants.class);
		i18nConstants = GWT.create(I18nConstants.class);

		DockPanel dPanel = new DockPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(dPanel);
		hPanel.setWidth("100%");
		hPanel.setCellHorizontalAlignment(dPanel, HasHorizontalAlignment.ALIGN_CENTER);

		centerPanel = new HorizontalPanel();
		centerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		centerPanel.setSize("100%", "450px");
		dPanel.add(centerPanel, DockPanel.CENTER);
		dPanel.add(getSouthPanel(), DockPanel.SOUTH);
		dPanel.add(getNorthPanel(), DockPanel.NORTH);

		RootPanel.get().add(hPanel);

		showWelcome();
	}

	public void onShowRegistration()
	{
		throw new UnsupportedOperationException();
	}

	private Widget getNorthPanel()
	{
		northPanel = new HorizontalPanel();
		northPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		northPanel.setWidth("100%");
		return northPanel;
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

	private void showWelcome()
	{
		centerPanel.clear();

		String loginCookie = Cookies.getCookie(constants.adminLoginCookieName());
		if (loginCookie != null && !loginCookie.equals(""))
			onLogin();
		else
		{
			centerPanel.clear();
			AdminLoginWidget loginWidget = AdminLoginWidget.getInstance();
			centerPanel.add(loginWidget);
			loginWidget.getUserNameTextBox().setText("");
			loginWidget.getPasswordTextBox().setText("");
			loginWidget.setLoginListener(this);
		}
	}
}
