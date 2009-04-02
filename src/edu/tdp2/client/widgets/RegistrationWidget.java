package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.client.utils.ClientUtils;

/* TODO Pasar los mensajes de RegistrationWidget a messages*/
public class RegistrationWidget extends FormWidget
{
	private static RegistrationWidget instance;
	private SuggestCallback suggestCallback;

	public static RegistrationWidget getInstance()
	{
		if (instance == null)
			instance = new RegistrationWidget();
		return instance;
	}

	private RegistrationWidget()
	{
		nombreWidget = "<b>Nuevo usuario</b>";
		anchoWidget = "200px";
		anchoTabla = "100px";
		url = "registration";
		dto = new UsuarioDto();
		init();
	}

	@Override
	protected FormFields[] values()
	{
		return RegistrationFields.values();
	}

	@Override
	protected void populateWidgets()
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

	@Override
	protected void buildWidget()
	{
		super.buildWidget();
		addFormHandler(new RegistrationFormHandler());
	}

	@Override
	protected boolean validate()
	{
		createErrorMessage();

		String fileName = ((FileUpload) widgets.get(RegistrationFields.Logo)).getFilename().toUpperCase();
		if (!fileName.isEmpty() && !fileName.endsWith("PNG") && !fileName.endsWith("GIF") && !fileName.endsWith("JPG")
				&& !fileName.endsWith("JPEG"))
			errMsgs.add("El archivo debe tener extensión PNG, GIF, JPG o JPEG");

		return super.buildErrorMessage();
	}

	private final class RegistrationFormHandler implements FormHandler
	{
		public void onSubmit(FormSubmitEvent event)
		{
			dto = new UsuarioDto();
			((UsuarioDto) dto).setNombre(((TextBox) instance.widgets.get(RegistrationFields.Nombre)).getText());
			((UsuarioDto) dto).setApellido(((TextBox) instance.widgets.get(RegistrationFields.Apellido)).getText());
			((UsuarioDto) dto).setEmail(((TextBox) instance.widgets.get(RegistrationFields.Email)).getText());
			((UsuarioDto) dto).setUsuario(((TextBox) instance.widgets.get(RegistrationFields.Usuario)).getText());
			((UsuarioDto) dto).setClave(((TextBox) instance.widgets.get(RegistrationFields.Clave)).getText());
			ListBox lisPaises = (ListBox) instance.widgets.get(RegistrationFields.Pais);
			((UsuarioDto) dto).setPais(lisPaises.getValue(lisPaises.getSelectedIndex()));
			((UsuarioDto) dto).setCiudad(((SuggestBox) instance.widgets.get(RegistrationFields.Ciudad)).getText());
			((UsuarioDto) dto).setCodPostal(((TextBox) instance.widgets.get(RegistrationFields.CodPostal)).getText());
			((UsuarioDto) dto).setDescripPerfil(((TextBox) instance.widgets.get(RegistrationFields.DescripPerfil))
					.getText());
			if (!validate())
				event.setCancelled(true);
		}

		public void onSubmitComplete(FormSubmitCompleteEvent event)
		{
			String results = event.getResults();
			if (results.startsWith("OK:"))
			{
				((UsuarioDto) dto).setLogo(results.split(":", 2)[1]);

				ClientUtils.getSoftmartService().registrar((UsuarioDto) dto, new AsyncCallback<String>()
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

	private enum RegistrationFields implements FormFields
	{
		Nombre, Apellido, Email, Usuario("Nombre de usuario"), Clave("Contrase&ntilde;a"), Pais("Pa&iacute;s"), Ciudad, CodPostal(
				"C&oacute;digo postal"), DescripPerfil("Descripci&oacute;n del perfil (opcional)"), Logo(
				"Logo o imagen (opcional - PNG, GIF, JPEG. Tama&ntilde;o m&aacute;ximo 200KB");

		private RegistrationFields(String description)
		{
			this.description = description;
		}

		public String description;

		private RegistrationFields()
		{
			this.description = name();
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

	@Override
	protected IValidator<Dto> getValidator()
	{
		return GWT.create(UsuarioDto.class);
	}
}
