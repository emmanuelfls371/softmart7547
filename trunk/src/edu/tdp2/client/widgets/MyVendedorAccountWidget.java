package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.OfertaWidget;
import edu.tdp2.client.ProjectWidget;
import edu.tdp2.client.dto.MyVendedorAccount;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;

public class MyVendedorAccountWidget extends AccountWidget
{

	private class ProjectTable extends FlexTable
	{
		private final List<Proyecto> proyectos;

		public ProjectTable(List<Proyecto> proyectos)
		{
			this.proyectos = proyectos;
			setBorderWidth(1);
			buildWidget();
		}

		private void buildWidget()
		{

			addStyleName("table");

			for (int i = 0; i < 6; i++)
				getCellFormatter().addStyleName(0, i, "firstRow");

			setWidget(0, 0, new HTML(constants.proyecto()));
			setWidget(0, 1, new HTML(constants.miOferta()));
			setWidget(0, 2, new HTML(constants.moneda()));
			setWidget(0, 3, new HTML(constants.dias()));
			setWidget(0, 4, new HTML(constants.esLaMenor()));
			setWidget(0, 5, new HTML(constants.fechaCierre()));
			if (accion)
			{
				setWidget(0, 6, new HTML(constants.accion()));
				getCellFormatter().addStyleName(0, 6, "firstRow");
			}

			int row = 1;
			for (final Proyecto proyecto : proyectos)
			{
				Oferta ofertaPropia = null;
				Oferta menorOferta = null;
				for (final Oferta of : proyecto.getOfertas())
				{

					if (of.getUsuario().getLogin().equals(LoginWidget.getCurrentUser()))
						ofertaPropia = of;
					else
						ofertaPropia = null;

					if (menorOferta == null || of.getMonto() < menorOferta.getMonto())
						menorOferta = of;

					if (ofertaPropia != null)
					{

						Anchor aProy = new Anchor(proyecto.getNombre());
						aProy.addClickHandler(new ClickHandler()
						{
							public void onClick(ClickEvent event)
							{
								centerPanel.clear();
								centerPanel.add(new ProjectWidget(proyecto));
								// centerPanel.add(underPanel);
							}
						});
						setWidget(row, 0, aProy);

						Anchor aOferta = new Anchor("" + ofertaPropia.getMonto());
						final Oferta finalOferta = ofertaPropia;
						aOferta.addClickHandler(new ClickHandler()
						{
							public void onClick(ClickEvent event)
							{
								centerPanel.clear();
								centerPanel.add(new OfertaWidget(finalOferta, proyecto.getNombre()));
								// centerPanel.add(underPanel);
							}
						});
						setWidget(row, 1, aOferta);

						setWidget(row, 2, new HTML(ofertaPropia.getMoneda().getDescription()));
						setWidget(row, 3, new HTML("" + ofertaPropia.getDias()));
						setWidget(row, 4, new HTML(ofertaPropia.compare(menorOferta) ? constants.si() : constants.no()));

						DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
						setWidget(row, 5, new HTML(format.format(proyecto.getFecha())));
						if (accion)
						{
							setWidget(row, 6, getActionButton(proyecto));
							getCellFormatter().addStyleName(row, 6, "column");
						}
					}

					for (int i = 0; i < 6; i++)
						getCellFormatter().addStyleName(row, i, "column");

					row++;
				}
			}
		}
	}

	private final MyVendedorAccount datos;
	private MyVendedorAccountConstants constants;

	public MyVendedorAccountWidget(MyVendedorAccount datos)
	{
		this.datos = datos;
		constants = GWT.create(MyVendedorAccountConstants.class);
		add(getWestPanel(), WEST);
		underPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		centerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		centerPanel.add(underPanel);
		add(centerPanel, CENTER);
		eastPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		add(eastPanel, SOUTH);
		centerPanel.setWidth("100%");
		centerPanel.setSpacing(10);
	}

	private Widget getWestPanel()
	{
		underPanel.clear();
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(10);

		panel.add(new HTML(constants.proyectosOfertados()));

		Anchor abiertos = new Anchor(constants.abiertos());
		abiertos.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected = null;
				accion = false;
				proyCerrados = false;
				centerPanel.clear();
				centerPanel.add(new ProjectTable(datos.getProyectosAbiertos()));
				centerPanel.add(underPanel);
			}
		});
		panel.add(abiertos);

		Anchor adjudicados = new Anchor(constants.adjudicados());
		adjudicados.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected = null;
				accion = true;
				proyCerrados = true;
				VerticalPanel vCerr = new VerticalPanel();
				vCerrados = new VerticalPanel();
				vCerr.add(new ProjectTable(datos.getProyectosCerrados()));
				setLinkCalificacion();
				centerPanel.clear();
				centerPanel.add(vCerr);
				centerPanel.add(underPanel);
				eastPanel.setWidth("100%");
			}
		});
		panel.add(adjudicados);

		Anchor perdidos = new Anchor(constants.perdidos());
		perdidos.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected = null;
				accion = false;
				proyCerrados = false;
				centerPanel.clear();
				centerPanel.add(new ProjectTable(datos.getProyectosPerdidos()));
				centerPanel.add(underPanel);
			}
		});
		panel.add(perdidos);

		Anchor cancelados = new Anchor(constants.cancelados());
		cancelados.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected = null;
				accion = false;
				proyCerrados = false;
				centerPanel.clear();
				centerPanel.add(new ProjectTable(datos.getProyectosCancelados()));
				centerPanel.add(underPanel);
			}
		});
		panel.add(cancelados);

		Anchor sinCalificar = new Anchor(constants.pendientesCalif());
		sinCalificar.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected = null;
				accion = true;
				proyCerrados = false;
				VerticalPanel v = new VerticalPanel();
				v.add(new ProjectTable(datos.getProyectosSinCalificar()));
				eastPanel.add(getCalificarAction());
				eastPanel.setWidth("100%");
				centerPanel.clear();
				centerPanel.add(v);
				centerPanel.add(underPanel);

			}
		});
		panel.add(sinCalificar);

		Anchor sinRecibirCalif = new Anchor(constants.pendientesRecibirCalif(), true);
		sinRecibirCalif.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected = null;
				accion = false;
				proyCerrados = false;
				centerPanel.clear();
				centerPanel.add(new ProjectTable(datos.getProyectosSinRecibirCalif()));
				centerPanel.add(underPanel);
			}
		});
		panel.add(sinRecibirCalif);

		panel.setWidth("50px");
		panel.addStyleName("hl");
		for (Moneda ganancia : datos.getGananciaAcumulada().keySet())
			underPanel.add(new HTML(constants.miReputacionVendedor() + String.valueOf(datos.getReputacion())
					+ " </p>" + constants.gananciaAcumulada()
					+ String.valueOf(datos.getGananciaAcumulada().get(ganancia)) + constants.preposicionEn() + ganancia.getDescription()
					+ " </p> "));
		underPanel.setVerticalAlignment(ALIGN_MIDDLE);
		return panel;
	}
}
