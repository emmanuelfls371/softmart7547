package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class AdminProjectsWidget extends AdminWidget
{
	private static AdminProjectsWidget instance;

	public static AdminProjectsWidget getInstance()
	{
		if (instance == null)
			instance = new AdminProjectsWidget();
		return instance;
	}

	@Override
	public void load()
	{
		History.newItem("AdminProjects");

		VerticalPanel vPanel = new VerticalPanel();
		final FlexTable table = new FlexTable();
		table.setCellPadding(5);
		statusMessage = new HTML();
		statusMessage.setHeight("50px");
		vPanel.add(statusMessage);
		vPanel.add(table);
		container.add(vPanel, "Proyectos");

		AsyncCallback<List<Proyecto>> callback = new AsyncCallback<List<Proyecto>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar los proyectos");
			}

			public void onSuccess(List<Proyecto> proyectos)
			{
				table.setWidget(0, 0, new HTML("Usuario"));
				table.setWidget(0, 1, new HTML("Nombre"));
				table.setWidget(0, 2, new HTML("Presupuesto"));
				table.setWidget(0, 3, new HTML("Moneda"));
				table.setWidget(0, 4, new HTML("Tama&ntilde;o"));
				table.setWidget(0, 5, new HTML("Complejidad"));
				table.setWidget(0, 6, new HTML("Fecha cierre"));
				table.setWidget(0, 7, new HTML("Revisado"));
				table.setWidget(0, 9, new HTML("Destacado"));
				int row = 1;
				for (Proyecto p : proyectos)
				{
					table.setWidget(row, 0, new HTML(p.getUsuario().getLogin()));
					table.setWidget(row, 1, new HTML(p.getNombre()));
					int ps = p.getMaxPresupuesto();
					table.setWidget(row, 2, new HTML(p.getMinPresupuesto() + " a " + (ps == -1 ? "m&aacute;s" : ps)));
					table.setWidget(row, 3, new HTML(p.getMoneda().getDescription()));
					table.setWidget(row, 4, new HTML(p.getTamanio()));
					table.setWidget(row, 5, new HTML(p.getDificultad()));
					DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
					table.setWidget(row, 6, new HTML(format.format(p.getFecha())));
					table.setWidget(row, 7, getCheckBoxRevisado(p));
					table.setWidget(row, 8, getAnchorCancelarProyecto(p));
					table.setWidget(row, 9, getCheckBoxDestacado(p));
					row++;
				}
			}
		};
		ClientUtils.getSoftmartService().getActiveProjects(callback);
	}

	private Anchor getAnchorCancelarProyecto(final Proyecto p)
	{
		Anchor a = new Anchor("Cancelar proyecto");
		a.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				if (!Window.confirm("Confirme que desea cancelar el proyecto"))
					return;
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert("No se pudo cancelar el proyecto");
					}

					public void onSuccess(String result)
					{
						if (result == null)
							statusMessage.setHTML("El proyecto \"" + p.getNombre() + "\" ha sido cancelado");
						else
						{
							Window.alert(result);
							statusMessage.setHTML("Error al intentar cancelar el proyecto");
						}
					}
				};
				ClientUtils.getSoftmartService().cancelarProyectoXAdmin(p.getId(), callback);
			}
		});
		return a;
	}

	private CheckBox getCheckBoxRevisado(final Proyecto p)
	{
		CheckBox c = new CheckBox();
		c.setValue(p.isRevisado());
		c.addValueChangeHandler(new ValueChangeHandler<Boolean>()
		{
			public void onValueChange(ValueChangeEvent<Boolean> event)
			{
				final boolean value = event.getValue();
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{

					public void onFailure(Throwable caught)
					{
						Window.alert("No se pudo marcar el proyecto como revisado");
					}

					public void onSuccess(String result)
					{
						if (result == null)
							statusMessage.setHTML("El proyecto \"" + p.getNombre() + "\" se marc&oacute; como "
									+ (value ? "" : "no ") + "revisado");
						else
						{
							Window.alert(result);
							statusMessage.setHTML("Error al intentar marcar el proyecto como revisado");
						}
					}
				};
				ClientUtils.getSoftmartService().setProyectoRevisado(p.getId(), event.getValue(), callback);
			}
		});
		return c;
	}
	
	private CheckBox getCheckBoxDestacado(final Proyecto p){
		
		CheckBox c = new CheckBox();
		c.setValue(p.isDestacado());
		c.addValueChangeHandler(new ValueChangeHandler<Boolean>()
		{
			public void onValueChange(ValueChangeEvent<Boolean> event)
			{
				final boolean value = event.getValue();
				if(p.isRevisado()){
					AsyncCallback<String> callback = new AsyncCallback<String>()
					{
	
						public void onFailure(Throwable caught)
						{
							Window.alert("No se pudo marcar el proyecto como destacado");
						}
	
						public void onSuccess(String result)
						{
							if (result == null)
								statusMessage.setHTML("El proyecto \"" + p.getNombre() + "\" se marc&oacute; como "
										+ (value ? "" : "no ") + "destacado");
							else
							{
								Window.alert(result);
								statusMessage.setHTML("Error al intentar marcar el proyecto como destacado");
							}
						}
					};
					ClientUtils.getSoftmartService().setProyectoDestacado(p.getId(), event.getValue(), callback);
				}else{
					Window.alert("No se pudo marcar el proyecto. El proyecto aún no fue revisado");
				}
			}
		});
		return c;
	
	}
	
}
