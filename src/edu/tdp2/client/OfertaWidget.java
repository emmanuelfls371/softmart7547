package edu.tdp2.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.model.Usuario;
import edu.tdp2.client.utils.ClientUtils;

public class OfertaWidget extends VerticalPanel
{
	private OfertaConstants constants;
	private OfertaDto oferta;
	private String proyecto;
	private Usuario usOferta;
	private FlexTable table = new FlexTable();
	
	public OfertaWidget(final Oferta oferta, final String proyecto)
	{
		this.oferta=OfertaDto.fromOferta(oferta);
		this.proyecto = proyecto;
		this.usOferta=oferta.getUsuario();
		initialize();
		load();
	}
	
	public OfertaWidget(final Proyecto proyecto)
	{
		this.proyecto = proyecto.getNombre();
		constants = GWT.create(OfertaConstants.class);
		final OfertaWidget ow = this;
		AsyncCallback<Oferta> callback = new AsyncCallback<Oferta>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failGetOffer());
			}

			public void onSuccess(Oferta oferta)
			{
				if (oferta == null)
				{
					Window.alert(constants.noOfertaGanadora());
					History.back();
				}
				else
				{
					usOferta = proyecto.getUsuario();
					ow.oferta = OfertaDto.fromOferta(oferta);
					initialize();
					load();
				}
			}
		};
		ClientUtils.getSoftmartService().getOfertaGanadora(proyecto, callback);
	}

	public OfertaWidget(final Proyecto proyecto, String usuario)
	{
		this.proyecto = proyecto.getNombre();
		final OfertaWidget ow = this;
		AsyncCallback<Oferta> callback = new AsyncCallback<Oferta>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failGetOffer());
			}

			public void onSuccess(Oferta oferta)
			{
				if (oferta == null)
					Window.alert(constants.usuarioNoHaOfertado());
				else
				{
					usOferta = proyecto.getUsuario();
					ow.oferta = OfertaDto.fromOferta(oferta);
					initialize();
					load();
				}
			}
		};
		ClientUtils.getSoftmartService().getOfertaDeUsuario(proyecto, usuario, callback);
	}

	protected native void reload() /*-{
	   $wnd.location.reload();
	  }-*/;
	
	private void initialize(){
		
		setSpacing(7);
		
		add(new Label(constants.ofertaParaProyecto() + proyecto));
		table.clear();
		add(table);
		table.setWidget(0, 2, new HTML(constants.monto()));
		table.setWidget(1, 2, new HTML(constants.dias()));
		
		table.setWidget(0, 0, new HTML(constants.vendedor()));
		table.setWidget(1, 0, new HTML(constants.reputacion()));
	}

	private void load()
	{
		final HTML h = new HTML(oferta.getUsuario());

		table.addStyleName("tableProjectWidget");
		
		table.setCellPadding(5);
		
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failGetUser());
			}

			public void onSuccess(Boolean isBloqueado)
			{
				if (isBloqueado)
				{
					h.addStyleName("blocked");
					h.setStyleName("blocked");
					HTML h2 = new HTML(constants.usuarioBloqueado());
					h2.setStyleName("blocked");
					h2.addStyleName("c1y2ProjectWidget");
					h2.setWidth("200px");
					add(h2);
					h.setHTML(oferta.getUsuario()+"*");
				}
			}
		};
		ClientUtils.getSoftmartService().isUsuarioBloqueado(oferta.getUsuario(), callback);

		table.getCellFormatter().addStyleName(0, 1, "row0c1ProjectWidget");
		table.getCellFormatter().addStyleName(1, 1, "row0c1ProjectWidget");
		
		table.getCellFormatter().addStyleName(2, 2, "row2c2ProjectWidget");
		
		table.getCellFormatter().addStyleName(0, 0, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(1, 0, "c0ProjectWidget");

		table.getCellFormatter().addStyleName(0, 2, "c0ProjectWidget");
		table.getCellFormatter().addStyleName(1, 2, "c0ProjectWidget");
		
		table.getCellFormatter().addStyleName(0, 1, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(1, 1, "c1y2ProjectWidget");
	
		table.getCellFormatter().addStyleName(0, 3, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(1, 3, "c1y2ProjectWidget");
		table.getCellFormatter().addStyleName(2, 2, "c1y2ProjectWidget");

		
		
		table.setWidget(0, 1, h);
		table.setWidget(1, 1, new HTML(usOferta.getNivel()));
		table.setWidget(0, 3, new HTML(Float.toString(oferta.getMonto()) + " " + constants.en() + " "
				+ oferta.getMoneda()));
		table.setWidget(1, 3, new HTML(Integer.toString(oferta.getDias())));

		Anchor menuLink = new Anchor(constants.verComentario());
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				 final DialogBox dialogBox = new ComentarioWidget(oferta);
				 dialogBox.setAnimationEnabled(true);
				 dialogBox.show();
				        
			}
		});

		table.setWidget(2, 2, menuLink);
		
		
	}

}
