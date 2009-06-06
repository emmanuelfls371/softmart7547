package edu.tdp2.client.widgets;


import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.utils.ClientUtils;

public class NewProjectWidget extends FormWidget
{
	
	
	private enum ProjectFields implements FormFields
	{
		Nombre(constants.nombre()), Presupuesto(constants.presupuesto()), Fecha(constants.fechaCierre()), Nivel(
				constants.reputacion()), Dificultad(constants.dificultad()), Tamanio(constants.tamano()), Descripcion(
				constants.descripcion()), Archivo(constants.archivo());

		public String description;

		private ProjectFields(String description)
		{
			this.description = description;
		}

		public String getDescription()
		{
			return description;
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
						Window.alert(constants.failPublicar());
					}

					public void onSuccess(String errMsg)
					{
						if (errMsg != null)
							Window.alert(errMsg);
						else
						{
							Window.alert(constants.proyectoAltaOk());
							reload();
						}
					}
				});

			}
			else if (results.startsWith("ERROR:"))
				Window.alert(results.split(":", 2)[1]);
			else
				Window.alert(results);
		}
	}

	private final class ProjectSubmitHandler implements SubmitHandler
	{
		public void onSubmit(final SubmitEvent event)
		{
			dto = new ProyectoDto();
			final ProyectoDto proyectoDto = (ProyectoDto) dto;
			proyectoDto.setNombre(((TextBox) instance.widgets.get(ProjectFields.Nombre)).getText());

			FlowPanel panel = (FlowPanel) instance.widgets.get(ProjectFields.Presupuesto);

			if (hayRangos)
						{
							ListBox lisRangos = (ListBox) panel.getWidget(1);
							proyectoDto.setPresupuesto(lisRangos.getValue(lisRangos.getSelectedIndex()));
						}
						ListBox lisMonedas = (ListBox) panel.getWidget(0);
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

	private static NewProjectWidget instance;
	private static NewProjectConstants constants = GWT.create(NewProjectConstants.class);

	private static final Format DATE_FORMAT = new DateFormat();

	public static NewProjectWidget getInstance()
	{
		if (instance == null)
			instance = new NewProjectWidget();
		return instance;
	}

	private SoftmartConstants softmartConstants;

	private boolean hayRangos = false;

	protected Map<String, Float> mapMonedas = new HashMap<String, Float>();

	private NewProjectWidget()
	{
		softmartConstants = (SoftmartConstants) GWT.create(SoftmartConstants.class);
		tituloWidget = constants.nuevoProyecto();
		anchoWidget = "200px";
		anchoTabla = "100px";
		url = "newproject";
		dto = new ProyectoDto();
		init();
	}

	@Override
	protected void buildWidget()
	{
		super.buildWidget();
		addSubmitHandler(new ProjectSubmitHandler());
		addSubmitCompleteHandler(new ProjectSubmitCompleteHandler());
	}

	@Override
	protected IValidator<Dto> getValidator()
	{
		return GWT.create(ProyectoDto.class);
	}

	@Override
	protected void populateWidgets()
	{
		
		TextBox t = new TextBox();
		t.setMaxLength(50);
		t.setName(ProjectFields.Nombre.toString());
		widgets.put(ProjectFields.Nombre, t);

		final FlowPanel horiz = new FlowPanel();

		final ListBox lisMonedas = new ListBox();

		AsyncCallback<List<Moneda>> callback = new AsyncCallback<List<Moneda>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failGetMonedas());
			}

			public void onSuccess(List<Moneda> monedas)
			{
				lisMonedas.setName(ProjectFields.Presupuesto.toString());

				lisMonedas.clear();
				mapMonedas.clear();
				lisMonedas.addItem(constants.elijaMoneda(), "");
				for (Moneda moneda : monedas)
				{
					lisMonedas.addItem(moneda.getDescription(), moneda.getDescription());
					mapMonedas.put(moneda.getDescription(), moneda.getConversion());
				}
				horiz.add(lisMonedas);
			}
		};
		ClientUtils.getSoftmartService().buscarMonedas(callback);

		final ListBox lisRangos = new ListBox();
		lisRangos.setName(ProjectFields.Presupuesto.toString());

		lisMonedas.addChangeHandler(new ChangeHandler()
		{

			public void onChange(ChangeEvent event)
			{

				Float c = mapMonedas.get(lisMonedas.getValue(lisMonedas.getSelectedIndex()));

				AsyncCallback<List<String>> projectCallback = new AsyncCallback<List<String>>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert(constants.failGetPresupuestos());
					}

					public void onSuccess(List<String> presupuestos)
					{
						lisRangos.clear();
						lisRangos.addItem(softmartConstants.elijaRango(), "");
						for (String presup : presupuestos)
							lisRangos.addItem(presup, presup);
					}
				};
				if (c != null)
				{
					ClientUtils.getSoftmartService().getPresupuestos(c, projectCallback);
					hayRangos = true;
					horiz.add(lisRangos);
				}
			}
		});

		widgets.put(ProjectFields.Presupuesto, horiz);

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
				Window.alert(constants.failGetNiveles());
			}

			public void onSuccess(List<String> niveles)
			{
				lisNivel.clear();
				lisNivel.addItem(softmartConstants.elijaReputacion(), "");
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
				Window.alert(constants.failGetDificultades());
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
				Window.alert(constants.failGetTamanios());
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

	@SuppressWarnings("deprecation")
	@Override
	protected void validate(List<String> errMsgs)
	{
		if (((ProyectoDto) dto).getFecha() == null)
			errMsgs.add(constants.debeIngresarCierre());
		else if (((ProyectoDto) dto).getFecha().before(new Date())&&
				!((((ProyectoDto) dto).getFecha().getDate()==(new Date().getDate()))&&
				(((ProyectoDto) dto).getFecha().getMonth()==(new Date().getMonth()))&&
				(((ProyectoDto) dto).getFecha().getYear()==(new Date().getYear()))))
			errMsgs.add(constants.fechaCierreAnteriorHoy());
		
	}

	@Override
	protected FormFields[] values()
	{
		return ProjectFields.values();
	}
}
