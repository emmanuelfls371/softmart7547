package edu.tdp2.client.widgets;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.ProjectList;
import edu.tdp2.client.SoftmartConstants;
import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.dto.FiltroDto;
import edu.tdp2.client.dto.ProyectoDto;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;


public class SearchWidget extends FormWidget{
	
	private static SearchWidget instance;
	private static final Format DATE_FORMAT = new DateFormat();
	private SoftmartConstants constants;

	public static SearchWidget getInstance()
	{
		if (instance == null)
			instance = new SearchWidget();
		return instance;
	}

	private SearchWidget()
	{
		constants = (SoftmartConstants) GWT.create(SoftmartConstants.class);
		tituloWidget = "<b>Búsqueda por filtros</b>";
		anchoWidget = "200px";
		anchoTabla = "100px";
		url = "search";
		dto = new FiltroDto();
		init();
	}

	@Override
	protected void populateWidgets()
	{
		FlowPanel horiz = new FlowPanel();
				
		TextBox t = new TextBox();
		t.setMaxLength(50);
		t.setName(SearchFields.Presupuesto.toString());
		horiz.add(t);
		
		final ListBox lisMonedas = new ListBox();
		lisMonedas.setName(SearchFields.Presupuesto.toString());

		lisMonedas.clear();
		lisMonedas.addItem("----Elija Moneda----", "");
		for (Moneda moneda : Moneda.values())
			lisMonedas.addItem(moneda.getDescription(), moneda.name());
		horiz.add(lisMonedas);
		widgets.put(SearchFields.Presupuesto, horiz);
		
		// Create a basic date picker
		DateBox date = new DateBox();
		date.getDatePicker().setValue(new Date());
		date.setFormat(DATE_FORMAT);
		widgets.put(SearchFields.FechaDesde, date);
		
		// Create a basic date picker
		DateBox date2 = new DateBox();
		date2.getDatePicker().setValue(new Date());
		date2.setFormat(DATE_FORMAT);
		widgets.put(SearchFields.FechaHasta, date2);
		
		final ListBox lisNivel = new ListBox();
		lisNivel.setName(SearchFields.Nivel.toString());

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
		widgets.put(SearchFields.Nivel, lisNivel);

		final FlowPanel panel = new FlowPanel();
		panel.setStyleName(SearchFields.Dificultad.toString());

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
					panel.add(new RadioButton(SearchFields.Dificultad.toString(), d));
			}
		};
		ClientUtils.getSoftmartService().getDificultades(dificultadesCallback);
		widgets.put(SearchFields.Dificultad, panel);

		final FlowPanel panel2 = new FlowPanel();
		panel2.setStyleName(SearchFields.Tamanio.toString());

		AsyncCallback<List<String>> tamaniosCallback = new AsyncCallback<List<String>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar los tamanios");
			}

			public void onSuccess(List<String> tamanios)
			{
				panel2.clear();
				for (String tamanio : tamanios)
					panel2.add(new RadioButton(SearchFields.Tamanio.toString(), tamanio));
			}
		};
		ClientUtils.getSoftmartService().getTamanios(tamaniosCallback);
		widgets.put(SearchFields.Tamanio, panel2);
		
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
		
	}

	@Override
	protected FormFields[] values()
	{
		return SearchFields.values();
	}

	private final class ProjectSubmitHandler implements SubmitHandler
	{
		public void onSubmit(SubmitEvent event)
		{
			dto = new FiltroDto();
			FiltroDto filtroDto = (FiltroDto) dto;
				
			FlowPanel panel = (FlowPanel) instance.widgets.get(SearchFields.Presupuesto);

			TextBox rango = (TextBox) panel.getWidget(0);
			filtroDto.setPresupuesto(rango.getValue());

			ListBox lisMonedas = (ListBox) panel.getWidget(1);
			filtroDto.setMoneda(lisMonedas.getValue(lisMonedas.getSelectedIndex()));

			DateBox dateFecha = (DateBox) instance.widgets.get(SearchFields.FechaDesde);
			filtroDto.setFechaDesde(dateFecha.getValue());
			
			DateBox dateFecha2 = (DateBox) instance.widgets.get(SearchFields.FechaHasta);
			filtroDto.setFechaHasta(dateFecha2.getValue());
			
			ListBox lisNivel = (ListBox) instance.widgets.get(SearchFields.Nivel);
			filtroDto.setReputacion(lisNivel.getValue(lisNivel.getSelectedIndex()));

			FlowPanel panelDificultad = (FlowPanel) instance.widgets.get(SearchFields.Dificultad);
			for (Widget widget : panelDificultad)
			{
				RadioButton b = (RadioButton) widget;
				if (b.getValue())
					filtroDto.setComplejidad(b.getHTML());
			}

			FlowPanel panelTamanio = (FlowPanel) instance.widgets.get(SearchFields.Tamanio);
			for (Widget widget : panelTamanio)
			{
				RadioButton b = (RadioButton) widget;
				if (b.getValue())
					filtroDto.setTamanio(b.getHTML());
			}
			
			filtroDto.setUsuario(LoginWidget.getCurrentUser());
			
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

				final FiltroDto filtro= (FiltroDto)dto;
				ClientUtils.getSoftmartService().filterProject(filtro, new AsyncCallback<List<Proyecto>>()
				{					
					
					public void onFailure(Throwable caught)
					{
						Window.alert("Error inesperado, no se pudo realizar la búsqueda");
					}
				

					public void onSuccess(List<Proyecto> listaProy)
					{
						if (listaProy == null)
							Window.alert("Error inesperado, no se pudo realizar la búsqueda");
						else{
													
							clear();
							setMethod(METHOD_POST);
							setEncoding(FormPanel.ENCODING_MULTIPART);
							setAction(GWT.getModuleBaseURL() + url);
							populateWidgets();
							
							//History.newItem("");
							ProjectList projectList=new ProjectList(listaProy);
							//add(projectList);
							widgets.put(SearchFields.ListaProy, projectList);
							
							FlowPanel panel = (FlowPanel) instance.widgets.get(SearchFields.Presupuesto);
							((TextBox)panel.getWidget(0)).setValue(filtro.getPresupuesto());
							//((ListBox) panel.getWidget(1)).setItemSelected(index, selected);
							
							((DateBox) instance.widgets.get(SearchFields.FechaDesde)).setValue(filtro.getFechaDesde());
							((DateBox) instance.widgets.get(SearchFields.FechaHasta)).setValue(filtro.getFechaHasta());
							
							if(filtro.getReputacion()!=null&&!filtro.getReputacion().isEmpty()){							
								int i=0;
								boolean encontrado=false;
								while (i<((ListBox) instance.widgets.get(SearchFields.Nivel)).getItemCount()-1&&encontrado){
									if(filtro.getReputacion().equals(((ListBox) instance.widgets.get(SearchFields.Nivel)).getValue(i)))
										encontrado=true;
									else
										i++;
								}
								((ListBox) instance.widgets.get(SearchFields.Nivel)).setItemSelected(i, true);
							}
							if(filtro.getComplejidad()!=null){
								FlowPanel panelDificultad = (FlowPanel) instance.widgets.get(SearchFields.Dificultad);
								for (Widget widget : panelDificultad)
								{
									RadioButton b = (RadioButton) widget;
									if (b.getHTML().equals(filtro.getComplejidad()))
										b.setValue(true);
								}
							}
							if(filtro.getTamanio()!=null){
								FlowPanel panelTamanio = (FlowPanel) instance.widgets.get(SearchFields.Tamanio);
								for (Widget widget : panelTamanio)
								{
									RadioButton b = (RadioButton) widget;
									if (b.getHTML().equals(filtro.getTamanio()))
										b.setValue(true);
								}
							}
							buildWidget();
							
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
	
	private enum SearchFields implements FormFields
	{
		Presupuesto, FechaDesde("Fecha desde de cierre de la oferta"), FechaHasta("Fecha hasta de cierre de la oferta"), Nivel("Nivel de reputaci&oacute;n"), Dificultad, Tamanio(
				"Tama&ntilde;o"), ListaProy("Proyectos encontrados");

		private SearchFields(String description)
		{
			this.description = description;
		}

		public String description;

		private SearchFields()
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
		return GWT.create(FiltroDto.class);
	}	
}
