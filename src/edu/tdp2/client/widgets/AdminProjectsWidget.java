package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
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

	private AdminProjectsConstants constants;

	private VerticalPanel vPanel = new VerticalPanel();

	private AdminProjectsWidget()
	{
		constants = GWT.create(AdminProjectsConstants.class);
	}

	@Override
	public void load()
	{
		container.add(vPanel, constants.proyectos());
		statusMessage = new HTML();
		loadPanel();
	}

	public void loadPanel()
	{
		History.newItem("AdminProjects");

		vPanel.clear();
		final FlexTable table = new FlexTable();
		table.setCellPadding(5);
		statusMessage.setHeight("50px");
		vPanel.add(statusMessage);
		vPanel.add(table);

		AsyncCallback<List<Proyecto>> callback = new AsyncCallback<List<Proyecto>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failGetProjects());
			}

			public void onSuccess(List<Proyecto> proyectos)
			{
				table.setWidget(0, 0, new HTML(constants.usuario()));
				table.setWidget(0, 1, new HTML(constants.nombre()));
				table.setWidget(0, 2, new HTML(constants.presupuesto()));
				table.setWidget(0, 3, new HTML(constants.moneda()));
				table.setWidget(0, 4, new HTML(constants.tamano()));
				table.setWidget(0, 5, new HTML(constants.complejidad()));
				table.setWidget(0, 6, new HTML(constants.fechaCierre()));
				table.setWidget(0, 7, new HTML(constants.revisado()));
				table.setWidget(0, 9, new HTML(constants.destacado()));
				int row = 1;
				for (Proyecto p : proyectos)
				{
					table.setWidget(row, 0, new HTML(p.getUsuario().getLogin()));
					table.setWidget(row, 1, new HTML(p.getNombre()));
					int ps = p.getMaxPresupuesto();
					table.setWidget(row, 2, new HTML(p.getMinPresupuesto() + constants.preposicionA()
							+ (ps == -1 ? constants.mas() : ps)));
					table.setWidget(row, 3, new HTML(p.getMoneda().getDescription()));
					table.setWidget(row, 4, new HTML(p.getTamanio()));
					table.setWidget(row, 5, new HTML(p.getDificultad()));
					DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");
					table.setWidget(row, 6, new HTML(format.format(p.getFecha())));
					CheckBox c = getCheckBoxRevisado(p);
					CheckBox c2 = getCheckBoxDestacado(p);
					Anchor c3 = getAnchorCancelarProyecto(p);
					table.setWidget(row, 7, c);
					table.setWidget(row, 8, c3);
					table.setWidget(row, 9, c2);
					row++;
				}
			}
		};
		ClientUtils.getSoftmartService().getActiveProjects(callback);
	}

	private Anchor getAnchorCancelarProyecto(final Proyecto p)
	{
		Anchor a = new Anchor(constants.cancelarProyecto());
		if (p.isRevisado())
			// a.setEnabled(false);
			a.addStyleName("a-disabled");
		else
		{
			a.setEnabled(true);
			a.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					if (!Window.confirm(constants.confirmaCancelarProy()))
						return;
					AsyncCallback<String> callback = new AsyncCallback<String>()
					{
						public void onFailure(Throwable caught)
						{
							Window.alert(constants.failCancelProject());
						}

						public void onSuccess(String result)
						{
							if (result == null)
							{
								statusMessage.setHTML(constants.elProyecto() + p.getNombre()
										+ constants.haSidoCancelado());

								loadPanel();
							}
							else
							{
								Window.alert(result);
								statusMessage.setHTML(constants.errorCancelProject());
							}
						}
					};
					ClientUtils.getSoftmartService().cancelarProyectoXAdmin(AdminLoginWidget.getCurrentUser(),
							p.getId(), callback);
				}
			});
		}
		return a;
	}

	private CheckBox getCheckBoxDestacado(final Proyecto p)
	{

		CheckBox c = new CheckBox();
		if (p.isRevisado())
			c.addStyleName("a-disabled");
		else
			c.setEnabled(false);
		c.setValue(p.isDestacado());
		c.addValueChangeHandler(new ValueChangeHandler<Boolean>()
		{
			public void onValueChange(ValueChangeEvent<Boolean> event)
			{
				final boolean value = event.getValue();
				if (p.isRevisado() && !p.isCancelado() && !p.isCanceladoXAdmin())
				{
					AsyncCallback<String> callback = new AsyncCallback<String>()
					{

						public void onFailure(Throwable caught)
						{
							Window.alert(constants.failDestacar());
						}

						public void onSuccess(String result)
						{
							if (result == null)
								statusMessage.setHTML(constants.elProyecto() + p.getNombre() + constants.seMarcoComo()
										+ (value ? "" : constants.no()) + constants.destacadoMinusc());
							else
							{
								Window.alert(result);
								statusMessage.setHTML(constants.errorDestacar());
							}
						}
					};
					ClientUtils.getSoftmartService().setProyectoDestacado(p.getId(), event.getValue(), callback);
				}
				else
					Window.alert(constants.errorDestacarRevOCancel());
			}
		});
		return c;

	}

	private CheckBox getCheckBoxRevisado(final Proyecto p)
	{
		final CheckBox c = new CheckBox();
		c.setValue(p.isRevisado());
		if (p.isRevisado())
			c.setEnabled(false);
		c.addValueChangeHandler(new ValueChangeHandler<Boolean>()
		{
			public void onValueChange(ValueChangeEvent<Boolean> event)
			{
				final boolean value = event.getValue();
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert(constants.failRevisar());
					}

					public void onSuccess(String result)
					{
						if (result == null)
						{
							statusMessage.setHTML(constants.elProyecto() + p.getNombre() + constants.seMarcoComo()
									+ (value ? "" : constants.no()) + constants.revisadoMinusc());

							loadPanel();
						}
						else
						{
							Window.alert(result);
							statusMessage.setHTML(constants.errorRevisar());
						}
					}
				};
				ClientUtils.getSoftmartService().setProyectoRevisado(AdminLoginWidget.getCurrentUser(), p.getId(),
						event.getValue(), callback);
			}
		});
		return c;
	}

}
