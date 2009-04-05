package edu.tdp2.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.utils.ClientUtils;

public class NewOfertaWidget extends FormWidget
{
	private static NewOfertaWidget instance;
	private List<String> errMsgs;

	public static NewOfertaWidget getInstance()
	{
		if (instance == null)
			instance = new NewOfertaWidget();
		return instance;
	}

	private NewOfertaWidget()
	{
		tituloWidget = "<b>Nueva oferta</b>";
		anchoWidget = "200px";
		anchoTabla = "100px";
		url = "newoferta";
		dto = new OfertaDto();
		errMsgs = new ArrayList<String>();
		init();
	}

	@Override
	protected void populateWidgets()
	{
		TextBox t = new TextBox();
		t.setMaxLength(50);
		t.setName(OfertaFields.Presupuesto.toString());
		widgets.put(OfertaFields.Presupuesto, t);

		t = new TextBox();
		t.setMaxLength(50);
		t.setName(OfertaFields.Dias.toString());
		widgets.put(OfertaFields.Dias, t);

		FlowPanel panel = new FlowPanel();
		panel.setStyleName(OfertaFields.MailNotification.toString());
		panel.add(new RadioButton(OfertaFields.MailNotification.toString(), "Si"));
		panel.add(new RadioButton(OfertaFields.MailNotification.toString(), "No"));
		widgets.put(OfertaFields.MailNotification, panel);

		t = new TextBox();
		t.setHeight("100px");
		t.setName(OfertaFields.Descripcion.toString());
		widgets.put(OfertaFields.Descripcion, t);
	}

	@Override
	protected FormFields[] values()
	{
		return OfertaFields.values();
	}

	@Override
	protected void buildWidget()
	{
		super.buildWidget();
		addSubmitHandler(new OfertaSubmitHandler());
		addSubmitCompleteHandler(new OfertaSubmitCompleteHandler());
	}

	@Override
	protected void validate(List<String> errMsgs)
	{
		errMsgs.addAll(this.errMsgs);
	}

	private final class OfertaSubmitHandler implements SubmitHandler
	{
		public void onSubmit(SubmitEvent event)
		{
			errMsgs.clear();
			dto = new OfertaDto();
			OfertaDto ofertaDto = (OfertaDto) dto;
			try
			{
				FlowPanel panel = (FlowPanel) instance.widgets.get(OfertaFields.MailNotification);
				for (Widget widget : panel)
				{
					RadioButton b = (RadioButton) widget;
					if (b.getValue())
						ofertaDto.setNotificacion(b.getHTML());
				}

				ofertaDto.setDescripcion(((TextBox) instance.widgets.get(OfertaFields.Descripcion)).getText());

				ofertaDto.setMonto(Integer.parseInt(((TextBox) instance.widgets.get(OfertaFields.Presupuesto))
						.getText()));
				ofertaDto.setDias(Integer.parseInt(((TextBox) instance.widgets.get(OfertaFields.Dias)).getText()));

				ofertaDto.setProyecto("Prueba");

				ofertaDto.setUsuario(LoginWidget.getCurrentUser());

				if (!validate())
					event.cancel();
			}
			catch (NumberFormatException e)
			{
				errMsgs.add("El formato de dias y/o monto no es valido");
				validate();
				event.cancel();
			}
		}
	}

	private final class OfertaSubmitCompleteHandler implements SubmitCompleteHandler
	{
		public void onSubmitComplete(SubmitCompleteEvent event)
		{
			String results = event.getResults();
			if (results.startsWith("OK:"))
				ClientUtils.getSoftmartService().ofertar((OfertaDto) dto, new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert("Error inesperado, no se pudo ofertar");
					}

					public void onSuccess(String errMsg)
					{
						if (errMsg != null)
							Window.alert(errMsg);
						else
							reload();
					}
				});
			else if (results.startsWith("ERROR:"))
				Window.alert(results.split(":", 2)[1]);
			else
				Window.alert(results);
		}
	}

	private enum OfertaFields implements FormFields
	{
		Presupuesto, Dias("D&iacute;as"), Descripcion("Descripci&oacute;n"), MailNotification(
				"Desea ser notificado de una oferta menor?");

		private OfertaFields(String description)
		{
			this.description = description;
		}

		public String description;

		private OfertaFields()
		{
			this.description = name();
		}

		public String getDescription()
		{
			return description;
		}
	}

	@Override
	protected IValidator<Dto> getValidator()
	{
		return GWT.create(OfertaDto.class);
	}
}