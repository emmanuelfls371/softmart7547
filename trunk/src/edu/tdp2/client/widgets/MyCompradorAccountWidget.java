package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.ProjectWidget;
import edu.tdp2.client.dto.MyCompradorAccount;
import edu.tdp2.client.model.Presupuesto;
import edu.tdp2.client.model.Proyecto;

public class MyCompradorAccountWidget extends DockPanel
{
	private SimplePanel centerPanel = new SimplePanel();
	private final MyCompradorAccount datos;

	public MyCompradorAccountWidget(MyCompradorAccount datos)
	{
		this.datos = datos;
		add(getWestPanel(), WEST);
		add(centerPanel, CENTER);

		centerPanel.setWidth("100%");
	}

	private Widget getWestPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(10);

		panel.add(new HTML("<big><b>Mis proyectos</b></big>"));

		Anchor abiertos = new Anchor("Abiertos");
		abiertos.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				centerPanel.setWidget(new ProjectTable(datos.getProyectosAbiertos()));
			}
		});
		panel.add(abiertos);

		Anchor cerrados = new Anchor("Cerrados");
		cerrados.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				centerPanel.setWidget(new ProjectTable(datos.getProyectosCerrados()));
			}
		});
		panel.add(cerrados);

		Anchor cancelados = new Anchor("Cancelados");
		cancelados.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				centerPanel.setWidget(new ProjectTable(datos.getProyectosCancelados()));
			}
		});
		panel.add(cancelados);

		Anchor sinCalificar = new Anchor("Pendientes a calificar");
		sinCalificar.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				centerPanel.setWidget(new ProjectTable(datos.getProyectosSinCalificar()));
			}
		});
		panel.add(sinCalificar);

		Anchor sinRecibirCalif = new Anchor("Pendientes de recibir calificaci&oacute;n", true);
		sinRecibirCalif.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				centerPanel.setWidget(new ProjectTable(datos.getProyectosSinRecibirCalif()));
			}
		});
		panel.add(sinRecibirCalif);

		panel.setWidth("50px");
		return panel;
	}

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
			setWidget(0, 0, new HTML("Nombre"));
			setWidget(0, 1, new HTML("Presupuesto"));
			setWidget(0, 2, new HTML("Moneda"));
			setWidget(0, 3, new HTML("Tama&ntilde;o"));
			setWidget(0, 4, new HTML("Complejidad"));
			setWidget(0, 5, new HTML("Fecha cierre"));

			int row = 1;
			for (final Proyecto proyecto : proyectos)
			{
				Anchor aProy = new Anchor(proyecto.getDescripcion());
				aProy.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						centerPanel.setWidget(new ProjectWidget(proyecto));
					}
				});
				setWidget(row, 0, aProy);

				setWidget(row, 1, new HTML(Presupuesto.armarRango(proyecto.getMinPresupuesto(), proyecto.getMaxPresupuesto())));
				setWidget(row, 2, new HTML(proyecto.getMoneda().getDescription()));
				setWidget(row, 3, new HTML(proyecto.getTamanio()));
				setWidget(row, 4, new HTML(proyecto.getDificultad()));
				DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
				setWidget(row, 5, new HTML(format.format(proyecto.getFecha())));

				row++;
			}
		}
	}
}
