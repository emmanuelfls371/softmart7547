package edu.tdp2.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.dto.CalificacionDto;
import edu.tdp2.client.utils.ClientUtils;

public class CalificationWidget extends VerticalPanel
{

	private CalificacionDto calif;
	private String proyecto;
	private String tipo;
	private FlexTable table = new FlexTable();

	public CalificationWidget(CalificacionDto calif, String proyecto, TipoCalificacion tipo)
	{
		this.calif = calif;
		this.proyecto = proyecto;
		this.tipo = tipo.getDescription();
		load();
	}

	protected native void reload() /*-{
			   $wnd.location.reload();
			  }-*/;

	private void load()
	{
		add(new Label("Calificacion para Proyecto " + proyecto));
		table.clear();
		add(table);
		table.setWidget(0, 0, new HTML("Calificacion"));
		table.setWidget(0, 1, new HTML("Comentario"));
		table.setWidget(0, 2, new HTML(tipo));

		int row = 1;
		table.setWidget(row, 0, new HTML(Integer.toString(calif.getCalificacion())));
		table.setWidget(row, 1, new HTML(calif.getComentario()));
		table.setWidget(row, 2, new HTML(calif.getUsuario()));

		table.setWidget(table.getRowCount(), 2, ClientUtils.getBackAnchor());
	}

}
