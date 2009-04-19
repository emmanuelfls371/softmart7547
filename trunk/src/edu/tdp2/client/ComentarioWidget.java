package edu.tdp2.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.model.Oferta;

public class ComentarioWidget extends VerticalPanel
{

	private String c;
	private FlexTable table = new FlexTable();

	public ComentarioWidget(Oferta oferta)
	{
		this.c = oferta.getDescripcion();
		load();
	}

	public ComentarioWidget(OfertaDto oferta)
	{
		this.c = oferta.getDescripcion();
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

		Anchor back = new Anchor("Volver");
		table.setWidget(table.getRowCount(), 2, back);
		back.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				History.back();
			}
		});

	}

}
