package edu.tdp2.client.widgets;

import java.util.Map;

import com.google.gwt.core.client.GWT;
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
import edu.tdp2.client.dto.MyAccountDto;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

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
	private MyAccountConstants constants;

	public MyAccountWidget(String usuario)
	{
		super();

		constants = GWT.create(MyAccountConstants.class);
		AsyncCallback<MyAccountDto> callback = new AsyncCallback<MyAccountDto>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failGetMyAccount());
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

	private void addRowDatos(Widget... widgets)
	{
		addRow(tableDatos, widgets);
	}

	private Widget getModificationAnchor(final MyAccountDto dto)
	{
		Anchor anchor = new Anchor(constants.modificar());
		anchor.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				putAlone(new PersonalModificationWidget(dto));
			}
		});
		return anchor;
	}

	private void init(MyAccountDto dto)
	{

		FlexTable table = new FlexTable();
		table.setCellPadding(10);
		addRow(table, new HTML(constants.datosDeMiCuenta()), ClientUtils.getBackAnchor());
		add(table);

		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setAnimationEnabled(true);
		tabPanel.setPixelSize(getAbsoluteLeft() + getOffsetWidth() - 40, getAbsoluteTop() + getOffsetHeight());

		tabPanel.getDeckPanel().addStyleName("dock");

		tableDatos.setCellPadding(10);
		tableComp.setCellPadding(10);
		tableVend.setCellPadding(10);

		addRowDatos(getModificationAnchor(dto));

		addRowDatos(new HTML(constants.nombre()), new HTML(dto.getNombre()));
		addRowDatos(new HTML(constants.apellido()), new HTML(dto.getApellido()));
		addRowDatos(new HTML(constants.email()), new HTML(dto.getEmail()));
		addRowDatos(new HTML(constants.usuario()), new HTML(dto.getUsuario()));
		addRowDatos(new HTML(constants.pais()), new HTML(dto.getPais()));
		addRowDatos(new HTML(constants.ciudad()), new HTML(dto.getCiudad()));
		addRowDatos(new HTML(constants.codPostal()), new HTML(dto.getCodigoPostal().toString()));
		addRowDatos(new HTML(constants.descripPerfil()), new HTML(dto.getDescripcion().toString()));
		addRowDatos(new HTML(constants.categoria()), new HTML(dto.getNivel().toString()));

		tabPanel.add(tableDatos, constants.datosPersonales());
		tabPanel.add(new MyCompradorAccountWidget(dto.getDatosComprador()), constants.datosComprador());
		tabPanel.add(new MyVendedorAccountWidget(dto.getDatosVendedor()), constants.datosVendedor());
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
