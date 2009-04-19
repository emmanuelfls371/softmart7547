package edu.tdp2.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.utils.ClientUtils;

public class OfertaWidget extends VerticalPanel
{

	private OfertaDto oferta;
	private String proyecto;
	private FlexTable table = new FlexTable();

	public OfertaWidget(OfertaDto oferta, String proyecto)
	{
		this.oferta = oferta;
		this.proyecto = proyecto;
		load();
	}

	protected native void reload() /*-{
	   $wnd.location.reload();
	  }-*/;

	private void load()
	{
		add(new Label("Ofertas para el proyecto " + proyecto));
		table.clear();
		add(table);
		table.setWidget(0, 0, new HTML("Monto"));
		table.setWidget(0, 1, new HTML("Moneda"));
		table.setWidget(0, 2, new HTML("D&iacute;as"));
		table.setWidget(0, 3, new HTML("Comentario"));
		table.setWidget(0, 4, new HTML("Vendedor"));

		int row = 1;
		table.setWidget(row, 0, new HTML(Integer.toString(oferta.getMonto())));
		table.setWidget(row, 1, new HTML(oferta.getMoneda()));
		table.setWidget(row, 2, new HTML(Integer.toString(oferta.getDias())));

		Anchor menuLink = new Anchor("Ver Comentario");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				History.newItem("");
				clear();
				add(new ComentarioWidget(oferta));
			}
		});

		table.setWidget(row, 3, menuLink);
		table.setWidget(row, 4, new HTML(oferta.getUsuario()));

		table.setWidget(table.getRowCount(), 2, ClientUtils.getBackAnchor());
	}

}
