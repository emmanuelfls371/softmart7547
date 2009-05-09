package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.model.Presupuesto;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;


public class ProjectWidget extends VerticalPanel
{

	private Proyecto project;
	private FlexTable table = new FlexTable();

	public ProjectWidget(Proyecto project)
	{
		this.project = project;
		load();
	}

	protected native void reload() /*-{
	   $wnd.location.reload();
	  }-*/;

	@SuppressWarnings("deprecation")
	private void load()
	{
		final HTML h = new HTML(project.getUsuario().getLogin());
		
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar el usuario");
			}

			public void onSuccess(Boolean isBloqueado)
			{
				if(isBloqueado){
					h.addStyleName("blocked");
					h.setStyleName("blocked");
				}
			}
		};
		ClientUtils.getSoftmartService().isUsuarioBloqueado(project.getUsuario().getLogin(), callback);
		
		
		
		add(new Label("Proyecto " + project.getNombre()));
		table.clear();
		add(table);
		table.setWidget(0, 0, new HTML("Comprador"));
		table.setWidget(0, 1, new HTML("Presupuesto"));
		table.setWidget(0, 2, new HTML("Moneda"));
		table.setWidget(0, 3, new HTML("Fecha de cierre"));
		table.setWidget(0, 4, new HTML("Nivel de reputaci&oacute;n"));
		table.setWidget(0, 5, new HTML("Dificultad"));
		table.setWidget(0, 6, new HTML("Tama&ntilde;o"));
		table.setWidget(0, 7, new HTML("Descripci&oacute;n"));
		table.setWidget(0, 8, new HTML("Archivo"));
		table.setWidget(0, 9, new HTML("¿Cancelado?"));

		int row = 1;
		table.setWidget(row, 0, h);
		table.setWidget(row, 1, new HTML(Presupuesto.armarRango(project.getMinPresupuesto(), project
				.getMaxPresupuesto())));
		table.setWidget(row, 2, new HTML(project.getMoneda().getDescription()));
		table.setWidget(row, 3, new HTML(String.valueOf(project.getFecha().getDate()) + "/"
				+ String.valueOf(project.getFecha().getMonth() + 1) + "/"
				+ String.valueOf(project.getFecha().getYear() + 1900)));
		table.setWidget(row, 4, new HTML(project.getNivel()));
		table.setWidget(row, 5, new HTML(project.getDificultad()));
		table.setWidget(row, 6, new HTML(project.getTamanio()));
		table.setWidget(row, 7, new HTML(project.getDescripcion()));
		if (project.getPathArchivo() != null && !project.getPathArchivo().isEmpty())
			table.setWidget(row, 8, new Anchor("Bajar"));
		else
			table.setWidget(row, 8, new HTML("No se ha cargado un archivo"));
		if(project.isCancelado())
			table.setWidget(row, 9, new HTML("Si"));
		else
			table.setWidget(row, 9, new HTML("No"));
		table.setWidget(table.getRowCount(), 2, ClientUtils.getBackAnchor());
	}

}
