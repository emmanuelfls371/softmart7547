package edu.tdp2.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.utils.ClientUtils;

public class ComentarioWidget extends VerticalPanel
{

	private String c;
	private FlexTable table = new FlexTable();

	public ComentarioWidget(Oferta oferta)
	{
		c = oferta.getDescripcion();
		load();
	}

	public ComentarioWidget(OfertaDto oferta)
	{
		c = oferta.getDescripcion();
		load();
	}

	protected native void reload() /*-{
	   $wnd.location.reload();
	  }-*/;

	private void load()
	{
		table.clear();
		add(table);
		table.setWidget(0, 0, new HTML("Comentario"));

		int row = 1;
		table.setWidget(row, 0, new HTML(c));
	}

}
