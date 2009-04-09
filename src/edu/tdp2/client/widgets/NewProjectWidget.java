package edu.tdp2.client.widgets;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.SoftmartConstants;
import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.utils.ClientUtils;
import edu.tdp2.client.model.Moneda;

public class NewProjectWidget extends FormWidget
{
	private static NewProjectWidget instance;
	private static final Format DATE_FORMAT = new DateFormat();
	private SoftmartConstants constants;

	public static NewProjectWidget getInstance()
	{
		if (instance == null)
			instance = new NewProjectWidget();
		return instance;
	}

	private NewProjectWidget()
	{
		constants = (SoftmartConstants) GWT.create(SoftmartConstants.class);
		tituloWidget = "<b>Nuevo proyecto</b>";
		anchoWidget = "200px";
		anchoTabla = "100px";
		url = "newproject";
		dto = new ProyectoDto();
		init();
	}

	@Override
	protected void populateWidgets()
	{
		TextBox t = new TextBox();
		t.setMaxLength(50);
		t.setName(ProjectFields.Nombre.toString());
		widgets.put(ProjectFields.Nombre, t);

		FlowPanel horiz=new FlowPanel();
		
		final ListBox lisRangos = new ListBox();
		lisRangos.setName(ProjectFields.Presupuesto.toString());

		AsyncCallback<List<String>> projectCallback = new AsyncCallback<List<String>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar los rangos de presupuesto");
			}

