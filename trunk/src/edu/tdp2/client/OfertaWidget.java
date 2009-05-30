package edu.tdp2.client;

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
				Window.alert("No se pudo recuperar la oferta");
			}

			public void onSuccess(Oferta oferta)
			{
				if (oferta == null)
					Window.alert("El usuario no ha realizado ofertas para este proyecto");
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
		
		add(new Label("Oferta para el proyecto " + proyecto));
		table.clear();
		add(table);
		table.setWidget(0, 2, new HTML("Monto:"));
		table.setWidget(1, 2, new HTML("D&iacute;as:"));
		
		table.setWidget(0, 0, new HTML("Vendedor:"));
		table.setWidget(1, 0, new HTML("Nivel de reputaci&oacute;n de usuario:"));
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
				Window.alert("No se pudo recuperar el usuario");
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
		table.setWidget(0, 3, new HTML(Float.toString(oferta.getMonto()) + " en "+ oferta.getMoneda()));
		table.setWidget(1, 3, new HTML(Integer.toString(oferta.getDias())));

		Anchor menuLink = new Anchor("Ver Comentario");
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
