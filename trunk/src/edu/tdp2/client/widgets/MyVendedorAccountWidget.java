package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;

import com.google.gwt.user.client.ui.Anchor;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.OfertaWidget;
import edu.tdp2.client.ProjectWidget;
import edu.tdp2.client.dto.MyVendedorAccount;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;


public class MyVendedorAccountWidget extends AccountWidget
{
	
	private final MyVendedorAccount datos;

	public MyVendedorAccountWidget(MyVendedorAccount datos)
	{
		this.datos = datos;
		add(getWestPanel(), WEST);
		add(centerPanel, CENTER);

		centerPanel.setWidth("100%");
	}

	private Widget getWestPanel()
	{
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(10);

		panel.add(new HTML("<big><b>Proyectos ofertados</b></big>"));

		Anchor abiertos = new Anchor("Abiertos");
		abiertos.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				proySelected=null;
				accion=false;
				proyCerrados = false;
				centerPanel.setWidget(new ProjectTable(datos.getProyectosAbiertos()));
			}
		});
		panel.add(abiertos);

		Anchor adjudicados = new Anchor("Adjudicados");
		adjudicados.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				proySelected=null;
				accion=true;
				proyCerrados = true;
				vCerrados= new VerticalPanel();
				vCerrados.add(new ProjectTable(datos.getProyectosCerrados()));
				setLinkCalificacion();
				centerPanel.setWidget(vCerrados);
			}
		});
		panel.add(adjudicados);

		Anchor perdidos = new Anchor("Perdidos");
		perdidos.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				proySelected=null;
				accion=false;
				proyCerrados = false;
				centerPanel.setWidget(new ProjectTable(datos.getProyectosPerdidos()));
			}
		});
		panel.add(perdidos);

		Anchor cancelados = new Anchor("Cancelados");
		cancelados.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
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
				proySelected=null;
				accion=true;
				proyCerrados = false;	
				VerticalPanel v= new VerticalPanel();
				v.add(new ProjectTable(datos.getProyectosSinCalificar()));
				v.add(getCalificarAction());
				
				centerPanel.setWidget(v);
				
			}
		});
		panel.add(sinCalificar);

		Anchor sinRecibirCalif = new Anchor("Pendientes de recibir calificaci&oacute;n", true);
		sinRecibirCalif.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				proySelected=null;
				accion=false;
				proyCerrados = false;
				centerPanel.setWidget(new ProjectTable(datos.getProyectosSinRecibirCalif()));
			}
		});
		panel.add(sinRecibirCalif);

		panel.setWidth("50px");
		return panel;
	}

	private class ProjectTable extends FlexTable
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
			setWidget(0, 0, new HTML("Proyecto"));
			setWidget(0, 1, new HTML("Mi oferta"));
			setWidget(0, 2, new HTML("Moneda"));
			setWidget(0, 3, new HTML("D&iacute;as"));
			setWidget(0, 4, new HTML("Es la menor"));
			setWidget(0, 5, new HTML("Fecha cierre proy."));
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

				Oferta ofertaPropia = null;
				Oferta menorOferta = null;
				for (final Oferta of : proyecto.getOfertas())
				{
					if (of.getUsuario().getLogin().equals(LoginWidget.getCurrentUser()))
					{
						ofertaPropia = of;
						break;
					}

					if (menorOferta == null || of.getMonto() < menorOferta.getMonto())
						menorOferta = of;
				}

				if (ofertaPropia != null)
				{
					Anchor aOferta = new Anchor("" + ofertaPropia.getMonto());
					final Oferta finalOferta = ofertaPropia;
					aOferta.addClickHandler(new ClickHandler()
					{
						public void onClick(ClickEvent event)
						{
							centerPanel.setWidget(new OfertaWidget(OfertaDto.fromOferta(finalOferta), proyecto.getDescripcion()));
						}
					});
					setWidget(row, 1, aOferta);

					setWidget(row, 2, new HTML(ofertaPropia.getMoneda().getDescription()));
					setWidget(row, 3, new HTML("" + ofertaPropia.getDias()));
					setWidget(row, 4, new HTML(ofertaPropia.equals(menorOferta) ? "SI" : "NO"));
				}

				DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
				setWidget(row, 5, new HTML(format.format(proyecto.getFecha())));
				if(accion)
					setWidget(row, 6, getActionButton(proyecto));

				row++;
			}
		}
	}
}
