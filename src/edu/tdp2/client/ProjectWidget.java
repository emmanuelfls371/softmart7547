package edu.tdp2.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
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
		setSpacing(10);
		
		table.clear();
		final HTML h = new HTML(project.getUsuario().getLogin());
		
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
					HTML h2 = new HTML("* No puede ofertar este proyecto ya que el usuario está bloqueado por el administrador");
					h2.setStyleName("blocked");
					h2.addStyleName("c1y2ProjectWidget");
					h2.setWidth("580px");
					add(h2);
					h.setHTML(project.getUsuario().getLogin()+"*");
				}
			}
		};
		ClientUtils.getSoftmartService().isUsuarioBloqueado(project.getUsuario().getLogin(), callback);

		HTML lineHoriz= new HTML("<h3> Proyecto " + project.getNombre() + "</h3>");
		lineHoriz.addStyleName("hl2");
		lineHoriz.setWidth("250px");
		add(lineHoriz);
		table.addStyleName("tableProjectWidget");
		
		table.setCellPadding(10);

		
		table.getCellFormatter().addStyleName(0, 1, "row0c1ProjectWidget");
		table.getCellFormatter().addStyleName(1, 1, "row1c1ProjectWidget");
		table.getCellFormatter().addStyleName(1, 0, "row1c0ProjectWidget");
		table.getCellFormatter().addStyleName(5, 2, "row5c3ProjectWidget");
		
		table.getCellFormatter().addStyleName(2, 1, "c1ProjectWidget");
		table.getCellFormatter().addStyleName(3, 1, "c1ProjectWidget");
		table.getCellFormatter().addStyleName(4, 1, "c1ProjectWidget");
		table.getCellFormatter().addStyleName(5, 1, "c1ProjectWidget");
		
		table.getCellFormatter().addStyleName(0, 0, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(1, 0, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(2, 0, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(3, 0, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(4, 0, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(5, 0, "c0ProjectWidget");
		
		table.getCellFormatter().addStyleName(0, 2, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(1, 2, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(2, 2, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(3, 2, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(4, 2, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(5, 2, "c0ProjectWidget");
		
		table.getCellFormatter().addStyleName(0, 1, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(1, 1, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(2, 1, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(3, 1, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(4, 1, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(5, 1, "c1y2ProjectWidget");
		
		table.getCellFormatter().addStyleName(0, 3, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(1, 3, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(2, 3, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(3, 3, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(4, 3, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(5, 3, "c1y2ProjectWidget");
			
		table.setWidget(0, 0, new HTML("Comprador:"));
		table.setWidget(1, 0, new HTML("Nivel de reputaci&oacute;n de usuario:"));
		table.setWidget(2, 0, new HTML("¿Cancelado por Usuario?"));
		table.setWidget(3, 0, new HTML("¿Cancelado por Administrador?"));
		table.setWidget(4, 0, new HTML("¿Revisado por Administrador?"));
		table.setWidget(5, 0, new HTML("Nivel de reputaci&oacute;n requerido:"));
		
		
		table.setWidget(0, 2, new HTML("Presupuesto:"));
		table.setWidget(1, 2, new HTML("Fecha de cierre:"));
		table.setWidget(2, 2, new HTML("Dificultad:"));
		table.setWidget(3, 2, new HTML("Tama&ntilde;o:"));
		table.setWidget(4, 2, new HTML("Descripci&oacute;n:"));
		
		

		int col = 1;
		
		table.setWidget(0, col, h);
		table.setWidget(1, col, new HTML (project.getUsuario().getNivel()));
		
		if (project.isCancelado())
			table.setWidget(2, col, new HTML("Si"));
		else
			table.setWidget(2, col, new HTML("No"));
		if (project.isCanceladoXAdmin())
			table.setWidget(3, col, new HTML("Si"));
		else
			table.setWidget(3, col, new HTML("No"));
		if (project.isRevisado())
			table.setWidget(4, col, new HTML("Si"));
		else
			table.setWidget(4, col, new HTML("No"));
		table.setWidget(5, col, new HTML(project.getNivel()));
		
		col=3;
		
		table.setWidget(0, col, new HTML(Presupuesto.armarRango(project.getMinPresupuesto(), project
				.getMaxPresupuesto())+ " en " +project.getMoneda().getDescription()));
		
		table.setWidget(1, col, new HTML(String.valueOf(project.getFecha().getDate()) + "/"
				+ String.valueOf(project.getFecha().getMonth() + 1) + "/"
				+ String.valueOf(project.getFecha().getYear() + 1900)));
		
		table.setWidget(2, col, new HTML(project.getDificultad()));
		table.setWidget(3, col, new HTML(project.getTamanio()));
		if (project.getDescripcion() != null && !project.getDescripcion().isEmpty())
			table.setWidget(4, col, new HTML(project.getDescripcion()));
		else
			table.setWidget(4, col, new HTML("No hay descripci&oacute;n disponible"));
		if (project.getPathArchivo() != null && !project.getPathArchivo().isEmpty())
			table.setWidget(5, 2, new Anchor("Bajar"));
		else
			table.setWidget(5, 2, new HTML("No se ha cargado un archivo"));
		
		
		add(table);
		
	}

}
