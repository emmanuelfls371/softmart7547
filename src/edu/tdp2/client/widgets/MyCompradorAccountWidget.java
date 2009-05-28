package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



import edu.tdp2.client.OfertaWidget;
import edu.tdp2.client.OffersWidget;
import edu.tdp2.client.ProjectWidget;

import edu.tdp2.client.dto.MyCompradorAccount;

import edu.tdp2.client.model.Presupuesto;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;


public class MyCompradorAccountWidget extends AccountWidget
{

	private final MyCompradorAccount datos;
	
	public MyCompradorAccountWidget(MyCompradorAccount datos)
	{
		this.datos = datos;
		add(getWestPanel(), WEST);
		add(centerPanel, CENTER);
		add(eastPanel, EAST);
		add(underPanel,SOUTH);
		centerPanel.setWidth("100%");
	}

	private Widget getWestPanel()
	{
		underPanel.clear();
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(10);

		panel.add(new HTML("<big><b>Mis proyectos</b></big>"));

		Anchor abiertos = new Anchor("Abiertos");
		
		abiertos.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected=null;
				accion=true;
				proyCerrados = false;
				Anchor menuLink = new Anchor("Ver ofertas");
				menuLink.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						Proyecto proyecto = proySelected;
						if (proyecto == null)
							Window.alert("Debe seleccionar un proyecto");
						else
						{
							onShowOffers(proyecto);
						}
					}
				});
				
				Anchor menuLink2 = new Anchor("Cancelar Proyecto");
				menuLink2.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						if (proySelected == null)
							Window.alert("Debe seleccionar un proyecto para cancelar");
						else if (Window.confirm("Â¿Seguro de que desea cancelar el proyecto?"))
						{
							AsyncCallback<String> projectCallback = new AsyncCallback<String>()
							{
								public void onFailure(Throwable caught)
								{
									Window.alert("No se pudo cancelar el proyecto");
								}

								public void onSuccess(String errMsg)
								{
									if (errMsg != null)
										Window.alert(errMsg);
									else
									{
										datos.getProyectosAbiertos().remove(proySelected);
										Window.alert("Proyecto cancelado");
									}
								}
							};
							ClientUtils.getSoftmartService().cancelarProyecto(proySelected.getId(),
									projectCallback);
						}
					}
				});
				
				VerticalPanel v= new VerticalPanel();
				v.add(new ProjectTable(datos.getProyectosAbiertos()));
				VerticalPanel v2= new VerticalPanel();
				v2.add(menuLink);
				v2.add(menuLink2);
				eastPanel.setWidget(v2);
				centerPanel.setWidget(v);
				eastPanel.setWidth("100%");
						
			}
		});
		
		
		panel.add(abiertos);

		Anchor cerrados = new Anchor("Cerrados");
		cerrados.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected=null;
				accion=true;
				proyCerrados = true;
				vCerrados= new VerticalPanel();
				VerticalPanel vCerr= new VerticalPanel();
				
				vCerr.add(new ProjectTable(datos.getProyectosCerrados()));
				
				Anchor ofertaG = new Anchor("Ver Oferta Ganadora");
				ofertaG.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						Proyecto proyecto = proySelected;
						if (proyecto == null)
							Window.alert("Debe seleccionar un proyecto para ver su oferta");
						else
							onShowOferta(proyecto);
					}
				});
				
				vCerrados.add(ofertaG);
				setLinkCalificacion();
				
				centerPanel.setWidget(vCerr);
				eastPanel.setWidth("100%");
			}
		});
		panel.add(cerrados);

		Anchor cancelados = new Anchor("Cancelados");
		cancelados.addClickHandler(new ClickHandler()
		{
			
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected=null;
				accion=false;
				proyCerrados = false;
				centerPanel.setWidget(new ProjectTable(datos.getProyectosCancelados()));
			}
		});
		panel.add(cancelados);

		Anchor sinCalificar = new Anchor("Pendientes a calificar");
		sinCalificar.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected=null;
				accion=true;
				VerticalPanel v= new VerticalPanel();
				v.add(new ProjectTable(datos.getProyectosSinCalificar()));
				eastPanel.add(getCalificarAction());
				eastPanel.setWidth("100%");
				centerPanel.setWidget(v);
			}
		});
		panel.add(sinCalificar);

		Anchor sinRecibirCalif = new Anchor("Pendientes de recibir calificaci&oacute;n", true);
		sinRecibirCalif.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				eastPanel.clear();
				proySelected=null;
				accion=false;
				proyCerrados = false;			
				VerticalPanel v= new VerticalPanel();
				v.add(new ProjectTable(datos.getProyectosSinRecibirCalif()));
				
				centerPanel.setWidget(v);
			}
		});
		panel.add(sinRecibirCalif);

		panel.setWidth("50px");
		
		underPanel.add(new HTML("<p> Mi reputación como comprador es: "+String.valueOf(datos.getReputacion())+" </p>"));
		underPanel.setVerticalAlignment(ALIGN_MIDDLE);
		return panel;
	}
	
	private void onShowOferta(Proyecto proy)
	{
		putAlone(new OfertaWidget(proy));
	}
	
	protected void onShowOffers(Proyecto projectOferta)
	{
		putAlone(new OffersWidget(projectOferta));
	}
	
	

	public class ProjectTable extends FlexTable
	{
		private final List<Proyecto> proyectos;

		public ProjectTable(List<Proyecto> proyectos)
		{
			this.proyectos = proyectos;
			setBorderWidth(1);
			buildWidget();
		}

		private void buildWidget()
		{
			setWidget(0, 0, new HTML("Nombre"));
			setWidget(0, 1, new HTML("Presupuesto"));
			setWidget(0, 2, new HTML("Moneda"));
			setWidget(0, 3, new HTML("Tama&ntilde;o"));
			setWidget(0, 4, new HTML("Complejidad"));
			setWidget(0, 5, new HTML("Fecha cierre"));
		
			if(accion)
				setWidget(0, 6, new HTML("Acción"));

			int row = 1;
			for (final Proyecto proyecto : proyectos)
			{
				Anchor aProy = new Anchor(proyecto.getNombre());
				aProy.addClickHandler(new ClickHandler()
				{
					public void onClick(ClickEvent event)
					{
						centerPanel.setWidget(new ProjectWidget(proyecto));
					}
				});
				setWidget(row, 0, aProy);
 
				setWidget(row, 1, new HTML(Presupuesto.armarRango(proyecto.getMinPresupuesto(), proyecto.getMaxPresupuesto())));
				setWidget(row, 2, new HTML(proyecto.getMoneda().getDescription()));
				setWidget(row, 3, new HTML(proyecto.getTamanio()));
				setWidget(row, 4, new HTML(proyecto.getDificultad()));
				DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
				setWidget(row, 5, new HTML(format.format(proyecto.getFecha())));
				if(accion){
					if(proyecto.isRevisado()){
						setWidget(row, 6, getActionButton(proyecto));
					}else{
						setWidget(row, 6, new HTML("Pendiente de aprobación por el administrador"));
					}
				}
				row++;
			}
		}
	}
}
