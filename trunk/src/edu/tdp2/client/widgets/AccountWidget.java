package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockPanel;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.CalificationWidget;
import edu.tdp2.client.TipoCalificacion;
import edu.tdp2.client.dto.ContratoDto;

import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;


public class AccountWidget extends DockPanel{

	
	protected Proyecto proySelected;
	protected VerticalPanel vCerrados;
	protected boolean proyCerrados;
	protected Anchor califHecha;
	protected Anchor califRecibida;
	protected SimplePanel centerPanel = new SimplePanel();
	protected SimplePanel eastPanel = new SimplePanel();
	protected HorizontalPanel underPanel = new HorizontalPanel();
	
	protected boolean accion;
	
	public AccountWidget(){
		proySelected = null;
		vCerrados = null;
		proyCerrados = false;
		accion=true;
	}
	
	protected void setLinkCalificacion(){
		
		califRecibida = new Anchor("Ver calificaci&oacute;n recibida", true);
		califHecha = new Anchor("Ver calificaci&oacute;n hecha", true);
		vCerrados.add(califRecibida);
		vCerrados.add(califHecha);
	}
	
	protected Anchor getCalificarAction(){
				
			proyCerrados = false;
			Anchor menuLink = new Anchor("Calificar");
			menuLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					Proyecto proyecto = proySelected;
					if (proyecto == null)
						Window.alert("Debe seleccionar un proyecto para calificar");
					else
						onShowNewCalificacion(proyecto);
				}
			});
			return menuLink;
				
	}
	
	public void onShowNewCalificacion(Proyecto project)
	{
		putAlone(NewCalificationWidget.getInstance(project));
	}
	

	protected void putAlone(Widget widget)
	{
		centerPanel.clear();
		centerPanel.setWidget(widget);
	}
	
	protected RadioButton getActionButton(final Proyecto proyecto){

		RadioButton r = new RadioButton("Acción", "");
		
		r.addValueChangeHandler(new ValueChangeHandler<Boolean>()
			{
				public void onValueChange(ValueChangeEvent<Boolean> event)
				{
					final boolean value = event.getValue();
					if(value == true){
						proySelected = proyecto;
					}
					if(proyCerrados){
						
						onShowOptionCalifRecibida();
						onShowOptionCalifHecha();	
					}
				}
			});
		
		return r;
	}

	protected void onShowOptionCalifHecha(){
		
		AsyncCallback<List<ContratoDto>> callback2 = new AsyncCallback<List<ContratoDto>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo ver la calificacion");
			}
			
			public void onSuccess(List<ContratoDto> contratos)
			{
				if(proySelected!=null){
					
					ContratoDto c = null;
					for(ContratoDto contrato : contratos){
					 if(contrato.getProyecto().getNombre().equals(proySelected.getNombre())){
						 c=contrato;
						 break;
					 }
					}
					if (c == null){
						califHecha.setEnabled(false);
						//Window.alert("No ha hecho calificación hasta el momento");
					}
					else{
						final ContratoDto c2=c;
						califHecha.setEnabled(true);
						califHecha.addClickHandler(new ClickHandler()
						{
							public void onClick(ClickEvent event)
							{
								if(proySelected==null){
									Window.alert("Debe seleccionar un proyecto");
								}else{
									onShowCalification(c2, TipoCalificacion.Hecha);
								}
							}
						});
					}
					eastPanel.setWidget(vCerrados);
				}
				
			}
		};						
	
		ClientUtils.getSoftmartService().getCalificacionesHechas(LoginWidget.getCurrentUser(), callback2);
		

		
	}
	
	
	protected void onShowOptionCalifRecibida(){

		AsyncCallback<List<ContratoDto>> callback = new AsyncCallback<List<ContratoDto>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo ver la calificacion");
			}

			public void onSuccess(List<ContratoDto> contratos)
			{
				if(proySelected!=null){
					
					ContratoDto c= null;
					for(ContratoDto contrato : contratos){
					 if(contrato.getProyecto().getNombre().equals(proySelected.getNombre())){
						 c=contrato;
						 break;
					 }
					}
					if (c == null)
						califRecibida.setEnabled(false);
						//Window.alert("El proyecto no ha recibido calificación");
					else{
						final ContratoDto c2=c;
						califRecibida.setEnabled(true);
						califRecibida.addClickHandler(new ClickHandler()
						{
									
							public void onClick(ClickEvent event)
							{
								if(proySelected==null){
									Window.alert("Debe seleccionar un proyecto");
								}else{
									onShowCalification(c2, TipoCalificacion.Recibida);
								}
							}
						});
					}
					eastPanel.setWidget(vCerrados);
				}
			}
		};
		ClientUtils.getSoftmartService().getCalificacionesRecibidas(LoginWidget.getCurrentUser(), callback);
		

	}
	
	protected void onShowCalification(ContratoDto contrato, TipoCalificacion tipo)
	{
		putAlone(new CalificationWidget(contrato.getCalif(), contrato.getProyecto().getNombre(), tipo));
	}
	
	
}
