package edu.tdp2.client.widgets;

import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.ProjectList;
import edu.tdp2.client.dto.MyAccountDto;
import edu.tdp2.client.dto.MyCompradorAccount;
import edu.tdp2.client.dto.MyVendedorAccount;
//import edu.tdp2.client.dto.MyAccountDto.MyCompradorAccount;
//import edu.tdp2.client.dto.MyAccountDto.MyVendedorAccount;
import edu.tdp2.client.model.Moneda;
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
		addRow(new HTML("Pais"), new HTML(dto.getPais()));
		addRow(new HTML("E-mail"), new HTML(dto.getEmail()));
		addRow(new HTML("Usuario"), new HTML(dto.getUsuario()));
		addRow(new HTML("Ciudad"), new HTML(dto.getCiudad()));
		addRow(new HTML("Nivel"), new HTML(dto.getNivel().toString()));

		MyCompradorAccount comprador = dto.getDatosComprador();
		addRow(new HTML("Reputación como comprador"), new HTML(((Float) comprador.getReputacion()).toString()));
		addRow(new HTML("Proyectos con ofertas aceptadas por mí donde no recibí calificación"), new ProjectList(
				comprador.getProyectosSinRecibirCalif()));
		addRow(new HTML("Proyectos propios pendientes de calificar"), new ProjectList(comprador
				.getProyectosSinCalificar()));
		addRow(new HTML("Proyectos propios cerrados"), new ProjectList(comprador.getProyectosCerrados()));
		addRow(new HTML("Proyectos propios cancelados"), new ProjectList(comprador.getProyectosCancelados()));
		addRow(new HTML("Proyectos propios abiertos"), new ProjectList(comprador.getProyectosAbiertos()));

		MyVendedorAccount vendedor = dto.getDatosVendedor();
		addRow(new HTML("Reputación como vendedor"), new HTML(((Float) vendedor.getReputacion()).toString()));
		addRow(new HTML("Proyectos adjudicados a mí donde no recibí calificación"), new ProjectList(vendedor
				.getProyectosSinRecibirCalif()));
		addRow(new HTML("Proyectos adjudicados a mí pendientes de calificar"), new ProjectList(vendedor
				.getProyectosSinCalificar()));
		addRow(new HTML("Proyectos cerrados adjudicados a mí"), new ProjectList(vendedor.getProyectosCerrados()));
		addRow(new HTML("Proyectos cancelados adjudicados a mí"), new ProjectList(vendedor.getProyectosCancelados()));
		addRow(new HTML("Ganancia acumulada"), new EarningsMap(vendedor.getGananciaAcumulada()));
		addRow(new HTML("Ofertas abiertas"), new ProjectList(vendedor.getProyectosConOfertasAbiertas()));

		add(table);
	}

	private void addRow(Widget... widgets)
	{
		int row = table.getRowCount();
		for (int i = 0; i < widgets.length; i++)
			table.setWidget(row, i, widgets[i]);
	}

	public class EarningsMap extends Widget
	{
		public EarningsMap(Map<Moneda, Float> gananciaAcumulada)
		{
			VerticalPanel panel = new VerticalPanel();
			for (Moneda moneda : gananciaAcumulada.keySet())
				panel.add(new HTML(moneda.getDescription() + ": " + gananciaAcumulada.get(moneda)));
		}
	}
}
