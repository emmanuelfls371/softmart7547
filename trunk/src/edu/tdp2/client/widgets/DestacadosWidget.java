package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

import edu.tdp2.client.ProjectWidget;

import edu.tdp2.client.model.Presupuesto;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class DestacadosWidget extends SimplePanel
{
	
	public DestacadosWidget(){
		load();
	}
	
	private void load()
	{
		
		AsyncCallback<List<Proyecto>> callback = new AsyncCallback<List<Proyecto>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar el proyecto destacado");
			}

			public void onSuccess(List<Proyecto> proyectos)
			{
				FlexTable table = new FlexTable();
		
				table.setWidget(0, 0, new HTML("Nombre"));
				table.setWidget(0, 1, new HTML("Presupuesto"));
				table.setWidget(0, 2, new HTML("Moneda"));
				table.setWidget(0, 3, new HTML("Tama&ntilde;o"));
				table.setWidget(0, 4, new HTML("Complejidad"));
				table.setWidget(0, 5, new HTML("Fecha cierre"));

				int row = 1;
				for (final Proyecto proyecto : proyectos)
				{
					Anchor aProy = new Anchor(proyecto.getNombre());
					aProy.addClickHandler(new ClickHandler()
					{
						public void onClick(ClickEvent event)
						{
							setWidget(new ProjectWidget(proyecto));
						}
					});
					table.setWidget(row, 0, aProy);
		
					table.setWidget(row, 1, new HTML(Presupuesto.armarRango(proyecto.getMinPresupuesto(), proyecto.getMaxPresupuesto())));
					table.setWidget(row, 2, new HTML(proyecto.getMoneda().getDescription()));
					table.setWidget(row, 3, new HTML(proyecto.getTamanio()));
					table.setWidget(row, 4, new HTML(proyecto.getDificultad()));
					DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
					table.setWidget(row, 5, new HTML(format.format(proyecto.getFecha())));

					row++;
				}
				
				setWidget(table);
			}
		};
		ClientUtils.getSoftmartService().getProyectosDestacados(callback);
		
	}

}
