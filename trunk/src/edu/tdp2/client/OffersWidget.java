package edu.tdp2.client;

import java.util.List;

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
	private Proyecto project;
	private FlexTable table = new FlexTable();

	public OffersWidget(Proyecto project)
	{
		this.project = project;
		load();
	}

	protected native void reload() /*-{
	   $wnd.location.reload();
	  }-*/;

	private void load()
	{
		add(new Label("Ofertas para el proyecto " + project.getNombre()));
		AsyncCallback<List<Oferta>> callback = new AsyncCallback<List<Oferta>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar las ofertas");
			}

			public void onSuccess(List<Oferta> ofertas)
			{
				table.clear();
				add(table);
				table.setWidget(0, 0, new HTML("Usuario"));
				table.setWidget(0, 1, new HTML("Monto"));
				table.setWidget(0, 2, new HTML("D&iacute;as (corridos)"));
				table.setWidget(0, 3, new HTML("Comentario"));
				table.setWidget(0, 4, new HTML("Elegir"));

				for (int i = 0; i < ofertas.size(); i++)
				{
					final Oferta oferta = ofertas.get(i);

					int row = i + 1;
					table.setWidget(row, 0, new HTML(oferta.getUsuario().getLogin()));
					table.setWidget(row, 1, new HTML(((Integer) oferta.getMonto()).toString()));
					table.setWidget(row, 2, new HTML(((Integer) oferta.getDias()).toString()));

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

					table.setWidget(row, COL_RADIO, new RadioButton("chooseOffer"));
					table.setWidget(row, COL_HIDDEN, new Hidden("id" + row, oferta.getId().toString()));
				}

				table.setWidget(table.getRowCount(), 2, ClientUtils.getBackAnchor());

				table.setWidget(table.getRowCount() - 1, 4, getSubmitButton());
			}
		};
		ClientUtils.getSoftmartService().getOffers(project, callback);
	}

	private Button getSubmitButton()
	{
		Button submit = new Button("Elegir", new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				int rowWithCheckedRadio = findChecked();
				long offerId;
				if (rowWithCheckedRadio == -1)
				{
					Window.alert("Debe elegir una oferta");
					return;
				}
				else
					offerId = Long.parseLong(((Hidden) table.getWidget(rowWithCheckedRadio, COL_HIDDEN)).getValue());
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert("No se pudo guardar las ofertas");
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

	protected int findChecked()
	{
		for (int row = 1; row < table.getRowCount() - 1; row++)
			if (((RadioButton) table.getWidget(row, COL_RADIO)).getValue())
				return row;
		return -1;
	}
}
