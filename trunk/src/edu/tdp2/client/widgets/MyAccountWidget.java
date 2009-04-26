package edu.tdp2.client.widgets;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.OfertaWidget;
import edu.tdp2.client.ProjectList;
import edu.tdp2.client.ProjectWidget;
import edu.tdp2.client.dto.MyAccountDto;
import edu.tdp2.client.dto.MyCompradorAccount;
import edu.tdp2.client.dto.MyVendedorAccount;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class MyAccountWidget extends SimplePanel
{
	FlexTable table = new FlexTable();

	public MyAccountWidget(String usuario)
	{
		super();
		AsyncCallback<MyAccountDto> callback = new AsyncCallback<MyAccountDto>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudieron recuperar los datos de Mi cuenta");
			}

			public void onSuccess(MyAccountDto dto)
			{
				init(dto);
			}
		};
		ClientUtils.getSoftmartService().getMyAccountData(usuario, callback);
	}

	private void init(MyAccountDto dto)
	{
		table.setCellPadding(10);
		addRow(new HTML("<b>Datos de mi cuenta</b>"), ClientUtils.getBackAnchor());
		addRow(new HTML("Nombre"), new HTML(dto.getNombre()));
		addRow(new HTML("Apellido"), new HTML(dto.getApellido()));
		addRow(new HTML("Pais"), new HTML(dto.getPais()));
		addRow(new HTML("E-mail"), new HTML(dto.getEmail()));
		addRow(new HTML("Usuario"), new HTML(dto.getUsuario()));
		addRow(new HTML("Ciudad"), new HTML(dto.getCiudad()));
		addRow(new HTML("Nivel"), new HTML(dto.getNivel().toString()));

		MyCompradorAccount comprador = dto.getDatosComprador();
		addRow(new HTML("Reputación como comprador"), new HTML(((Double) comprador.getReputacion()).toString()));

		ProjectList projs = new ProjectList(comprador.getProyectosSinRecibirCalif());
		addRow(new HTML("Proyectos propios con ofertas aceptadas por mí donde no recibí calificación"), projs,
				getAnchorForProjects(projs), getOfferAnchorForProjects(projs));

		projs = new ProjectList(comprador.getProyectosSinCalificar());
		addRow(new HTML("Proyectos propios pendientes de calificar"), projs, getAnchorForProjects(projs),
				getOfferAnchorForProjects(projs));

		projs = new ProjectList(comprador.getProyectosCerrados());
		addRow(new HTML("Proyectos propios cerrados"), projs, getAnchorForProjects(projs),
				getOfferAnchorForProjects(projs));

		projs = new ProjectList(comprador.getProyectosCancelados());
		addRow(new HTML("Proyectos propios cancelados"), projs, getAnchorForProjects(projs));

		projs = new ProjectList(comprador.getProyectosAbiertos());
		addRow(new HTML("Proyectos propios abiertos"), projs, getAnchorForProjects(projs));

		MyVendedorAccount vendedor = dto.getDatosVendedor();
		addRow(new HTML("Reputación como vendedor"), new HTML(((Double) vendedor.getReputacion()).toString()));

		projs = new ProjectList(vendedor.getProyectosSinRecibirCalif());
		addRow(new HTML("Proyectos adjudicados a mí donde no recibí calificación"), projs, getAnchorForProjects(projs),
				getOfferAnchorForProjects(projs));

		projs = new ProjectList(vendedor.getProyectosSinCalificar());
		addRow(new HTML("Proyectos adjudicados a mí pendientes de calificar"), projs, getAnchorForProjects(projs),
				getOfferAnchorForProjects(projs));

		projs = new ProjectList(vendedor.getProyectosCerrados());
		addRow(new HTML("Proyectos cerrados adjudicados a mí"), projs, getAnchorForProjects(projs),
				getOfferAnchorForProjects(projs));

		projs = new ProjectList(vendedor.getProyectosCancelados());
		addRow(new HTML("Proyectos cancelados adjudicados a mí"), projs, getAnchorForProjects(projs));

		addRow(new HTML("Ganancia acumulada"), new EarningsMap(vendedor.getGananciaAcumulada()));

		projs = new ProjectList(vendedor.getProyectosConOfertasAbiertas());
		addRow(new HTML("Proyectos con ofertas abiertas"), projs, getAnchorForProjects(projs),
				getOwnOfferAnchorForProjects(projs));

		add(table);
	}

	private void addRow(Widget... widgets)
	{
		int row = table.getRowCount();
		for (int i = 0; i < widgets.length; i++)
			table.setWidget(row, i, widgets[i]);
		table.getWidget(row, 0).setWidth("200px");
	}

	public class EarningsMap extends VerticalPanel
	{
		public EarningsMap(Map<Moneda, Long> gananciaAcumulada)
		{
			for (Moneda moneda : gananciaAcumulada.keySet())
				add(new HTML(moneda.getDescription() + ": " + gananciaAcumulada.get(moneda)));
		}
	}

	private Anchor getAnchorForProjects(final ProjectList projects)
	{
		Anchor anchor = new Anchor("Ver proyecto");
		anchor.addClickHandler(getHandlerFormProjectList(projects));
		return anchor;
	}

	private Anchor getOfferAnchorForProjects(final ProjectList projects)
	{
		Anchor anchor = new Anchor("Ver oferta ganadora");
		anchor.addClickHandler(getOfferHandlerFormProjectList(projects));
		return anchor;
	}

	private Widget getOwnOfferAnchorForProjects(ProjectList projects)
	{
		Anchor anchor = new Anchor("Ver mi oferta");
		anchor.addClickHandler(getOwnOfferHandlerFormProjectList(projects));
		return anchor;
	}

	private ClickHandler getOwnOfferHandlerFormProjectList(final ProjectList projects)
	{
		return new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Proyecto proyecto = projects.getSelectedItem();
				if (proyecto == null)
					Window.alert("Debe seleccionar un proyecto");
				else
					onShowOwnOferta(proyecto);
			}
		};
	}

	private ClickHandler getOfferHandlerFormProjectList(final ProjectList projects)
	{
		return new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Proyecto proyecto = projects.getSelectedItem();
				if (proyecto == null)
					Window.alert("Debe seleccionar un proyecto");
				else
					onShowOferta(proyecto);
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

	private void onShowProyecto(Proyecto project)
	{
		putAlone(new ProjectWidget(project));
	}

	protected void onShowOferta(Proyecto project)
	{
		putAlone(new OfertaWidget(project));
	}

	protected void onShowOwnOferta(Proyecto project)
	{
		putAlone(new OfertaWidget(project, LoginWidget.getCurrentUser()));
	}

	private void putAlone(Widget widget)
	{
		clear();
		add(widget);
	}
}
