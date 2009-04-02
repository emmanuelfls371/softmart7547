package edu.tdp2.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.SoftmartConstants;
import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.dto.OfertaDto;
import edu.tdp2.client.utils.ClientUtils;



public class NewOfertaWidget extends FormWidget{
	
	private static NewOfertaWidget instance;

	public static NewOfertaWidget getInstance()
	{
		if (instance == null)
			instance = new NewOfertaWidget();
		return instance;
	}

	private NewOfertaWidget()
	{
		nombreWidget = "<b>Nueva oferta</b>";
		anchoWidget = "200px";
		anchoTabla = "100px";
		url = "newoferta";
		dto = new OfertaDto();
		init();
	}

	@Override
	protected void populateWidgets() {
		
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
	protected boolean validate() {
		return super.buildErrorMessage();
	}

	@Override
	protected FormFields[] values() {
		return OfertaFields.values();
	}
	
	@Override
	protected void buildWidget()
	{
		super.buildWidget();
		addFormHandler(new OfertaFormHandler());
	}
	
	private final class OfertaFormHandler implements FormHandler
	{
		public void onSubmit(FormSubmitEvent event)
		{
			dto = new OfertaDto();
			OfertaDto ofertaDto = (OfertaDto) dto;
			try{
			FlowPanel panel = (FlowPanel) instance.widgets.get(OfertaFields.MailNotification);
			for (Widget widget : panel)
			{
				RadioButton b = (RadioButton) widget;
				if (b.isChecked())
					ofertaDto.setNotificacion(b.getHTML());					
			}
			
			ofertaDto.setDescripcion(((TextBox) instance.widgets.get(OfertaFields.Descripcion)).getText());

			ofertaDto.setMonto(Integer.parseInt(((TextBox) instance.widgets.get(OfertaFields.Presupuesto)).getText()));
			ofertaDto.setDias(Integer.parseInt(((TextBox) instance.widgets.get(OfertaFields.Dias)).getText()));
			
				
			//((OfertaDto) dto).setProyecto(ListProjectsWidget.getCurrentProject());
			
			createErrorMessage();
			if (!validate())
				event.setCancelled(true);
			
			}catch (NumberFormatException e){
				createErrorMessage();
				errMsgs.add("El formato de dias y/o monto no es valido");
				validate();
				event.setCancelled(true);
			}
				
		
		}

		public void onSubmitComplete(FormSubmitCompleteEvent event)
		{
			String results = event.getResults();
			if (results.startsWith("OK:"))
			{
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

			}
			else if (results.startsWith("ERROR:"))
				Window.alert(results.split(":", 2)[1]);
			else
				Window.alert(results);
		}
	}
	
	private enum OfertaFields implements FormFields
	{
		Presupuesto, Dias("D&iacute;as"), Descripcion("Descripci&oacute;n"), 
		MailNotification("Desea ser notificado de una oferta menor?");

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
	protected IValidator<Dto> getValidator() {
		return GWT.create(OfertaDto.class);
	}

}
