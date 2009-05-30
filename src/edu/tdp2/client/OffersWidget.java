package edu.tdp2.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class OffersWidget extends VerticalPanel
{
	private static final int COL_HIDDEN = 5;
	private static final int COL_RADIO = 4;
	private OfertaConstants constants;
	private Proyecto project;
	private FlexTable table = new FlexTable();

	public OffersWidget(Proyecto project)
	{
		this.project = project;
		constants = GWT.create(OfertaConstants.class);
		load();
	}

	protected int findChecked()
	{
		for (int row = 1; row < table.getRowCount() - 1; row++)
			if (((RadioButton) table.getWidget(row, COL_RADIO)).getValue())
				return row;
		return -1;
	}

	protected native void reload() /*-{
	   $wnd.location.reload();
	  }-*/;

	private Button getSubmitButton()
	{
		Button submit = new Button(constants.elegir(), new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				int rowWithCheckedRadio = findChecked();
				long offerId;
				if (rowWithCheckedRadio == -1)
				{
					Window.alert(constants.debeElegirOferta());
					return;
				}
				else
					offerId = Long.parseLong(((Hidden) table.getWidget(rowWithCheckedRadio, COL_HIDDEN)).getValue());
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert(constants.failChooseOffer());
					}

					public void onSuccess(String errMsg)
					{
						if (errMsg != null)
							Window.alert(errMsg);
						else
							reload();
					}
				};
				ClientUtils.getSoftmartService().chooseOffer(offerId, callback);
			}
		});
		return submit;
	}

	private void load()
	{
		add(new Label(constants.ofertasParaProyecto() + project.getNombre()));
		AsyncCallback<List<Oferta>> callback = new AsyncCallback<List<Oferta>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failGetOffers());
			}

			public void onSuccess(List<Oferta> ofertas)
			{
				table.clear();
				add(table);
				table.setWidget(0, 0, new HTML(constants.usuario()));
				table.setWidget(0, 1, new HTML(constants.monto()));
				table.setWidget(0, 2, new HTML(constants.dias()));
				table.setWidget(0, 3, new HTML(constants.comentario()));
				table.setWidget(0, 4, new HTML(constants.elegir()));

				for (int i = 0; i < ofertas.size(); i++)
				{
					final Oferta oferta = ofertas.get(i);

					int row = i + 1;
					table.setWidget(row, 0, new HTML(oferta.getUsuario().getLogin()));
					table.setWidget(row, 1, new HTML(((Float) oferta.getMonto()).toString()));
					table.setWidget(row, 2, new HTML(((Integer) oferta.getDias()).toString()));

					Anchor menuLink = new Anchor(constants.verComentario());
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

					table.setWidget(row, COL_RADIO, new RadioButton("chooseOffer"));
					table.setWidget(row, COL_HIDDEN, new Hidden("id" + row, oferta.getId().toString()));
				}

				table.setWidget(table.getRowCount(), 2, ClientUtils.getBackAnchor());

				table.setWidget(table.getRowCount() - 1, 4, getSubmitButton());
			}
		};
		ClientUtils.getSoftmartService().getOffers(project, callback);
	}
}
