package edu.tdp2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.widgets.CalificationWidget;
import edu.tdp2.client.widgets.ChangePwListener;
import edu.tdp2.client.widgets.LoginListener;
import edu.tdp2.client.widgets.LoginWidget;
import edu.tdp2.client.widgets.NewOfertaWidget;
import edu.tdp2.client.widgets.NewProjectWidget;
import edu.tdp2.client.widgets.RegistrationWidget;
import edu.tdp2.client.widgets.UnassignedProjectList;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Softmart implements EntryPoint, LoginListener, ChangePwListener
{
	private SoftmartConstants constants;
	private SoftmartMessages messages;
	private VerticalPanel centerPanel;
	private HorizontalPanel northPanel;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		constants = (SoftmartConstants) GWT.create(SoftmartConstants.class);
		messages = (SoftmartMessages) GWT.create(SoftmartMessages.class);
		History.addValueChangeHandler(new SoftmartHistoryHandler());

		HorizontalPanel hPanel = new HorizontalPanel();
		DockPanel dPanel = new DockPanel();

		dPanel.add(getWestPanel(), DockPanel.WEST);
		dPanel.add(getCenterPanel(), DockPanel.CENTER);
		dPanel.add(getNorthPanel(), DockPanel.NORTH);
		showWelcome();
		History.newItem(HistoryToken.Welcome.toString());

		hPanel.add(dPanel);
		hPanel.setWidth("100%");
		hPanel.setCellHorizontalAlignment(dPanel, HasHorizontalAlignment.ALIGN_CENTER);
		RootPanel.get().add(hPanel);
	}

	private void showWelcome()
	{
		centerPanel.clear();

		String loginCookie = Cookies.getCookie(constants.loginCookieName());
		if (loginCookie != null && !loginCookie.equals(""))
			onLogin();
		else
		{
			centerPanel.clear();
			LoginWidget loginWidget = LoginWidget.getInstance();
			centerPanel.add(loginWidget);
			loginWidget.getUserNameTextBox().setText("");
			loginWidget.getPasswordTextBox().setText("");
			loginWidget.setLoginListener(this);
		}
	}

	private void showMenu()
	{
		FlexTable table = new FlexTable();
		table.setCellPadding(10);

		table.setWidget(0, 0, new Label("Nuevo proyecto"));
		Anchor menuLink = new Anchor("Publicar");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onShowNewProject();
			}
		});
		table.setWidget(0, 2, menuLink);

		table.setWidget(1, 0, new Label("Proyectos abiertos para ofertar"));
		final ProjectList unassignedProjects = new UnassignedProjectList();
		table.setWidget(1, 1, unassignedProjects);
		menuLink = new Anchor("Ofertar");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Proyecto proyecto = unassignedProjects.getSelectedItem();
				if (proyecto == null)
					Window.alert("Debe seleccionar un proyecto para calificar");
				else
					onShowNewOferta(proyecto);
			}
		});
		table.setWidget(1, 2, menuLink);

		table.setWidget(2, 0, new Label("Proyectos pendientes de calificar"));
		final ProjectList qualifiableProjects = new QualifiableProjectList(LoginWidget.getCurrentUser());
		table.setWidget(2, 1, qualifiableProjects);
		menuLink = new Anchor("Calificar");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Proyecto proyecto = qualifiableProjects.getSelectedItem();
				if (proyecto == null)
					Window.alert("Debe seleccionar un proyecto para calificar");
				else
					onShowCalificacion(proyecto);
			}
		});
		table.setWidget(2, 2, menuLink);
		centerPanel.add(table);

		table.setWidget(3, 0, new Label("Proyectos propios abiertos"));
		final ProjectList ownOpenProjects = new OwnOpenProjectList(LoginWidget.getCurrentUser());
		table.setWidget(3, 1, ownOpenProjects);
		menuLink = new Anchor("Ver ofertas");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Proyecto proyecto = ownOpenProjects.getSelectedItem();
				if (proyecto == null)
					Window.alert("Debe seleccionar un proyecto");
				else
					onShowOffers(proyecto);
			}
		});
		table.setWidget(3, 2, menuLink);
		centerPanel.add(table);
	}

	private Widget getWestPanel()
	{
		SimplePanel panel = new SimplePanel();
		panel.setSize("1px", "600px");
		return panel;
	}

	private Panel getCenterPanel()
	{
		centerPanel = new VerticalPanel();
		centerPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		centerPanel.setSize("100%", "450px");
		return centerPanel;
	}

	private Panel getNorthPanel()
	{
		northPanel = new HorizontalPanel();
		northPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		northPanel.setSize("700px", "50px");
		return northPanel;
	}

	public void onLogin()
	{
		centerPanel.clear();
		northPanel.clear();
		String loginCookie = Cookies.getCookie(constants.loginCookieName());
		String currentUser = loginCookie.split(";")[0];
		LoginWidget.setCurrentUser(currentUser);
		centerPanel.add(new HTML(messages.welcomeUser(currentUser == null ? "" : currentUser)));

		Anchor logoutLink = new Anchor(constants.logout());
		logoutLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onLogout();
			}
		});
		northPanel.add(logoutLink);

		showMenu();
	}

	public void onLogout()
	{
		Cookies.setCookie(constants.loginCookieName(), "");
		LoginWidget.setCurrentUser(null);
		northPanel.clear();
		showWelcome();
	}

	public void onChangePw()
	{
		// TODO Auto-generated method stub

	}

	public void onShowRegistration()
	{
		putAlone(RegistrationWidget.getInstance(), HistoryToken.onShowRegistration.toString());
	}

	public void onShowNewProject()
	{
		putAlone(NewProjectWidget.getInstance(), HistoryToken.onShowNewProject.toString());
	}

	public void onShowNewOferta(Proyecto project)
	{
		putAlone(NewOfertaWidget.getInstance(project));
	}

	public void onShowCalificacion(Proyecto project)
	{
		putAlone(CalificationWidget.getInstance(project));
	}

	protected void onShowOffers(Proyecto project)
	{
		putAlone(new OffersWidget(project));
	}

	private void putAlone(Widget widget)
	{
		putAlone(widget, "");
	}

	private void putAlone(Widget widget, String historyToken)
	{
		History.newItem(historyToken);
		centerPanel.clear();
		centerPanel.add(widget);
	}

	private class SoftmartHistoryHandler implements ValueChangeHandler<String>
	{
		public void onValueChange(ValueChangeEvent<String> event)
		{
			HistoryToken token;
			try
			{
				token = HistoryToken.valueOf(event.getValue());
			}
			catch (IllegalArgumentException e)
			{
				return;
			}

			switch (token)
			{
			case onShowNewProject:
				onShowNewProject();
				break;
			case onShowRegistration:
				onShowRegistration();
				break;
			case Welcome:
				showWelcome();
				break;
			}
		}
	}
}
