package edu.tdp2.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;
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
		initialize();
		load(1);
	}

	
	public OfertaWidget(Proyecto proyecto)
	{
		this.proyecto = proyecto.getNombre();
		final OfertaWidget ow = this;
		AsyncCallback<Oferta> callback = new AsyncCallback<Oferta>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar la oferta");
			}

			public void onSuccess(Oferta oferta)
			{
				if (oferta == null)
				{
					Window.alert("Este proyecto no tiene una oferta ganadora");
					History.back();
				}
				else
				{
					ow.oferta = OfertaDto.fromOferta(oferta);
					initialize();
					load(1);
				}
			}
		};
		ClientUtils.getSoftmartService().getOfertaGanadora(proyecto, callback);
	}

	public OfertaWidget(Proyecto proyecto, String usuario)
	{
		this.proyecto = proyecto.getNombre();
		final OfertaWidget ow = this;
		AsyncCallback<Oferta> callback = new AsyncCallback<Oferta>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar la oferta");
			}

			public void onSuccess(Oferta oferta)
			{
				if (oferta == null)
					Window.alert("El usuario no ha realizado ofertas para este proyecto");
				else
				{
					ow.oferta = OfertaDto.fromOferta(oferta);
					initialize();
					load(1);
				}
			}
		};
		ClientUtils.getSoftmartService().getOfertaDeUsuario(proyecto, usuario, callback);
	}

	protected native void reload() /*-{
	   $wnd.location.reload();
	  }-*/;
	
	private void initialize(){
		add(new Label("Ofertas para el proyecto " + proyecto));
		table.clear();
		add(table);
		table.setWidget(0, 0, new HTML("Monto"));
		table.setWidget(0, 1, new HTML("Moneda"));
		table.setWidget(0, 2, new HTML("D&iacute;as"));
		table.setWidget(0, 3, new HTML("Comentario"));
		table.setWidget(0, 4, new HTML("Vendedor"));
	}

	private void load(int row)
	{
		final HTML h = new HTML(oferta.getUsuario());

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
				}
			}
		};
		ClientUtils.getSoftmartService().isUsuarioBloqueado(oferta.getUsuario(), callback);

		table.setWidget(row, 0, new HTML(Float.toString(oferta.getMonto())));
		table.setWidget(row, 1, new HTML(oferta.getMoneda()));
		table.setWidget(row, 2, new HTML(Integer.toString(oferta.getDias())));

		Anchor menuLink = new Anchor("Ver Comentario");
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				new ComentarioWidget(oferta).show();
			}
		});

		table.setWidget(row, 3, menuLink);
		table.setWidget(row, 4, h);
		table.setBorderWidth(1);
	}

}
