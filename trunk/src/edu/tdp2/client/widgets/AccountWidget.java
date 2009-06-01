package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.CalificationWidget;
import edu.tdp2.client.TipoCalificacionWrapper.TipoCalificacion;
import edu.tdp2.client.dto.ContratoDto;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class AccountWidget extends DockPanel
{

	private AccountConstants constants;
	protected Proyecto proySelected;
	protected VerticalPanel vCerrados;
	protected boolean proyCerrados;
	protected Anchor califHecha;
	protected Anchor califRecibida;
	protected VerticalPanel centerPanel = new VerticalPanel();
	protected HorizontalPanel eastPanel = new HorizontalPanel();
	protected HorizontalPanel underPanel = new HorizontalPanel();

	protected boolean accion;

	public AccountWidget()
	{
		constants = GWT.create(AccountConstants.class);
		proySelected = null;
		vCerrados = null;
		proyCerrados = false;
		accion = true;
	}

	public void onShowNewCalificacion(Proyecto project)
	{
		putAlone(NewCalificationWidget.getInstance(project));
	}

	protected RadioButton getActionButton(final Proyecto proyecto)
	{

		RadioButton r = new RadioButton(constants.accion(), "");

		r.addValueChangeHandler(new ValueChangeHandler<Boolean>()
		{
			public void onValueChange(ValueChangeEvent<Boolean> event)
			{
				final boolean value = event.getValue();
				if (value == true)
					proySelected = proyecto;
				if (proyCerrados)
				{
					onShowOptionCalifRecibida();
					onShowOptionCalifHecha();
				}
			}
		});

		return r;
	}

	protected Anchor getCalificarAction()
	{

		proyCerrados = false;
		Anchor menuLink = new Anchor(constants.calificar());
		menuLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				Proyecto proyecto = proySelected;
				if (proyecto == null)
					Window.alert(constants.debeSeleccionarProyCalif());
				else
					onShowNewCalificacion(proyecto);
			}
		});
		return menuLink;

	}

	protected void onShowCalification(ContratoDto contrato, TipoCalificacion tipo)
	{
		putAlone(new CalificationWidget(contrato.getCalif(), contrato.getProyecto().getNombre(), tipo));
	}

	protected void onShowOptionCalifHecha()
	{

		AsyncCallback<List<ContratoDto>> callback2 = new AsyncCallback<List<ContratoDto>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failVerCalif());
			}

			public void onSuccess(List<ContratoDto> contratos)
			{
				if (proySelected != null)
				{

					ContratoDto c = null;
					for (ContratoDto contrato : contratos)
						if (contrato.getProyecto().getNombre().equals(proySelected.getNombre()))
						{
							c = contrato;
							break;
						}
					if (c == null)
						// califHecha.setEnabled(false);
						califHecha.setStyleName("a-disabled");
					else
					{
						califHecha.setStyleName("a-enable");
						final ContratoDto c2 = c;
						califHecha.setEnabled(true);
						califHecha.addClickHandler(new ClickHandler()
						{
							public void onClick(ClickEvent event)
							{
								if (proySelected == null)
									Window.alert(constants.debeSeleccionarProy());
								else
									onShowCalification(c2, TipoCalificacion.Hecha);
							}
						});
					}
					vCerrados.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
					eastPanel.add(vCerrados);
				}

			}
		};

		ClientUtils.getSoftmartService().getCalificacionesHechas(LoginWidget.getCurrentUser(), callback2);

	}

	protected void onShowOptionCalifRecibida()
	{
		AsyncCallback<List<ContratoDto>> callback = new AsyncCallback<List<ContratoDto>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failVerCalif());
			}

			public void onSuccess(List<ContratoDto> contratos)
			{
				if (proySelected != null)
				{

					ContratoDto c = null;
					for (ContratoDto contrato : contratos)
						if (contrato.getProyecto().getNombre().equals(proySelected.getNombre()))
						{
							c = contrato;
							break;
						}
					if (c == null)
						// califRecibida.setEnabled(false);
						califRecibida.setStyleName("a-disabled");
					// Window.alert("El proyecto no ha recibido calificación");
					else
					{
						califRecibida.setStyleName("a-enable");
						final ContratoDto c2 = c;
						califRecibida.setEnabled(true);
						califRecibida.addClickHandler(new ClickHandler()
						{

							public void onClick(ClickEvent event)
							{
								if (proySelected == null)
									Window.alert(constants.debeSeleccionarProy());
								else
									onShowCalification(c2, TipoCalificacion.Recibida);
							}
						});
					}
					vCerrados.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
					eastPanel.add(vCerrados);
				}
			}
		};
		ClientUtils.getSoftmartService().getCalificacionesRecibidas(LoginWidget.getCurrentUser(), callback);

	}

	protected void putAlone(Widget widget)
	{
		centerPanel.clear();
		eastPanel.clear();
		centerPanel.add(widget);
	}

	protected void setLinkCalificacion()
	{

		califRecibida = new Anchor(constants.verCalifRecibida(), true);
		califHecha = new Anchor(constants.verCalifHecha(), true);
		vCerrados.add(califRecibida);
		vCerrados.add(califHecha);
	}

}
