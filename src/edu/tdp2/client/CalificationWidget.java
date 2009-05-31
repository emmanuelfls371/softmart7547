package edu.tdp2.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.dto.CalificacionDto;
import edu.tdp2.client.utils.ClientUtils;

public class CalificationWidget extends VerticalPanel
{

	private CalificacionDto calif;
	private String proyecto;
	private String tipo;
	private FlexTable table = new FlexTable();
	private CalificacionConstants constants;

	public CalificationWidget(CalificacionDto calif, String proyecto, TipoCalificacion tipo)
	{
		this.calif = calif;
		this.proyecto = proyecto;
		this.tipo = tipo.getDescription();
		constants = GWT.create(CalificacionConstants.class);
		load();
	}

	protected native void reload() /*-{
			   $wnd.location.reload();
			  }-*/;

	private void load()
	{

		final HTML h = new HTML(calif.getUsuario());

		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failIsUsuarioBloqueado());
			}

			public void onSuccess(Boolean isBloqueado)
			{
				if (isBloqueado)
				{
					h.addStyleName("blocked");
					h.setStyleName("blocked");
					HTML h2 = new HTML("*Usuario Bloqueado");
					h2.setStyleName("blocked");
					h2.addStyleName("c1y2ProjectWidget");
					h2.setWidth("200px");
					add(h2);
					h.setHTML(calif.getUsuario() + "*");
				}
			}
		};
		ClientUtils.getSoftmartService().isUsuarioBloqueado(calif.getUsuario(), callback);

		HTML lineHoriz = new HTML("<h3>" + constants.califParaProyecto() + proyecto + "</h3>");
		lineHoriz.addStyleName("hl2");
		lineHoriz.setWidth("250px");
		add(lineHoriz);

		table.clear();
		add(table);

		table.addStyleName("tableProjectWidget");

		table.setCellPadding(5);

		table.getCellFormatter().addStyleName(0, 0, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(1, 0, "c0ProjectWidget");

		table.getCellFormatter().addStyleName(0, 1, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(1, 1, "c1y2ProjectWidget");

		table.setWidget(0, 0, new HTML(constants.calificacion()));
		table.setWidget(0, 1, new HTML(constants.comentario()));
		table.setWidget(0, 2, new HTML(tipo));

		int row = 1;
		table.setWidget(row, 0, new HTML(Integer.toString(calif.getCalificacion())));
		table.setWidget(row, 1, new HTML(calif.getComentario()));
		table.setWidget(row, 2, h);

	}

}
