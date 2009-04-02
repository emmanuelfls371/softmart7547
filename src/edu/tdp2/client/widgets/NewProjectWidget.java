package edu.tdp2.client.widgets;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.validation.client.InvalidConstraint;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.SoftmartConstants;
import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.client.utils.ClientUtils;
import edu.tdp2.server.model.DificultadProyecto;
import edu.tdp2.server.model.NivelReputacion;
import edu.tdp2.server.model.Presupuesto;
import edu.tdp2.server.model.TamanioProyecto;



public class NewProjectWidget extends FormWidget{

	private static NewProjectWidget instance;
	
	
	public static NewProjectWidget getInstance()
	{
		if (instance == null){
			instance = new NewProjectWidget();
			
		}
		return instance;
	}
	
	private NewProjectWidget(){
		nombreWidget="<b>Nuevo proyecto</b>";
		anchoWidget="200px";
		anchoTabla="100px";
		url="newproject";
		dto=new ProyectoDto();
		init();
	}
	
	
	protected void populateWidgets()
	{
		TextBox t = new TextBox();
		t.setMaxLength(50);
		t.setName(ProjectFields.Nombre.toString());
		widgets.put(ProjectFields.Nombre, t);
		
		
		
		final ListBox lisRangos = new ListBox();
		lisRangos.setName(ProjectFields.Presupuesto.toString());
		
		AsyncCallback<List<String>> callbackP = new AsyncCallback<List<String>>()
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
		ClientUtils.getSoftmartService().getPresupuestos(callbackP);
		widgets.put(ProjectFields.Presupuesto, lisRangos);

		FlowPanel panel3=new FlowPanel();
		
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
		
		AsyncCallback<List<String>> callbackN = new AsyncCallback<List<String>>()
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
		ClientUtils.getSoftmartService().getNiveles(callbackN);
		widgets.put(ProjectFields.Nivel, lisNivel);
		
		final FlowPanel panel=new FlowPanel();
		panel.setStyleName(ProjectFields.Dificultad.toString());
		
		AsyncCallback<List<String>> callbackD = new AsyncCallback<List<String>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar las dificultades");
			}

			public void onSuccess(List<String> dificultades)
			{
				panel.clear();
				for(String d: dificultades)
					panel.add(new RadioButton(ProjectFields.Dificultad.toString(),d));
			}
		};
		ClientUtils.getSoftmartService().getDificultades(callbackD);
		widgets.put(ProjectFields.Dificultad, panel);
	

		final FlowPanel panel2=new FlowPanel();
		panel2.setStyleName(ProjectFields.Tamanio.toString());
		
		AsyncCallback<List<String>> callbackT = new AsyncCallback<List<String>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar las dificultades");
			}

			public void onSuccess(List<String> tamanios)
			{
				panel2.clear();
				for(String ta: tamanios){
					panel2.add(new RadioButton(ProjectFields.Tamanio.toString(),ta));
				}
			}
		};
		ClientUtils.getSoftmartService().getTamanios(callbackT);
		widgets.put(ProjectFields.Tamanio, panel2);
		
		t = new TextBox();
		t.setHeight("100px");
		t.setName(ProjectFields.Descripcion.toString());
		widgets.put(ProjectFields.Descripcion, t);
		
		FileUpload f = new FileUpload();
		f.setName(ProjectFields.Archivo.toString());
		widgets.put(ProjectFields.Archivo, f);
		
	}
	
	protected void buildWidget(){
		super.buildWidget();
		addFormHandler(new ProjectFormHandler());
	}

	@Override
	protected boolean validate() {
		createErrorMessage(ProyectoDto.class);
		return super.buildErrorMessage();
	}

	@Override
	protected FormFields[] values() {
		return ProjectFields.values();
	}
	
	private final class ProjectFormHandler implements FormHandler
	{
		public void onSubmit(FormSubmitEvent event)
		{
			dto = new ProyectoDto();
			((ProyectoDto) dto).setNombre(((TextBox) instance.widgets.get(ProjectFields.Nombre)).getText());
			
			
			ListBox lisRangos = (ListBox) instance.widgets.get(ProjectFields.Presupuesto);
			((ProyectoDto) dto).setPresupuesto(lisRangos.getValue(lisRangos.getSelectedIndex()));
			
			FlowPanel panel=(FlowPanel) instance.widgets.get(ProjectFields.Fecha);
			try{
			((ProyectoDto) dto).setDia(Integer.parseInt(((TextBox) panel.getWidget(0)).getText()));
			((ProyectoDto) dto).setMes(Integer.parseInt(((TextBox) panel.getWidget(1)).getText()));
			((ProyectoDto) dto).setAnio(Integer.parseInt(((TextBox) panel.getWidget(2)).getText()));
			
			
			ListBox lisNivel = (ListBox) instance.widgets.get(ProjectFields.Nivel);
			((ProyectoDto) dto).setNivel(lisNivel.getValue(lisNivel.getSelectedIndex()));
			
			FlowPanel panel3=(FlowPanel) instance.widgets.get(ProjectFields.Dificultad);
			Iterator<Widget> it3=panel3.iterator();
			while(it3.hasNext()){
				RadioButton b=(RadioButton) it3.next();
				if(b.isChecked()){
					((ProyectoDto) dto).setDificultad(b.getHTML());
				}
			}
			
			FlowPanel panel2=(FlowPanel) instance.widgets.get(ProjectFields.Tamanio);
			Iterator<Widget> it2=panel2.iterator();
			while(it2.hasNext()){
				RadioButton b=(RadioButton) it2.next();
				if(b.isChecked()){
					((ProyectoDto) dto).setTamanio(b.getHTML());
				}
			}
					
			((ProyectoDto) dto).setDescripcion(((TextBox) instance.widgets.get(ProjectFields.Descripcion)).getText());
			
			/*String loginCookie = Cookies.getCookie(constants.loginCookieName());
			String currentUser = loginCookie.split(";")[0];
			((ProyectoDto) dto).setUsuario(currentUser);*/
			
			if (!validate())
				event.setCancelled(true);
			
			}catch (NumberFormatException e){
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
		Nombre, Presupuesto, Fecha("Fecha de cierre de la oferta (dd/mm/yyyy)"), Nivel("Nivel de reputación"), Dificultad, Tamanio("Tamaño"), Descripcion,
		Archivo("Archivo (opcional - m&aacute;ximo 5MB");

		
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
	
}
