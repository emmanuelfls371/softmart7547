package edu.tdp2.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.model.Presupuesto;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class ProjectWidget extends VerticalPanel
{

	private Proyecto project;
	private FlexTable table = new FlexTable();
	private boolean bloqueado = false;
	
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
		bloqueado = false;
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar el usuario");
			}

			public void onSuccess(Boolean isBloqueado)
			{
				if (isBloqueado)
				{
					h.addStyleName("blocked");
					h.setStyleName("blocked");
					bloqueado = true;
				}
			}
		};
		ClientUtils.getSoftmartService().isUsuarioBloqueado(project.getUsuario().getLogin(), callback);

		add(new Label("Proyecto " + project.getNombre()));
		table.clear();
		add(table);
		table.setWidget(0, 0, new HTML("Comprador"));
		table.setWidget(1, 0, new HTML("Presupuesto"));
		table.setWidget(2, 0, new HTML("Moneda"));
		table.setWidget(3, 0, new HTML("Fecha de cierre"));
		table.setWidget(4, 0, new HTML("Nivel de reputaci&oacute;n"));
		table.setWidget(5, 0, new HTML("Dificultad"));
		table.setWidget(6, 0, new HTML("Tama&ntilde;o"));
		table.setWidget(7, 0, new HTML("Descripci&oacute;n"));
		table.setWidget(8, 0, new HTML("Archivo"));
		table.setWidget(9, 0, new HTML("¿Cancelado por Usuario?"));
		table.setWidget(10, 0, new HTML("¿Cancelado por Administrador?"));
		table.setWidget(11, 0, new HTML("¿Revisado por Administrador?"));

		int col = 1;
		HTML h2;
		if(bloqueado)
			h2= new HTML(project.getUsuario().getLogin()+". Este usuario está bloqueado");
		else
			h2=h;
		
		table.setWidget(0, col, h2);
		table.setWidget(1, col, new HTML(Presupuesto.armarRango(project.getMinPresupuesto(), project
				.getMaxPresupuesto())));
		table.setWidget(2, col, new HTML(project.getMoneda().getDescription()));
		table.setWidget(3, col, new HTML(String.valueOf(project.getFecha().getDate()) + "/"
				+ String.valueOf(project.getFecha().getMonth() + 1) + "/"
				+ String.valueOf(project.getFecha().getYear() + 1900)));
		table.setWidget(4, col, new HTML(project.getNivel()));
		table.setWidget(5, col, new HTML(project.getDificultad()));
		table.setWidget(6, col, new HTML(project.getTamanio()));
		table.setWidget(7, col, new HTML(project.getDescripcion()));
		if (project.getPathArchivo() != null && !project.getPathArchivo().isEmpty())
			table.setWidget(8, col, new Anchor("Bajar"));
		else
			table.setWidget(8, col, new HTML("No se ha cargado un archivo"));
		if (project.isCancelado())
			table.setWidget(9, col, new HTML("Si"));
		else
			table.setWidget(9, col, new HTML("No"));
		if (project.isCanceladoXAdmin())
			table.setWidget(10, col, new HTML("Si"));
		else
			table.setWidget(10, col, new HTML("No"));
		if (project.isRevisado())
			table.setWidget(11, col, new HTML("Si"));
		else
			table.setWidget(11, col, new HTML("No"));
		
		table.setBorderWidth(1);
	}

}
