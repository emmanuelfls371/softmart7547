package edu.tdp2.client.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.utils.ClientUtils;

public class RegistrationWidget extends FormPanel
{
	private static RegistrationWidget instance;

	private enum RegistrationFields
	{
		Nombre, Apellido, Email, Usuario("Nombre de usuario"), Clave("Contrase&ntilde;a"), Pais("Pa&iacute;s"), Ciudad, CodPostal(), DescripPerfil(), Logo(
				"Logo o imagen (tama&ntilde;o m&aacute;ximo 200KB");

		private String description;

		private RegistrationFields()
		{
			description = name();
		}

		private RegistrationFields(String description)
		{
			this.description = description;
		}

		public String getDescription()
		{
			return description;
		}
	}

	private Map<RegistrationFields, Widget> widgets = new HashMap<RegistrationFields, Widget>();

	private class SuggestCallback implements AsyncCallback<List<String>>
	{
		private MultiWordSuggestOracle oracle;

		public SuggestCallback(MultiWordSuggestOracle oracle)
		{
			this.oracle = oracle;
		}

		public void onFailure(Throwable caught)
		{
			Window.alert("No se pudo recuperar las ciudades");
		}

		public void onSuccess(List<String> paises)
		{
			if (oracle == null)
				throw new RuntimeException("El oracle no puede ser nulo");
			oracle.clear();
			oracle.addAll(paises);
		}
	};

	private SuggestCallback suggestCallback;

	public static RegistrationWidget getInstance()
	{
		if (instance == null)
			instance = new RegistrationWidget();
		return instance;
	}

	private RegistrationWidget()
	{
		/*TODO Armar el upload bien
		 * setMethod(METHOD_POST);
		setEncoding(FormPanel.ENCODING_MULTIPART);
		setAction(GWT.getModuleBaseURL() + "uploads.target");*/
		populateWidgets();
		buildWidget();
	}

	private void populateWidgets()
	{
		TextBox t = new TextBox();
		t.setMaxLength(50);
		widgets.put(RegistrationFields.Nombre, t);

		t = new TextBox();
		t.setMaxLength(50);
		widgets.put(RegistrationFields.Apellido, t);

		t = new TextBox();
		t.setMaxLength(255);
		widgets.put(RegistrationFields.Email, t);

		t = new TextBox();
		t.setMaxLength(50);
		widgets.put(RegistrationFields.Usuario, t);

		t = new PasswordTextBox();
		t.setMaxLength(50);
		widgets.put(RegistrationFields.Clave, t);

		final ListBox lisPaises = new ListBox();
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar los paises");
			}

			public void onSuccess(List<String> paises)
			{
				lisPaises.clear();
				for (String pais : paises)
					lisPaises.addItem(pais);
			}
		};
		ClientUtils.getSoftmartService().getPaises(callback);
		lisPaises.addChangeListener(new ChangeListener()
		{
			public void onChange(Widget sender)
			{
				ClientUtils.getSoftmartService().getCiudades(lisPaises.getItemText(lisPaises.getSelectedIndex()),
						suggestCallback);
			}
		});
		widgets.put(RegistrationFields.Pais, lisPaises);

		MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
		SuggestBox suggestBox = new SuggestBox(oracle);
		suggestCallback = new SuggestCallback(oracle);
		widgets.put(RegistrationFields.Ciudad, suggestBox);

		t = new TextBox();
		t.setMaxLength(10);
		widgets.put(RegistrationFields.CodPostal, t);

		t = new TextBox();
		t.setHeight("100px");
		widgets.put(RegistrationFields.DescripPerfil, t);

		// TODO Armar el upload bien
		FileUpload f = new FileUpload();
		widgets.put(RegistrationFields.Logo, f);
	}

	private void buildWidget()
	{
		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		FlexTable table = getTable();
		panel.add(table);
		add(panel);
	}

	private FlexTable getTable()
	{
		FlexTable table = new FlexTable();
		table.setWidget(0, 0, new HTML("<b>Nuevo usuario</b>"));
		int row = 1;
		table.getWidget(0, 0).setWidth("200px");
		for (RegistrationFields field : RegistrationFields.values())
		{
			table.setWidget(row, 0, new HTML("<b>" + field.getDescription() + "</b>"));
			table.setWidget(row, 1, widgets.get(field));
			row++;
		}
		table.setWidget(row, 1, getSubmitPanel());
		table.setWidth("100px");
		return table;
	}

	private HorizontalPanel getSubmitPanel()
	{
		HorizontalPanel submitPanel = new HorizontalPanel();
		submitPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		submitPanel.setWidth("100%");
		Button submit = new Button("Entrar", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				if (validate())
					submit();
			}
		});
		submitPanel.add(submit);
		return submitPanel;
	}

	protected boolean validate()
	{
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFormSubmit()
	{
		// TODO Auto-generated method stub
		return super.onFormSubmit();
	}
}
