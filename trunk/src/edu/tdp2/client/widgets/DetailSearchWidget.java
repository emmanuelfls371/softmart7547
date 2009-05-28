package edu.tdp2.client.widgets;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.ComentarioWidget;

import edu.tdp2.client.ProjectWidget;

import edu.tdp2.client.model.NivelReputacion;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class DetailSearchWidget extends VerticalPanel {

	private Proyecto proy;
	private FlexTable table;
	private List<Oferta> ofertas;
	private Oferta ofertaG;
	private boolean bloqueado;
	
	public DetailSearchWidget(Proyecto proy){
		this.proy=proy;
		load();
	}
	
	public void load(){
		
		getOfertaGanadora();
		
		AsyncCallback<List<Oferta>> callback = new AsyncCallback<List<Oferta>>(){
		
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar las ofertas");
			}

			public void onSuccess(List<Oferta> of)
			{
				if(of==null){
					Window.alert("No se pudo recuperar las ofertas");
				}else{
					ofertas=of;
					add(new ProjectWidget(proy));
					
					Anchor menuLink = new Anchor("Ofertar");
					if(ofertaG==null&&!proy.getUsuario().getLogin().equals(LoginWidget.getCurrentUser())&&
							proy.getFecha().after(new Date())&&!proy.isCancelado()&&proy.isRevisado()&&
							!proy.isCanceladoXAdmin()&&!proy.getUsuario().isBloqueado()){
						menuLink.addClickHandler(new ClickHandler()
						{
							public void onClick(ClickEvent event)
							{
								final Proyecto proyecto = proy;
								if (proyecto == null)
									Window.alert("Debe seleccionar un proyecto para ofertar");
								else {
									
									AsyncCallback<String> callback = new AsyncCallback<String>()
									{
										public void onFailure(Throwable caught)
										{
											Window.alert("No se pudo recuperar el usuario");
										}
				
										public void onSuccess(String nivel)
										{
											if (nivel == null)
												Window.alert("No se pudo recuperar el usuario");
											else
											{
												if(proyecto.getNivel().equals(NivelReputacion.Premium.name())&&nivel.equals(NivelReputacion.Premium.name())){
													onShowNewOferta(proyecto);
												}else if (proyecto.getNivel().equals(NivelReputacion.Normal.name())){
													onShowNewOferta(proyecto);
												}else {
													Window.alert("El proyecto requiere ofertantes Premium");
												}
													
											}
										}
									};
									ClientUtils.getSoftmartService().getUsuario(LoginWidget.getCurrentUser(), callback);
													
								}
							}
						});
					}else{
						menuLink.setEnabled(false);
					}
					
					add(menuLink);
					buildTableOfertas();
					add(table);
				}
			}
		};
		ClientUtils.getSoftmartService().getOffers(proy, callback);
		
	}
	
	
	private void buildTableOfertas()
	{
		table = new FlexTable();
		initialize();
		int row = 1;
		for (final Oferta of : ofertas)
		{
			load(row, of);
			row++;
		}
	}
	
	private void getOfertaGanadora(){
		
		AsyncCallback<Oferta> callback = new AsyncCallback<Oferta>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar la oferta");
			}

			public void onSuccess(Oferta oferta)
			{
				ofertaG=oferta;
			}
		};
		ClientUtils.getSoftmartService().getOfertaGanadora(proy, callback);
	}


	private void initialize(){
		add(new Label("Ofertas para el proyecto " + proy.getNombre()));
		add(table);
		table.setWidget(0, 0, new HTML("Monto"));
		table.setWidget(0, 1, new HTML("Moneda"));
		table.setWidget(0, 2, new HTML("D&iacute;as"));
		table.setWidget(0, 3, new HTML("Comentario"));
		table.setWidget(0, 4, new HTML("Vendedor"));
	}

	private void load(int row, final Oferta oferta)
	{
		final HTML h = new HTML(oferta.getUsuario().getLogin());
		bloqueado=false;
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
					bloqueado = true;
				}
			}
		};
		ClientUtils.getSoftmartService().isUsuarioBloqueado(oferta.getUsuario().getLogin(), callback);

		table.setWidget(row, 0, new HTML(Float.toString(oferta.getMonto())));
		table.setWidget(row, 1, new HTML(oferta.getMoneda().getDescription()));
		table.setWidget(row, 2, new HTML(Integer.toString(oferta.getDias())));

		Anchor menuLink = new Anchor("Ver Comentario");
		if (oferta.getUsuario().getLogin().equals(LoginWidget.getCurrentUser())||proy.getUsuario().getLogin().equals(LoginWidget.getCurrentUser()))
		{
			menuLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					new ComentarioWidget(oferta).show();			
				}
			});
		}else{
			menuLink.setEnabled(false);
		}
		HTML h2;

		if(bloqueado)
			h2= new HTML(oferta.getUsuario().getLogin()+"<p>Este usuario está bloqueado</p>");
		else
			h2=h;
		table.setWidget(row, 3, menuLink);
		table.setWidget(row, 4, h2);
		
		if(ofertaG!=null&&ofertaG.equals(oferta)){
			table.setWidget(row, 5, new HTML("Oferta ganadora"));
		}
		
		table.setBorderWidth(1);
	}

	public void onShowNewOferta(Proyecto project)
	{
		clear();
		add(NewOfertaWidget.getInstance(project));
	}
	
}
