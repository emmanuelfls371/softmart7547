package edu.tdp2.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class NewOfertaWidget extends FormWidget
{
	private enum OfertaFields implements FormFields
	{
		Proyecto(constants.proyecto()), Usuario(constants.comprador()), Presupuesto(constants.presupuesto()), Dias(
				constants.dias()), Descripcion(constants.descripcion()), MailNotification(constants.mailNotification());

		public String description;

		private OfertaFields(String description)
		{
			this.description = description;
		}

		public String getDescription()
		{
			return description;
		}
	}

	private final class OfertaSubmitHandler implements SubmitHandler
	{
		public void onSubmit(SubmitEvent event)
		{
			errMsgs.clear();
			dto = new OfertaDto();
			OfertaDto ofertaDto = (OfertaDto) dto;

			FlowPanel panel = (FlowPanel) instance.widgets.get(OfertaFields.MailNotification);
			for (Widget widget : panel)
			{
				RadioButton b = (RadioButton) widget;
				if (b.getValue())
					ofertaDto.setNotificacion(b.getHTML());
			}

			ofertaDto.setDescripcion(((TextArea) instance.widgets.get(OfertaFields.Descripcion)).getText());
			if (ofertaDto.getDescripcion().length() > 200)
				errMsgs.add(constants.errorDescription());

			FlowPanel f = (FlowPanel) instance.widgets.get(OfertaFields.Presupuesto);
			try
			{
				ofertaDto.setMonto(Float.parseFloat(((TextBox) f.getWidget(0)).getText()));

			}
			catch (NumberFormatException e)
			{
				errMsgs.add(constants.errorFormatoDiasMonto());
			}
			ofertaDto.setMoneda(((ListBox) f.getWidget(1)).getValue(((ListBox) f.getWidget(1)).getSelectedIndex()));

			try
			{
				ofertaDto.setDias(Integer.parseInt(((TextBox) instance.widgets.get(OfertaFields.Dias)).getText()));
			}
			catch (NumberFormatException e)
			{

				errMsgs.add(constants.verifiqueDias());
			}
			ofertaDto.setProyecto(projectId);

			ofertaDto.setUsuario(LoginWidget.getCurrentUser());

			if (!validate())
				return;

			ClientUtils.getSoftmartService().ofertar((OfertaDto) dto, new AsyncCallback<String>()
			{
				public void onFailure(Throwable caught)
				{
					Window.alert(constants.failOfertar());
				}

				public void onSuccess(String errMsg)
				{
					if (errMsg != null)
						Window.alert(errMsg);
					else
						reload();
				}
			});
		}
	}

	private static NewOfertaWidget instance;
	private static NewOfertaConstants constants = GWT.create(NewOfertaConstants.class);

	public static NewOfertaWidget getInstance(Proyecto project)
	{

		if (instance == null)
			instance = new NewOfertaWidget(project);
		((Label) instance.widgets.get(OfertaFields.Proyecto)).setText(project.getNombre());
		((Label) instance.widgets.get(OfertaFields.Usuario)).setText(project.getUsuario().getLogin());
		instance.projectId = project.getId();
		instance.moneda = project.getMoneda().getDescription();

		final FlowPanel f = (FlowPanel) instance.widgets.get(OfertaFields.Presupuesto);
		AsyncCallback<List<Moneda>> callback = new AsyncCallback<List<Moneda>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failGetMonedas());
			}

			public void onSuccess(List<Moneda> monedas)
			{
				((ListBox) f.getWidget(1)).clear();
				for (Moneda moneda : monedas)
					if (moneda.getDescription().compareTo(instance.moneda) == 0)
					{
						((ListBox) f.getWidget(1)).addItem(moneda.getDescription(), moneda.getDescription());
						((ListBox) f.getWidget(1)).setItemSelected(0, true);
					}
				((ListBox) f.getWidget(1)).setEnabled(false);
			}
		};
		ClientUtils.getSoftmartService().buscarMonedas(callback);

		return instance;
	}

	private List<String> errMsgs;

	private long projectId;

	private String moneda;

	private NewOfertaWidget(Proyecto project)
	{
		tituloWidget = constants.nuevaOferta();
		anchoWidget = "200px";
		anchoTabla = "100px";
		dto = new OfertaDto();
		errMsgs = new ArrayList<String>();
		init();
	}

	@Override
	protected void buildWidget()
	{
		super.buildWidget();
		addSubmitHandler(new OfertaSubmitHandler());
	}

	@Override
	protected IValidator<Dto> getValidator()
	{
		return GWT.create(OfertaDto.class);
	}

	@Override
	protected void populateWidgets()
	{
		widgets.put(OfertaFields.Proyecto, new Label());

		widgets.put(OfertaFields.Usuario, new Label());

		FlowPanel p = new FlowPanel();

		TextBox t = new TextBox();
		t.setMaxLength(50);
		t.setName(OfertaFields.Presupuesto.toString());
		p.add(t);

		final ListBox lisMonedas = new ListBox();
		lisMonedas.setName(OfertaFields.Presupuesto.toString());

		p.add(lisMonedas);
		widgets.put(OfertaFields.Presupuesto, p);

		t = new TextBox();
		t.setMaxLength(50);
		t.setName(OfertaFields.Dias.toString());
		widgets.put(OfertaFields.Dias, t);

		FlowPanel panel = new FlowPanel();
		panel.setStyleName(OfertaFields.MailNotification.toString());
		panel.add(new RadioButton(OfertaFields.MailNotification.toString(), constants.si()));
		panel.add(new RadioButton(OfertaFields.MailNotification.toString(), constants.no()));
		widgets.put(OfertaFields.MailNotification, panel);

		TextArea t2 = new TextArea();
		t2.setHeight("100px");
		t2.setWidth("500px");
		t2.setTextAlignment(TextBoxBase.ALIGN_JUSTIFY);
		t2.setName(OfertaFields.Descripcion.toString());
		widgets.put(OfertaFields.Descripcion, t2);
	}

	@Override
	protected void validate(List<String> errMsgs)
	{
		errMsgs.addAll(this.errMsgs);
	}

	@Override
	protected FormFields[] values()
	{
		return OfertaFields.values();
	}
}