package edu.tdp2.client.widgets;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.OfertaWidget;
import edu.tdp2.client.ProjectList;
import edu.tdp2.client.dto.MyAccountDto;
import edu.tdp2.client.dto.MySpecificAccount;
import edu.tdp2.client.dto.MyVendedorAccount;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;
import edu.tdp2.client.utils.OneParamDelegate;

public class MyAccountWidget extends NavigablePanel
{
	public class EarningsMap extends VerticalPanel
	{
		public EarningsMap(Map<Moneda, Double> gananciaAcumulada)
		{
			for (Moneda moneda : gananciaAcumulada.keySet())
				add(new HTML(moneda.getDescription() + ": " + gananciaAcumulada.get(moneda)));
		}
	}

	private FlexTable tableDatos = new FlexTable();
	private FlexTable tableComp = new FlexTable();

	private FlexTable tableVend = new FlexTable();

	private OneParamDelegate<Proyecto> onShowOwnOfertaDelegate = new OneParamDelegate<Proyecto>()
	{
		public void invoke(Proyecto p)
		{
			onShowOwnOferta(p);
		}
	};

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

	public void onModuleLoad()
	{

	}

	protected void onShowOwnOferta(Proyecto proyectoActual)
	{
		putAlone(new OfertaWidget(proyectoActual, LoginWidget.getCurrentUser()));
	}

	private void addRow(FlexTable table, Widget... widgets)
	{
		int row = table.getRowCount();
		for (int i = 0; i < widgets.length; i++)
			table.setWidget(row, i, widgets[i]);
		table.getWidget(row, 0).setWidth("200px");
	}

	private void addRowComp(Widget... widgets)
	{
		addRow(tableComp, widgets);
	}

	private void addRowDatos(Widget... widgets)
	{
		addRow(tableDatos, widgets);
	}

	private void addRowVend(Widget... widgets)
	{
		addRow(tableVend, widgets);
	}

