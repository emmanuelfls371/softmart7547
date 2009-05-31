package edu.tdp2.client.widgets;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.ComentarioWidget;
import edu.tdp2.client.ProjectWidget;
import edu.tdp2.client.model.NivelReputacion;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class DetailSearchWidget extends VerticalPanel
{

	private Proyecto proy;
	private VerticalPanel panel;
	private List<Oferta> ofertas;
	private Oferta ofertaG;

	public DetailSearchWidget(Proyecto proy)
	{
		this.proy = proy;
		load();
	}

	public void load()
	{

		AsyncCallback<List<Oferta>> callback = new AsyncCallback<List<Oferta>>()
		{

			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar las ofertas");
			}

			public void onSuccess(List<Oferta> of)
			{
				if (of == null)
					Window.alert("No se pudo recuperar las ofertas");
				else
				{
					ofertas = of;
					add(new ProjectWidget(proy));
					getOfertaGanadora();
				}
			}
		};
		ClientUtils.getSoftmartService().getOffers(proy, callback);

	}

	public void onShowNewOferta(Proyecto project)
	{
		clear();
		add(NewOfertaWidget.getInstance(project));
	}

	private void buildTableOfertas()
	{
		panel = new VerticalPanel();
		initialize();
		for (final Oferta of : ofertas)
			load(of);

	}

	private void getOfertaGanadora()
	{

		AsyncCallback<Oferta> callback = new AsyncCallback<Oferta>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar la oferta");
			}

			public void onSuccess(Oferta oferta)
			{
				ofertaG = oferta;


				Anchor menuLink = new Anchor("Ofertar");
				if (ofertaG == null && !proy.getUsuario().getLogin().equals(LoginWidget.getCurrentUser())
						&& proy.getFecha().after(new Date()) && !proy.isCancelado() && proy.isRevisado()
						&& !proy.isCanceladoXAdmin() && !proy.getUsuario().isBloqueado())
					menuLink.addClickHandler(new ClickHandler()
					{
						public void onClick(ClickEvent event)
						{
							final Proyecto proyecto = proy;
							if (proyecto == null)
								Window.alert("Debe seleccionar un proyecto para ofertar");
							else
							{

								AsyncCallback<String> callback = new AsyncCallback<String>()
								{
									public void onFailure(Throwable caught)
									{
										Window.alert("No se pudo recuperar el usuario");
									}

									public void onSuccess(String nivel)
									{
										if (nivel == null)
											Window.alert("No se pudo recuperar el usuario");
										else if (proyecto.getNivel().equals(NivelReputacion.Premium.name())
												&& nivel.equals(NivelReputacion.Premium.name()))
											onShowNewOferta(proyecto);
										else if (proyecto.getNivel().equals(NivelReputacion.Normal.name()))
											onShowNewOferta(proyecto);
										else
											Window.alert("El proyecto requiere ofertantes Premium");
									}
								};
								ClientUtils.getSoftmartService().getUsuario(LoginWidget.getCurrentUser(), callback);

							}
						}
					});
				else{
					//VERIFICAR CADA CONDICION Y PONER UN CARTEL
					//menuLink.setEnabled(false);
					menuLink.addStyleName("a-disabled");
				}

				add(menuLink);
				buildTableOfertas();
				add(panel);
				
				
			}
		};
		ClientUtils.getSoftmartService().getOfertaGanadora(proy.getId(), callback);
	}

	private void initialize()
	{
		add(new HTML("<h3> Ofertas para el proyecto " + proy.getNombre() + "</h3>"));
	}

	private void load(final Oferta oferta)
	{
		FlexTable table = new FlexTable();
		setSpacing(7);

		table.clear();
		add(table);
		table.setWidget(0, 2, new HTML("Monto:"));
		table.setWidget(1, 2, new HTML("D&iacute;as:"));

		table.setWidget(0, 0, new HTML("Vendedor:"));
		table.setWidget(1, 0, new HTML("Nivel de reputaci&oacute;n de usuario:"));

		final HTML h = new HTML(oferta.getUsuario().getLogin());

		table.addStyleName("tableProjectWidget");

		table.setCellPadding(5);

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar el usuario");
			}

			public void onSuccess(Boolean isBloqueado)
			{
				if (isBloqueado)
				{
					h.addStyleName("blocked");
					h.setStyleName("blocked");
					HTML h2 = new HTML("*Usuario Bloqueado");
					h2.setStyleName("blocked");
					h2.addStyleName("c1y2ProjectWidget");
					h2.setWidth("200px");
					add(h2);
					h.setHTML(oferta.getUsuario().getLogin() + "*");
				}
			}
		};
		ClientUtils.getSoftmartService().isUsuarioBloqueado(oferta.getUsuario().getLogin(), callback);

		table.getCellFormatter().addStyleName(0, 1, "row0c1ProjectWidget");
		table.getCellFormatter().addStyleName(1, 1, "row0c1ProjectWidget");

		table.getCellFormatter().addStyleName(2, 2, "row2c2ProjectWidget");

		table.getCellFormatter().addStyleName(0, 0, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(1, 0, "c0ProjectWidget");

		table.getCellFormatter().addStyleName(0, 2, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(1, 2, "c0ProjectWidget");

		table.getCellFormatter().addStyleName(0, 1, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(1, 1, "c1y2ProjectWidget");

		table.getCellFormatter().addStyleName(0, 3, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(1, 3, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(2, 2, "c1y2ProjectWidget");

		table.setWidget(0, 1, h);
		table.setWidget(1, 1, new HTML(oferta.getUsuario().getNivel()));
		table.setWidget(0, 3,
				new HTML(Float.toString(oferta.getMonto()) + " en " + oferta.getMoneda().getDescription()));
		table.setWidget(1, 3, new HTML(Integer.toString(oferta.getDias())));

		Anchor menuLink = new Anchor("Ver Comentario");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				final DialogBox dialogBox = new ComentarioWidget(oferta);
				dialogBox.setAnimationEnabled(true);
				dialogBox.show();

			}
		});

		table.setWidget(2, 2, menuLink);

		if (ofertaG != null && ofertaG.compare(oferta))
		{
			table.setWidget(2, 0, new HTML("Oferta ganadora"));
			table.getCellFormatter().addStyleName(2, 0, "row0ProjectWidget");
			table.getCellFormatter().addStyleName(2, 1, "row0ProjectWidget");
			table.getCellFormatter().addStyleName(2, 0, "styleOfganadora");
		}

		panel.add(table);
	}

}
