package edu.tdp2.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.validation.client.InvalidConstraint;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.client.utils.ClientUtils;

// TODO Pasar los mensajes de RegistrationWidget a messages
public class RegistrationWidget extends FormPanel
{
	private static RegistrationWidget instance;
	private Map<RegistrationFields, Widget> widgets = new HashMap<RegistrationFields, Widget>();
	private UsuarioDto usuario;

	private SuggestCallback suggestCallback;

	public static RegistrationWidget getInstance()
	{
		if (instance == null)
			instance = new RegistrationWidget();
		return instance;
	}

	private RegistrationWidget()
	{
		setMethod(METHOD_POST);
		setEncoding(FormPanel.ENCODING_MULTIPART);
		setAction(GWT.getModuleBaseURL() + "registration");
		populateWidgets();
		buildWidget();
	}

	private void populateWidgets()
	{
		TextBox t = new TextBox();
		t.setMaxLength(50);
		t.setName(RegistrationFields.Nombre.toString());
		widgets.put(RegistrationFields.Nombre, t);

		t = new TextBox();
		t.setMaxLength(50);
		t.setName(RegistrationFields.Apellido.toString());
		widgets.put(RegistrationFields.Apellido, t);

		t = new TextBox();
		t.setMaxLength(255);
		t.setName(RegistrationFields.Email.toString());
		widgets.put(RegistrationFields.Email, t);

		t = new TextBox();
		t.setMaxLength(50);
		t.setName(RegistrationFields.Usuario.toString());
		widgets.put(RegistrationFields.Usuario, t);

		t = new PasswordTextBox();
		t.setMaxLength(50);
		t.setName(RegistrationFields.Clave.toString());
		widgets.put(RegistrationFields.Clave, t);

		final ListBox lisPaises = new ListBox();
		lisPaises.setName(RegistrationFields.Pais.toString());
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar los paises");
			}

			public void onSuccess(List<String> paises)
			{
				lisPaises.clear();
				lisPaises.addItem("---- Elija un pais ----", "");
				for (String pais : paises)
					lisPaises.addItem(pais, pais);
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
		t.setName(RegistrationFields.CodPostal.toString());
		t.setMaxLength(10);
		widgets.put(RegistrationFields.CodPostal, t);

		t = new TextBox();
		t.setHeight("100px");
		t.setName(RegistrationFields.DescripPerfil.toString());
		widgets.put(RegistrationFields.DescripPerfil, t);

		FileUpload f = new FileUpload();
		f.setName(RegistrationFields.Logo.toString());
		widgets.put(RegistrationFields.Logo, f);
	}

	private void buildWidget()
	{
		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		FlexTable table = getTable();
		panel.add(table);
		add(panel);
		addFormHandler(new RegistrationFormHandler());
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
				submit();
			}
		});
		submitPanel.add(submit);
		return submitPanel;
	}

	protected boolean validate()
	{
		List<String> errMsgs = new ArrayList<String>();

		IValidator<UsuarioDto> validator = GWT.create(UsuarioDto.class);
		for (InvalidConstraint<UsuarioDto> error : validator.validate(usuario))
			errMsgs.add(error.getMessage());

		String fileName = ((FileUpload) widgets.get(RegistrationFields.Logo)).getFilename().toUpperCase();
		if (!fileName.isEmpty() && !fileName.endsWith("PNG") && !fileName.endsWith("GIF") && !fileName.endsWith("JPG")
				&& !fileName.endsWith("JPEG"))
			errMsgs.add("El archivo debe tener extensión PNG, GIF, JPG o JPEG");

		if (errMsgs.size() > 0)
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < errMsgs.size(); i++)
			{
				if (i > 0)
					sb.append('\n');
				sb.append(errMsgs.get(i));
			}
			Window.alert(sb.toString());
			return false;
		}
		return true;
	}

	private native void reload() /*-{
	   $wnd.location.reload();
	  }-*/;

	private final class RegistrationFormHandler implements FormHandler
	{
		public void onSubmit(FormSubmitEvent event)
		{
			usuario = new UsuarioDto();
			usuario.setNombre(((TextBox) instance.widgets.get(RegistrationFields.Nombre)).getText());
			usuario.setApellido(((TextBox) instance.widgets.get(RegistrationFields.Apellido)).getText());
			usuario.setEmail(((TextBox) instance.widgets.get(RegistrationFields.Email)).getText());
			usuario.setUsuario(((TextBox) instance.widgets.get(RegistrationFields.Usuario)).getText());
			usuario.setClave(((TextBox) instance.widgets.get(RegistrationFields.Clave)).getText());
			ListBox lisPaises = (ListBox) instance.widgets.get(RegistrationFields.Pais);
			usuario.setPais(lisPaises.getValue(lisPaises.getSelectedIndex()));
			usuario.setCiudad(((SuggestBox) instance.widgets.get(RegistrationFields.Ciudad)).getText());
			usuario.setCodPostal(((TextBox) instance.widgets.get(RegistrationFields.CodPostal)).getText());
			usuario.setDescripPerfil(((TextBox) instance.widgets.get(RegistrationFields.DescripPerfil)).getText());
			if (!validate())
				event.setCancelled(true);
		}

		public void onSubmitComplete(FormSubmitCompleteEvent event)
		{
			String results = event.getResults();
			if (results.startsWith("OK:"))
			{
				usuario.setLogo(results.split(":", 2)[1]);

				ClientUtils.getSoftmartService().registrar(usuario, new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert("Error inesperado, no se pudo registrar el usuario");
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

	private enum RegistrationFields
	{
		Nombre, Apellido, Email, Usuario("Nombre de usuario"), Clave("Contrase&ntilde;a"), Pais("Pa&iacute;s"), Ciudad, CodPostal(
				"C&oacute;digo postal"), DescripPerfil("Descripci&oacute;n del perfil (opcional)"), Logo(
				"Logo o imagen (opcional - PNG, GIF, JPEG. Tama&ntilde;o m&aacute;ximo 200KB");

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
	}
}
