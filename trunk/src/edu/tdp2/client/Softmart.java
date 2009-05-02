package edu.tdp2.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

import edu.tdp2.client.dto.ContratoDto;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.model.Usuario;
import edu.tdp2.client.utils.ClientUtils;
import edu.tdp2.client.widgets.LoginListener;
import edu.tdp2.client.widgets.LoginWidget;
import edu.tdp2.client.widgets.MyAccountWidget;
import edu.tdp2.client.widgets.NewCalificationWidget;
import edu.tdp2.client.widgets.NewOfertaWidget;
import edu.tdp2.client.widgets.NewProjectWidget;
import edu.tdp2.client.widgets.RegistrationWidget;
import edu.tdp2.client.widgets.SearchWidget;
import edu.tdp2.client.widgets.UnassignedProjectList;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Softmart implements EntryPoint, LoginListener
{
	private SoftmartConstants constants;
	private SoftmartMessages messages;
	private VerticalPanel centerPanel;
	private HorizontalPanel northPanel;

	private Proyecto projectOferta;
	private ContratoDto contratoDto;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		if (RootPanel.get("SoftmartIdentifierDiv") == null)
			return;

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

		table.setWidget(0, 0, new Label("Mi cuenta"));
		Anchor menuLink = new Anchor("Ver datos");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onShowMyAccount();
			}
		});
		table.setWidget(0, 2, menuLink);

		table.setWidget(1, 0, new Label("Buscar por filtros"));
		Anchor menuLinkBuscar = new Anchor("Buscar");
		menuLinkBuscar.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onShowSearch();
			}
		});
		table.setWidget(1, 2, menuLinkBuscar);

		table.setWidget(2, 0, new Label("Nuevo proyecto"));
		menuLink = new Anchor("Publicar");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onShowNewProject();
			}
		});
		table.setWidget(2, 2, menuLink);

		table.setWidget(3, 0, new Label("Proyectos abiertos para ofertar"));
		final ProjectList unassignedProjects = new UnassignedProjectList(LoginWidget.getCurrentUser());
		table.setWidget(3, 1, unassignedProjects);
		menuLink = new Anchor("Ofertar");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Proyecto proyecto = unassignedProjects.getSelectedItem();
				if (proyecto == null)
					Window.alert("Debe seleccionar un proyecto para ofertar");
				else
					onShowNewOferta(proyecto);
			}
		});
		table.setWidget(3, 2, menuLink);
		menuLink = new Anchor("Ver Proyecto");
		menuLink.addClickHandler(getHandlerFormProjectList(unassignedProjects));
		table.setWidget(3, 3, menuLink);

		table.setWidget(4, 0, new Label("Proyectos pendientes de calificar"));
		final ProjectList qualifiableProjects = new QualifiableProjectList(LoginWidget.getCurrentUser());
		table.setWidget(4, 1, qualifiableProjects);
		menuLink = new Anchor("Calificar");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Proyecto proyecto = qualifiableProjects.getSelectedItem();
				if (proyecto == null)
					Window.alert("Debe seleccionar un proyecto para calificar");
				else
					onShowNewCalificacion(proyecto);
			}
		});
		table.setWidget(4, 2, menuLink);

		table.setWidget(5, 0, new Label("Proyectos propios abiertos"));
		final ProjectList ownOpenProjects = new OwnOpenProjectList(LoginWidget.getCurrentUser());
		table.setWidget(5, 1, ownOpenProjects);
		menuLink = new Anchor("Ver ofertas");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Proyecto proyecto = ownOpenProjects.getSelectedItem();
				if (proyecto == null)
					Window.alert("Debe seleccionar un proyecto");
				else
				{
					projectOferta = proyecto;
					onShowOffers();
				}
			}
		});
		table.setWidget(5, 2, menuLink);
		menuLink = new Anchor("Ver Proyecto");
		menuLink.addClickHandler(getHandlerFormProjectList(ownOpenProjects));
		table.setWidget(5, 3, menuLink);
		menuLink = new Anchor("Cancelar Proyecto");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				if (ownOpenProjects.getSelectedItem() == null)
					Window.alert("Debe seleccionar un proyecto para cancelar");
				else if (Window.confirm("Â¿Seguro de que desea cancelar el proyecto?"))
				{
					AsyncCallback<String> projectCallback = new AsyncCallback<String>()
					{
						public void onFailure(Throwable caught)
						{
							Window.alert("No se pudo cancelar el proyecto");
						}

						public void onSuccess(String errMsg)
						{
							if (errMsg != null)
								Window.alert(errMsg);
							else
							{
								ownOpenProjects.removeItem(ownOpenProjects.getSelectedIndex());
								Window.alert("Proyecto cancelado");
							}
						}
					};
					ClientUtils.getSoftmartService().cancelarProyecto(ownOpenProjects.getSelectedItem().getId(),
							projectCallback);
				}
			}
		});
		table.setWidget(5, 3, menuLink);

		table.setWidget(6, 0, new Label("Calificaciones recibidas"));
		final CalificacionList calif = new CalificacionRecibidaList(LoginWidget.getCurrentUser());
		table.setWidget(6, 1, calif);
		menuLink = new Anchor("Ver calificaci&oacute;n", true);
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				ContratoDto contrato = calif.getSelectedItem();
				if (contrato == null)
					Window.alert("Debe seleccionar un proyecto");
				else
					onShowCalification(contrato, TipoCalificacion.Recibida);
			}
		});
		table.setWidget(6, 2, menuLink);
		menuLink = new Anchor("Ver Proyecto");
		menuLink.addClickHandler(getHandlerForProjectFromCalifList(calif));
		table.setWidget(6, 3, menuLink);

		menuLink = new Anchor("Ver Oferta Ganadora");
		menuLink.addClickHandler(getHandlerForOfferFromCalifList(calif));
		table.setWidget(6, 5, menuLink);

		table.setWidget(7, 0, new Label("Calificaciones hechas"));
		final CalificacionList calif2 = new CalificacionHechaList(LoginWidget.getCurrentUser());
		table.setWidget(7, 1, calif2);
		menuLink = new Anchor("Ver calificaci&oacute;n", true);
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				ContratoDto contrato = calif2.getSelectedItem();
				if (contrato == null)
					Window.alert("Debe seleccionar un proyecto");
				else
					onShowCalification(contrato, TipoCalificacion.Hecha);
			}
		});
		table.setWidget(7, 2, menuLink);
		menuLink = new Anchor("Ver Proyecto");
		menuLink.addClickHandler(getHandlerForProjectFromCalifList(calif2));
		table.setWidget(7, 3, menuLink);

		menuLink = new Anchor("Ver Oferta Ganadora");
		menuLink.addClickHandler(getHandlerForOfferFromCalifList(calif2));
		table.setWidget(7, 5, menuLink);
		centerPanel.add(table);
	}

	private ClickHandler getHandlerForOfferFromCalifList(final CalificacionList calif)
	{
		return new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				ContratoDto contrato = calif.getSelectedItem();
				if (contrato == null)
					Window.alert("Debe seleccionar un proyecto");
				else
				{
					contratoDto = contrato;
					onShowOferta();
				}
			}
		};
	}

	private ClickHandler getHandlerForProjectFromCalifList(final CalificacionList califs)
	{
		return new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				final ContratoDto contrato = califs.getSelectedItem();
				if (contrato == null)
					Window.alert("Debe seleccionar un proyecto para ver");
				else
				{
					final Usuario us = new Usuario();
					us.setLogin(contrato.getProyecto().getUsuario());
					AsyncCallback<List<Moneda>> callback = new AsyncCallback<List<Moneda>>()
					{
						public void onFailure(Throwable caught)
						{
							Window.alert("No se pudo recuperar las monedas");
						}

				
						public void onSuccess(List<Moneda> monedas)
						{
							if (monedas == null)
								Window.alert("No se pudo recuperar las monedas");
							else
							{
								Moneda monedaEncontrada = null;
								for(Moneda m: monedas){
									if (m.getDescription().equals(contrato.getProyecto().getMoneda()))
										monedaEncontrada = m;
								}
								onShowProyecto(new Proyecto(contrato.getProyecto(), us, monedaEncontrada));
							}
						}
					};
					ClientUtils.getSoftmartService().buscarMonedas(callback);

				}
			}
		};
	}

	private ClickHandler getHandlerFormProjectList(final ProjectList projects)
	{
		return new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Proyecto proyecto = projects.getSelectedItem();
				if (proyecto == null)
					Window.alert("Debe seleccionar un proyecto");
				else
					onShowProyecto(proyecto);
			}
		};
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

	public void onShowRegistration()
	{
		putAlone(RegistrationWidget.getInstance(), HistoryToken.onShowRegistration.toString());
	}

	private void onShowMyAccount()
	{
		putAlone(new MyAccountWidget(LoginWidget.getCurrentUser()), HistoryToken.onShowMyAccount.toString());
	}

	public void onShowNewProject()
	{
		putAlone(NewProjectWidget.getInstance(), HistoryToken.onShowNewProject.toString());
	}

	public void onShowNewOferta(Proyecto project)
	{
		putAlone(NewOfertaWidget.getInstance(project));
	}

	public void onShowNewCalificacion(Proyecto project)
	{
		putAlone(NewCalificationWidget.getInstance(project));
	}

	protected void onShowOffers()
	{
		putAlone(new OffersWidget(projectOferta), HistoryToken.onShowChooseOffer.toString());
	}

	private void onShowProyecto(Proyecto project)
	{
		putAlone(new ProjectWidget(project));
	}

	private void onShowOferta()
	{
		putAlone(new OfertaWidget(contratoDto.getOferta(), contratoDto.getProyecto().getNombre()),
				HistoryToken.onShowOferta.toString());
	}

	protected void onShowCalification(ContratoDto contrato, TipoCalificacion tipo)
	{
		putAlone(new CalificationWidget(contrato.getCalif(), contrato.getProyecto().getNombre(), tipo));
	}

	private void onShowSearch()
	{
		putAlone(new SearchWidget(), HistoryToken.onShowSearch.toString());
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
			case onShowChooseOffer:
				onShowOffers();
				break;
			case onShowOferta:
				onShowOferta();
				break;
			case onShowMyAccount:
				onShowMyAccount();
				break;
			case onShowSearch:
				onShowSearch();
				break;
			}
		}
	}

}