			public void onSuccess(List<String> presupuestos)
			{
				lisRangos.clear();
				lisRangos.addItem(constants.elijaRango(), "");
				for (String presup : presupuestos)
					lisRangos.addItem(presup, presup);
			}
		};
		ClientUtils.getSoftmartService().getPresupuestos(projectCallback);
		horiz.add(lisRangos);
		
		final ListBox lisMonedas = new ListBox();
		lisMonedas.setName(ProjectFields.Presupuesto.toString());

		lisMonedas.clear();
		lisMonedas.addItem("----Elija Moneda----", "");
		for (Moneda moneda : Moneda.values())
				lisMonedas.addItem(moneda.getDescription(), moneda.name());	
		horiz.add(lisMonedas);
		widgets.put(ProjectFields.Presupuesto,horiz);

		// Create a basic date picker
		DateBox date = new DateBox();
		date.getDatePicker().setValue(new Date());
		date.setFormat(DATE_FORMAT);
		widgets.put(ProjectFields.Fecha, date);

		final ListBox lisNivel = new ListBox();
		lisNivel.setName(ProjectFields.Nivel.toString());

		AsyncCallback<List<String>> nivelesCallback = new AsyncCallback<List<String>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar los niveles");
			}

			public void onSuccess(List<String> niveles)
			{
				lisNivel.clear();
				lisNivel.addItem(constants.elijaReputacion(), "");
				for (String n : niveles)
					lisNivel.addItem(n, n);
			}
		};
		ClientUtils.getSoftmartService().getNiveles(nivelesCallback);
		widgets.put(ProjectFields.Nivel, lisNivel);

		final FlowPanel panel = new FlowPanel();
		panel.setStyleName(ProjectFields.Dificultad.toString());

		AsyncCallback<List<String>> dificultadesCallback = new AsyncCallback<List<String>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar las dificultades");
			}

			public void onSuccess(List<String> dificultades)
			{
				panel.clear();
				for (String d : dificultades)
					panel.add(new RadioButton(ProjectFields.Dificultad.toString(), d));
			}
		};
		ClientUtils.getSoftmartService().getDificultades(dificultadesCallback);
		widgets.put(ProjectFields.Dificultad, panel);

		final FlowPanel panel2 = new FlowPanel();
		panel2.setStyleName(ProjectFields.Tamanio.toString());

		AsyncCallback<List<String>> tamaniosCallback = new AsyncCallback<List<String>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar las dificultades");
			}

			public void onSuccess(List<String> tamanios)
			{
				panel2.clear();
				for (String tamanio : tamanios)
					panel2.add(new RadioButton(ProjectFields.Tamanio.toString(), tamanio));
			}
		};
		ClientUtils.getSoftmartService().getTamanios(tamaniosCallback);
		widgets.put(ProjectFields.Tamanio, panel2);

		t = new TextBox();
		t.setHeight("100px");
		t.setName(ProjectFields.Descripcion.toString());
		widgets.put(ProjectFields.Descripcion, t);

		FileUpload f = new FileUpload();
		f.setName(ProjectFields.Archivo.toString());
		widgets.put(ProjectFields.Archivo, f);
	}

	@Override
	protected void buildWidget()
	{
		super.buildWidget();
		addSubmitHandler(new ProjectSubmitHandler());
		addSubmitCompleteHandler(new ProjectSubmitCompleteHandler());
	}

	@Override
	protected void validate(List<String> errMsgs)
	{
		if (((ProyectoDto) dto).getFecha() == null)
			errMsgs.add("Debe ingresar la fecha de cierre");
	}

	@Override
	protected FormFields[] values()
	{
		return ProjectFields.values();
	}

	private final class ProjectSubmitHandler implements SubmitHandler
	{
		public void onSubmit(SubmitEvent event)
		{
			dto = new ProyectoDto();
			ProyectoDto proyectoDto = (ProyectoDto) dto;
			proyectoDto.setNombre(((TextBox) instance.widgets.get(ProjectFields.Nombre)).getText());
			
			FlowPanel panel=(FlowPanel) instance.widgets.get(ProjectFields.Presupuesto);
			
			ListBox lisRangos = (ListBox) panel.getWidget(0);
			proyectoDto.setPresupuesto(lisRangos.getValue(lisRangos.getSelectedIndex()));

			ListBox lisMonedas = (ListBox) panel.getWidget(1);
			proyectoDto.setMoneda(lisMonedas.getValue(lisMonedas.getSelectedIndex()));
			
			DateBox dateFecha = (DateBox) instance.widgets.get(ProjectFields.Fecha);
			proyectoDto.setFecha(dateFecha.getValue());

			ListBox lisNivel = (ListBox) instance.widgets.get(ProjectFields.Nivel);
			proyectoDto.setNivel(lisNivel.getValue(lisNivel.getSelectedIndex()));

			FlowPanel panelDificultad = (FlowPanel) instance.widgets.get(ProjectFields.Dificultad);
			for (Widget widget : panelDificultad)
			{
				RadioButton b = (RadioButton) widget;
				if (b.getValue())
					proyectoDto.setDificultad(b.getHTML());
			}

			FlowPanel panelTamanio = (FlowPanel) instance.widgets.get(ProjectFields.Tamanio);
			for (Widget widget : panelTamanio)
			{
				RadioButton b = (RadioButton) widget;
				if (b.getValue())
					proyectoDto.setTamanio(b.getHTML());
			}

			proyectoDto.setDescripcion(((TextBox) instance.widgets.get(ProjectFields.Descripcion)).getText());

			((ProyectoDto) dto).setUsuario(LoginWidget.getCurrentUser());

			if (!validate())
				event.cancel();
		}
	}

	private final class ProjectSubmitCompleteHandler implements SubmitCompleteHandler
	{
		public void onSubmitComplete(SubmitCompleteEvent event)
		{
			String results = event.getResults();
			if (results.startsWith("OK:"))
			{
				((ProyectoDto) dto).setArchivo(results.split(":", 2)[1]);

				ClientUtils.getSoftmartService().publicar((ProyectoDto) dto, new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert("Error inesperado, no se pudo publicar el proyecto");
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

	private enum ProjectFields implements FormFields
	{
		Nombre, Presupuesto, Fecha("Fecha de cierre de la oferta"), Nivel("Nivel de reputaci&oacute;n"), Dificultad, Tamanio(
				"Tama&ntilde;o"), Descripcion("Descripci&oacute;n"), Archivo("Archivo (opcional - m&aacute;ximo 5MB)");

		private ProjectFields(String description)
		{
			this.description = description;
		}

		public String description;

		private ProjectFields()
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
		return GWT.create(ProyectoDto.class);
	}
}
