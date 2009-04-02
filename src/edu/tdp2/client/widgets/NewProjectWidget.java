package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.utils.ClientUtils;

public class NewProjectWidget extends FormWidget
{
	private static NewProjectWidget instance;

	public static NewProjectWidget getInstance()
	{
		if (instance == null)
			instance = new NewProjectWidget();
		return instance;
	}

	private NewProjectWidget()
	{
		nombreWidget = "<b>Nuevo proyecto</b>";
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
				lisRangos.addItem("---- Elija un rango en Pesos ----", "");
				for (String presup : presupuestos)
					lisRangos.addItem(presup, presup);
			}
		};
		ClientUtils.getSoftmartService().getPresupuestos(projectCallback);
		widgets.put(ProjectFields.Presupuesto, lisRangos);

		FlowPanel panel3 = new FlowPanel();

		t = new TextBox();
		t.setMaxLength(2);
		t.setWidth("30px");
		panel3.add(t);

		t = new TextBox();
		t.setMaxLength(2);
		t.setWidth("30px");
		panel3.add(t);

		t = new TextBox();
		t.setMaxLength(4);
		t.setWidth("60px");
		panel3.add(t);

		widgets.put(ProjectFields.Fecha, panel3);

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
				lisNivel.addItem("---- Elija un nivel de reputación mínima ----", "");
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
		addFormHandler(new ProjectFormHandler());
	}

	@Override
	protected boolean validate()
	{
		createErrorMessage();
		return super.buildErrorMessage();
	}

	@Override
	protected FormFields[] values()
	{
		return ProjectFields.values();
	}

	private final class ProjectFormHandler implements FormHandler
	{
		public void onSubmit(FormSubmitEvent event)
		{
			dto = new ProyectoDto();
			ProyectoDto proyectoDto = (ProyectoDto) dto;
			proyectoDto.setNombre(((TextBox) instance.widgets.get(ProjectFields.Nombre)).getText());

			ListBox lisRangos = (ListBox) instance.widgets.get(ProjectFields.Presupuesto);
			proyectoDto.setPresupuesto(lisRangos.getValue(lisRangos.getSelectedIndex()));

			FlowPanel panel = (FlowPanel) instance.widgets.get(ProjectFields.Fecha);
			try
			{
				proyectoDto.setDia(Integer.parseInt(((TextBox) panel.getWidget(0)).getText()));
				proyectoDto.setMes(Integer.parseInt(((TextBox) panel.getWidget(1)).getText()));
				proyectoDto.setAnio(Integer.parseInt(((TextBox) panel.getWidget(2)).getText()));

				ListBox lisNivel = (ListBox) instance.widgets.get(ProjectFields.Nivel);
				proyectoDto.setNivel(lisNivel.getValue(lisNivel.getSelectedIndex()));

				FlowPanel panel3 = (FlowPanel) instance.widgets.get(ProjectFields.Dificultad);
				for (Widget widget : panel3)
				{
					RadioButton b = (RadioButton) widget;
					if (b.isChecked())
						proyectoDto.setDificultad(b.getHTML());
				}

				FlowPanel panel2 = (FlowPanel) instance.widgets.get(ProjectFields.Tamanio);
				for (Widget widget : panel2)
				{
					RadioButton b = (RadioButton) widget;
					if (b.isChecked())
						proyectoDto.setTamanio(b.getHTML());
				}

				proyectoDto.setDescripcion(((TextBox) instance.widgets.get(ProjectFields.Descripcion)).getText());

				/*
				 * String loginCookie = Cookies.getCookie(constants.loginCookieName()); String currentUser =
				 * loginCookie.split(";")[0]; ((ProyectoDto) dto).setUsuario(currentUser);
				 */

				if (!validate())
					event.setCancelled(true);
			}
			catch (NumberFormatException e)
			{
				Window.alert("La fecha indicada no es valida");
				validate();
				event.setCancelled(true);
			}
		}

		public void onSubmitComplete(FormSubmitCompleteEvent event)
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
		Nombre, Presupuesto, Fecha("Fecha de cierre de la oferta (dd/mm/yyyy)"), Nivel("Nivel de reputación"), Dificultad, Tamanio(
				"Tamaño"), Descripcion, Archivo("Archivo (opcional - m&aacute;ximo 5MB");

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
