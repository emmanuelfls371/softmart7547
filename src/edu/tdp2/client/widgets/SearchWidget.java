package edu.tdp2.client.widgets;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;

import edu.tdp2.client.ProjectList;
import edu.tdp2.client.SoftmartConstants;
import edu.tdp2.client.dto.FiltroDto;
import edu.tdp2.client.model.Moneda;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;


public class SearchWidget extends VerticalPanel{
	
	
	protected String tituloWidget;
	protected String anchoWidget;
	protected String anchoTabla;
	
	private ProjectList projectList;

	protected FiltroDto dto;
	protected Map<FormFields, Widget> widgets = new HashMap<FormFields, Widget>();
	private FormPanel panelBusqueda;
	
	
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
		dto = new FiltroDto();
		init();
	}

	private void buildWidget()
	{
		panelBusqueda=new FormPanel();
		FlexTable table = getTabla();
		panelBusqueda.add(table);
		//panelBusqueda.addSubmitHandler(new SearchSubmitHandler());
		//panelBusqueda.addSubmitCompleteHandler(new SearchSubmitCompleteHandler());
		add(panelBusqueda);
	}
	
	private FlexTable getTabla()
	{
		FlexTable table = new FlexTable();
		table.setWidget(0, 0, new HTML(tituloWidget));

		table.setWidget(0, 1, ClientUtils.getBackAnchor());

		int row = 1;
		table.getWidget(0, 0).setWidth(anchoWidget);
		for (FormFields field : this.values())
		{
			table.setWidget(row, 0, new HTML("<b>" + field.getDescription() + "</b>"));
			table.setWidget(row, 1, widgets.get(field));
			row++;
		}
		table.setWidget(row, 1, getSubmitPanel());
		table.setWidth(anchoTabla);
		return table;
	}
	
	private void init()
	{
		this.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		populateWidgets();
		buildWidget();
	}
	
	private HorizontalPanel getSubmitPanel()
	{
		HorizontalPanel submitPanel = new HorizontalPanel();
		submitPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		submitPanel.setWidth("100%");
		Button submit = new Button("Buscar", new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				this.onSubmit(event);
			}

			private void onSubmit(ClickEvent event) {
				
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
								if(projectList!=null)remove(projectList);
								projectList=new ProjectList(listaProy);
								add(projectList);
							}
						}
					});

				
			}				
		});
		submitPanel.add(submit);
		return submitPanel;
	}

	
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
	
	
	protected FormFields[] values()
	{
		return SearchFields.values();
	}

	
	
	private enum SearchFields implements FormFields
	{
		Presupuesto, FechaDesde("Fecha desde de cierre de la oferta"), FechaHasta("Fecha hasta de cierre de la oferta"), Nivel("Nivel de reputaci&oacute;n"), Dificultad, Tamanio(
				"Tama&ntilde;o");

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

}