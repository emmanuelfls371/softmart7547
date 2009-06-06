package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.OfertaWidget;
import edu.tdp2.client.OffersWidget;
import edu.tdp2.client.ProjectWidget;
import edu.tdp2.client.dto.MyCompradorAccount;
import edu.tdp2.client.model.Presupuesto;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class MyCompradorAccountWidget extends AccountWidget
{

	public class ProjectTable extends FlexTable
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

			setWidget(0, 0, new HTML(constants.nombre()));
			setWidget(0, 1, new HTML(constants.presupuesto()));
			setWidget(0, 2, new HTML(constants.moneda()));
			setWidget(0, 3, new HTML(constants.tamano()));
			setWidget(0, 4, new HTML(constants.complejidad()));
			setWidget(0, 5, new HTML(constants.fechaCierra()));

			if (accion)
			{
				setWidget(0, 6, new HTML(constants.accion()));
				getCellFormatter().addStyleName(0, 6, "firstRow");
			}

			int row = 1;
			for (final Proyecto proyecto : proyectos)
			{
				Anchor aProy = new Anchor(proyecto.getNombre());
				aProy.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						centerPanel.clear();
						centerPanel.add(new ProjectWidget(proyecto, true));
					}
				});
				setWidget(row, 0, aProy);

				setWidget(row, 1, new HTML(Presupuesto.armarRango(proyecto.getMinPresupuesto(), proyecto
						.getMaxPresupuesto())));
				setWidget(row, 2, new HTML(proyecto.getMoneda().getDescription()));
				setWidget(row, 3, new HTML(proyecto.getTamanio()));
				setWidget(row, 4, new HTML(proyecto.getDificultad()));
				DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
				setWidget(row, 5, new HTML(format.format(proyecto.getFecha())));
				if (accion)
					if (proyecto.isRevisado())
					{
						setWidget(row, 6, getActionButton(proyecto));
						getCellFormatter().addStyleName(row, 6, "column");
					}
					else
					{
						setWidget(row, 6, new HTML(constants.pendienteAprob()));
						getCellFormatter().addStyleName(row, 6, "column");
					}
				for (int i = 0; i < 6; i++)
					getCellFormatter().addStyleName(row, i, "column");
				row++;
			}
		}
	}

	private final MyCompradorAccount datos;
	private MyCompradorAccountConstants constants;
	protected HorizontalPanel underPanel = new HorizontalPanel();

	public MyCompradorAccountWidget(MyCompradorAccount datos)
	{
		this.datos = datos;
		constants = GWT.create(MyCompradorAccountConstants.class);
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

	protected void onShowOffers(Proyecto projectOferta)
	{
		putAlone(new OffersWidget(projectOferta));
	}

	private Widget getWestPanel()
	{
		underPanel.clear();
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(10);

		panel.add(new HTML(constants.misProyectos()));

		Anchor abiertos = new Anchor(constants.abiertos());

		abiertos.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				centerPanel.clear();
				eastPanel.clear();
				proySelected = null;
				accion = true;
				proyCerrados = false;
				Anchor menuLink = new Anchor(constants.verOfertas());
				menuLink.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						Proyecto proyecto = proySelected;
						if (proyecto == null)
							Window.alert(constants.debeSelecProyecto());
						else
							onShowOffers(proyecto);
					}
				});

				Anchor menuLink2 = new Anchor(constants.cancelarProy());
				menuLink2.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						if (proySelected == null)
							Window.alert(constants.debeSelecProyectoCancel());
						else if (Window.confirm(constants.confirmaCancelar()))
						{
							AsyncCallback<String> projectCallback = new AsyncCallback<String>()
							{
								public void onFailure(Throwable caught)
								{
									Window.alert(constants.failCancelProject());
								}

								public void onSuccess(String errMsg)
								{
									if (errMsg != null)
										Window.alert(errMsg);
									else
									{
										datos.getProyectosAbiertos().remove(proySelected);
										Window.alert(constants.proyCancelado());
									}
								}
							};
							ClientUtils.getSoftmartService().cancelarProyecto(proySelected.getId(), projectCallback);
						}
					}
				});
				centerPanel.clear();
				VerticalPanel v = new VerticalPanel();
				v.add(new ProjectTable(datos.getProyectosAbiertos()));
				VerticalPanel v2 = new VerticalPanel();
				v2.add(menuLink);
				v2.add(menuLink2);
				eastPanel.add(v2);
				centerPanel.add(v);
				centerPanel.add(underPanel);
				eastPanel.setWidth("100%");

			}
		});

		panel.add(abiertos);

		Anchor cerrados = new Anchor(constants.cerrados());
		cerrados.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected = null;
				accion = true;
				proyCerrados = true;
				vCerrados = new VerticalPanel();
				VerticalPanel vCerr = new VerticalPanel();

				vCerr.add(new ProjectTable(datos.getProyectosCerrados()));

				Anchor ofertaG = new Anchor(constants.verOfertaGanadora());
				ofertaG.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						Proyecto proyecto = proySelected;
						if (proyecto == null)
							Window.alert(constants.debeSelecProyectoVerOferta());
						else
							onShowOferta(proyecto);
					}
				});

				vCerrados.add(ofertaG);
				setLinkCalificacion();
				centerPanel.clear();
				centerPanel.add(vCerr);
				centerPanel.add(underPanel);
				eastPanel.setWidth("100%");
			}
		});
		panel.add(cerrados);

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

		Anchor sinCalificar = new Anchor(constants.pendientesCalificar());
		sinCalificar.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected = null;
				accion = true;
				VerticalPanel v = new VerticalPanel();
				v.add(new ProjectTable(datos.getProyectosSinCalificar()));
				Anchor w = getCalificarAction();
				w.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
				eastPanel.add(w);
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
				VerticalPanel v = new VerticalPanel();
				v.add(new ProjectTable(datos.getProyectosSinRecibirCalif()));
				centerPanel.clear();
				centerPanel.add(v);
				centerPanel.add(underPanel);
			}
		});
		panel.add(sinRecibirCalif);

		panel.setWidth("50px");
		panel.addStyleName("hl");
		String promedio;
		if (datos.getReputacion() > 0)
			promedio = String.valueOf(datos.getReputacion());
		else
			promedio = "-";
		HTML rep = new HTML(constants.miReputacionComprador() + promedio + " </p>");
		underPanel.add(rep);
		return panel;
	}

	private void onShowOferta(Proyecto proy)
	{
		putAlone(new OfertaWidget(proy));
	}
}