	private Widget getModificationAnchor(final MyAccountDto dto)
	{
		Anchor anchor = new Anchor("Modificar");
		anchor.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				putAlone(new PersonalModificationWidget(dto));
			}
		});
		return anchor;
	}

	private Widget getOwnOfferAnchorForProjects(ProjectList projs)
	{
		Anchor anchor = new Anchor("Ver mi oferta");
		anchor.addClickHandler(getOwnOfferHandlerFormProjectList(projs));
		return anchor;
	}

	private ClickHandler getOwnOfferHandlerFormProjectList(final ProjectList projects)
	{
		return ClientUtils.getHandlerForProjects(projects, onShowOwnOfertaDelegate);
	}

	private void init(MyAccountDto dto)
	{

		FlexTable table = new FlexTable();
		table.setCellPadding(10);
		addRow(table, new HTML("<b>Datos de mi cuenta</b>"), ClientUtils.getBackAnchor());
		add(table);

		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setAnimationEnabled(true);
		tabPanel.setPixelSize(getAbsoluteLeft() + getOffsetWidth() - 40, getAbsoluteTop() + getOffsetHeight());

		tabPanel.getDeckPanel().addStyleName("dock");

		tableDatos.setCellPadding(10);
		tableComp.setCellPadding(10);
		tableVend.setCellPadding(10);

		addRowDatos(getModificationAnchor(dto));

		addRowDatos(new HTML("Nombre"), new HTML(dto.getNombre()));
		addRowDatos(new HTML("Apellido"), new HTML(dto.getApellido()));
		addRowDatos(new HTML("E-mail"), new HTML(dto.getEmail()));
		addRowDatos(new HTML("Usuario"), new HTML(dto.getUsuario()));
		addRowDatos(new HTML("Pais"), new HTML(dto.getPais()));
		addRowDatos(new HTML("Ciudad"), new HTML(dto.getCiudad()));
		addRowDatos(new HTML("Codigo Postal"), new HTML(dto.getCodigoPostal().toString()));
		addRowDatos(new HTML("Descripcion"), new HTML(dto.getDescripcion().toString()));
		addRowDatos(new HTML("Mi categor&iacute;a como usuario de Softmart"), new HTML(dto.getNivel().toString()));

		MySpecificAccount comprador = dto.getDatosComprador();
		addRowComp(new HTML("Reputación como comprador"), new HTML(((Double) comprador.getReputacion()).toString()));

		ProjectList projs = new ProjectList(comprador.getProyectosSinRecibirCalif());
		addRowComp(new HTML("Proyectos propios con ofertas aceptadas por mí donde no recibí calificación"), projs,
				getAnchorForProjects(projs), getOfferAnchorForProjects(projs));

		projs = new ProjectList(comprador.getProyectosSinCalificar());
		addRowComp(new HTML("Proyectos propios pendientes de calificar"), projs, getAnchorForProjects(projs),
				getOfferAnchorForProjects(projs));

		projs = new ProjectList(comprador.getProyectosCerrados());
		addRowComp(new HTML("Proyectos propios cerrados"), projs, getAnchorForProjects(projs),
				getOfferAnchorForProjects(projs));

		projs = new ProjectList(comprador.getProyectosCancelados());
		addRowComp(new HTML("Proyectos propios cancelados"), projs, getAnchorForProjects(projs));

		projs = new ProjectList(comprador.getProyectosAbiertos());
		addRowComp(new HTML("Proyectos propios abiertos"), projs, getAnchorForProjects(projs));

		MyVendedorAccount vendedor = dto.getDatosVendedor();
		addRowVend(new HTML("Reputación como vendedor"), new HTML(((Double) vendedor.getReputacion()).toString()));

		projs = new ProjectList(vendedor.getProyectosSinRecibirCalif());
		addRowVend(new HTML("Proyectos adjudicados a mí donde no recibí calificación"), projs,
				getAnchorForProjects(projs), getOfferAnchorForProjects(projs));

		projs = new ProjectList(vendedor.getProyectosSinCalificar());
		addRowVend(new HTML("Proyectos adjudicados a mí pendientes de calificar"), projs, getAnchorForProjects(projs),
				getOfferAnchorForProjects(projs));

		projs = new ProjectList(vendedor.getProyectosCerrados());
		addRowVend(new HTML("Proyectos cerrados adjudicados a mí"), projs, getAnchorForProjects(projs),
				getOfferAnchorForProjects(projs));

		projs = new ProjectList(vendedor.getProyectosCancelados());
		addRowVend(new HTML("Proyectos cancelados adjudicados a mí"), projs, getAnchorForProjects(projs));

		addRowVend(new HTML("Ganancia acumulada"), new EarningsMap(vendedor.getGananciaAcumulada()));

		projs = new ProjectList(vendedor.getProyectosAbiertos());
		// History.addValueChangeHandler(new MyAccountHistoryHandler());
		addRowVend(new HTML("Proyectos con ofertas abiertas"), projs, getAnchorForProjects(projs),
				getOwnOfferAnchorForProjects(projs));

		tabPanel.add(tableDatos, "Datos Personales");
		tabPanel.add(new MyCompradorAccountWidget(dto.getDatosComprador()), "Datos Comprador");
		tabPanel.add(new MyVendedorAccountWidget(dto.getDatosVendedor()), "Datos Vendedor");
		tabPanel.selectTab(0);

		add(tabPanel);

	}

	/*
	 * private class MyAccountHistoryHandler implements ValueChangeHandler<String> { public void
	 * onValueChange(ValueChangeEvent<String> event) { HistoryToken token; try { token =
	 * HistoryToken.valueOf(event.getValue()); } catch (IllegalArgumentException e) { return; }
	 * 
	 * switch (token) { case onShowOfertaMyAccount: onShowOferta(); break; case onShowOwnOferta: //onShowOwnOferta();
	 * getOwnOfferAnchorForProjects(); break; } } }
	 */

}
