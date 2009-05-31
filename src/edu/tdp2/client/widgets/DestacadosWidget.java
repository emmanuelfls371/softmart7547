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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.model.Presupuesto;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class DestacadosWidget extends SimplePanel
{
	private DestacadosConstants constants;

	public DestacadosWidget()
	{
		constants = GWT.create(DestacadosConstants.class);
		load();
	}

	private void load()
	{

		AsyncCallback<List<Proyecto>> callback = new AsyncCallback<List<Proyecto>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failGetProyDestacado());
			}

			public void onSuccess(List<Proyecto> proyectos)
			{
				VerticalPanel panel = new VerticalPanel();
				panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				panel.add(new HTML(constants.proyectosDestacados()));

				FlexTable table = new FlexTable();
				table.addStyleName("table");

				table.getCellFormatter().addStyleName(0, 0, "firstRow");
				table.getCellFormatter().addStyleName(0, 1, "firstRow");
				table.getCellFormatter().addStyleName(0, 2, "firstRow");
				table.getCellFormatter().addStyleName(0, 3, "firstRow");
				table.getCellFormatter().addStyleName(0, 4, "firstRow");
				table.getCellFormatter().addStyleName(0, 5, "firstRow");

				table.setWidget(0, 0, new HTML(constants.nombre()));
				table.setWidget(0, 1, new HTML(constants.presupuesto()));
				table.setWidget(0, 2, new HTML(constants.moneda()));
				table.setWidget(0, 3, new HTML(constants.tamano()));
				table.setWidget(0, 4, new HTML(constants.complejidad()));
				table.setWidget(0, 5, new HTML(constants.fechaCierre()));

				int row = 1;
				for (final Proyecto proyecto : proyectos)
				{
					Anchor aProy = new Anchor(proyecto.getNombre());
					aProy.addClickHandler(new ClickHandler()
					{
						public void onClick(ClickEvent event)
						{
							setWidget(new DetailSearchWidget(proyecto));
						}
					});
					table.setWidget(row, 0, aProy);

					table.setWidget(row, 1, new HTML(Presupuesto.armarRango(proyecto.getMinPresupuesto(), proyecto
							.getMaxPresupuesto())));
					table.setWidget(row, 2, new HTML(proyecto.getMoneda().getDescription()));
					table.setWidget(row, 3, new HTML(proyecto.getTamanio()));
					table.setWidget(row, 4, new HTML(proyecto.getDificultad()));
					DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
					table.setWidget(row, 5, new HTML(format.format(proyecto.getFecha())));

					table.getCellFormatter().addStyleName(row, 0, "column");
					table.getCellFormatter().addStyleName(row, 1, "column");
					table.getCellFormatter().addStyleName(row, 2, "column");
					table.getCellFormatter().addStyleName(row, 3, "column");
					table.getCellFormatter().addStyleName(row, 4, "column");
					table.getCellFormatter().addStyleName(row, 5, "column");

					row++;

				}
				panel.add(table);
				setWidget(panel);

			}
		};
		ClientUtils.getSoftmartService().getProyectosDestacados(callback);

	}

}
