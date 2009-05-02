package edu.tdp2.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class Admin implements EntryPoint
{
	private SimplePanel centerPanel;
	private HTML statusMessage = new HTML();

	public void onModuleLoad()
	{
		if (RootPanel.get("AdminIdentifierDiv") == null)
			return;
		DockPanel dPanel = new DockPanel();
		centerPanel = new SimplePanel();
		centerPanel.setSize("100%", "450px");
		dPanel.add(centerPanel, DockPanel.CENTER);

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(dPanel);
		hPanel.setWidth("100%");
		hPanel.setCellHorizontalAlignment(dPanel, HasHorizontalAlignment.ALIGN_CENTER);

		RootPanel.get().add(hPanel);

		addWidget();
	}

	private void addWidget()
	{
		VerticalPanel vPanel = new VerticalPanel();
		final FlexTable table = new FlexTable();
		vPanel.add(getAnchorVerUsuarios());
		vPanel.add(statusMessage);
		statusMessage.setHeight("50px");
		vPanel.add(table);
		centerPanel.add(vPanel);

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
					row++;
				}
			}
		};
		ClientUtils.getSoftmartService().getActiveProjects(callback);
	}

	private Anchor getAnchorVerUsuarios()
	{
		Anchor a = new Anchor("Ver usuarios");
		a.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				// TODO Implementar
				Window.alert("Not implemented!");
			}
		});
		return a;
	}

	private Anchor getAnchorCancelarProyecto(final Proyecto p)
	{
		Anchor a = new Anchor("Cancelar proyecto");
		a.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				if (!Window.confirm("Confirme que desea cancelar el proyecto?"))
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
							statusMessage.setHTML("Error al intentar marcar el proyecto como revisado");
						}
					}
				};
				ClientUtils.getSoftmartService().cancelarProyecto(p.getId(), callback);
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
				final ValueChangeEvent<Boolean> ev = event;
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{

					public void onFailure(Throwable caught)
					{
						Window.alert("No se pudo marcar el proyecto");
					}

					public void onSuccess(String result)
					{
						if (result == null)
							statusMessage.setHTML("El proyecto \"" + p.getNombre() + "\" se marc&oacute; como "
									+ (ev.getValue() ? "" : "no ") + "revisado\"");
						else
						{
							Window.alert(result);
							statusMessage.setHTML("Error al intentar marcar el proyecto");
						}
					}
				};
				ClientUtils.getSoftmartService().setProyectoRevisado(p.getId(), event.getValue(), callback);
			}
		});
		return c;
	}
}
