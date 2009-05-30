package edu.tdp2.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.model.Oferta;

public class ComentarioWidget extends DialogBox
{

	private String c;
	private FlexTable table = new FlexTable();
	private ComentarioConstants constants;

	public ComentarioWidget(Oferta oferta)
	{
		super(true);
		c = oferta.getDescripcion();
		load();
		/*
		 * this.setPopupPositionAndShow(new PopupPanel.PositionCallback() { public void setPosition(int offsetWidth, int
		 * offsetHeight) { int left = (Window.getClientWidth() - offsetWidth); int top = (Window.getClientHeight() -
		 * offsetHeight); setPopupPosition(left, top); } });
		 */

	}

	public ComentarioWidget(OfertaDto oferta)
	{
		super(true);
		constants = GWT.create(ComentarioConstants.class);
		c = oferta.getDescripcion();
		load();
		/*
		 * this.setPopupPositionAndShow(new PopupPanel.PositionCallback() { public void setPosition(int offsetWidth, int
		 * offsetHeight) { int left = (Window.getClientWidth() - offsetWidth) / 3; int top = (Window.getClientHeight() -
		 * offsetHeight) / 3; setPopupPosition(left, top); } });
		 */
	}

	protected native void reload() /*-{
	   $wnd.location.reload();
	  }-*/;

	private void load()
	{
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(10);
		table.clear();
		panel.add(new HTML(constants.comentario()));
		int row = 0;
		if (c.isEmpty())
			table.setWidget(row, 0, new HTML(constants.noHayComentario()));
		table.setWidget(row, 0, new HTML(c));

		panel.add(table);
		setWidget(panel);
	}

}
