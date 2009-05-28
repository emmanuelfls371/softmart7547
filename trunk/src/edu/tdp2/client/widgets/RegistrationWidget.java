package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.client.utils.ClientUtils;


/* TODO Pasar los mensajes de RegistrationWidget a messages*/
public class RegistrationWidget extends FormWidget
{
	private static RegistrationWidget instance;

	private boolean errorC;
	private boolean errorE;
	private boolean errorClave;
	private boolean errorEmail;
	private boolean errorClaveRep;
	private boolean errorEmailRep;
	
	public static RegistrationWidget getInstance()
	{
		if (instance == null)
			instance = new RegistrationWidget();
		return instance;
	}

	private RegistrationWidget()
	{
		tituloWidget = "<big><big><b>Reg&iacute;strese</b></big></big>";
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
		t.setMaxLength(255);
		t.setName(RegistrationFields.ConfirmeEmail.toString());
		widgets.put(RegistrationFields.ConfirmeEmail, t);
		

		t = new TextBox();
		t.setMaxLength(50);
		t.setName(RegistrationFields.Usuario.toString());
		widgets.put(RegistrationFields.Usuario, t);

		t = new PasswordTextBox();
		t.setMaxLength(50);
		t.setName(RegistrationFields.Clave.toString());
		widgets.put(RegistrationFields.Clave, t);
		
		t = new PasswordTextBox();
		t.setMaxLength(50);
		t.setName(RegistrationFields.ConfirmeClave.toString());
		widgets.put(RegistrationFields.ConfirmeClave, t);

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
		widgets.put(RegistrationFields.Pais, lisPaises);

		t = new TextBox();
		t.setMaxLength(50);
		t.setName(RegistrationFields.Ciudad.toString());
		widgets.put(RegistrationFields.Ciudad, t);

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
		addSubmitCompleteHandler(new RegistrationSubmitCompleteHandler());
		addSubmitHandler(new RegistrationSubmitHandler());
	}

	@Override
	protected void validate(List<String> errMsgs)
	{
		String fileName = ((FileUpload) widgets.get(RegistrationFields.Logo)).getFilename().toUpperCase();
		if (!fileName.isEmpty() && !fileName.endsWith("PNG") && !fileName.endsWith("GIF") && !fileName.endsWith("JPG")
				&& !fileName.endsWith("JPEG"))
			errMsgs.add("El archivo debe tener extensi�n PNG, GIF, JPG o JPEG");
		
		if (errorClave)
			errMsgs.add("Debe ingresar la clave original");
		
		if(errorClaveRep)
			errMsgs.add("Debe ingresar la clave repetida");
		
		if(errorC)
			errMsgs.add("Las claves no son iguales");
		
		if (errorEmail)
			errMsgs.add("Debe ingresar el email original");
		if (errorEmailRep)
			errMsgs.add("Debe ingresar el email repetido");
		
		if(errorE)
			errMsgs.add("Los e-mail no son iguales");
	}

	private final class RegistrationSubmitHandler implements SubmitHandler
	{
		public void onSubmit(SubmitEvent event)
		{
			errorClave = false;
			errorEmail = false;
			errorClaveRep = false;
			errorEmailRep = false;
			errorC = false;
			errorE = false;
			dto = new UsuarioDto();
			((UsuarioDto) dto).setNombre(((TextBox) instance.widgets.get(RegistrationFields.Nombre)).getText());
			((UsuarioDto) dto).setApellido(((TextBox) instance.widgets.get(RegistrationFields.Apellido)).getText());
			String email =(((TextBox) instance.widgets.get(RegistrationFields.Email)).getText());
			String emailRepetido = ((TextBox) widgets.get(RegistrationFields.ConfirmeEmail)).getText();

			((UsuarioDto) dto).setUsuario(((TextBox) instance.widgets.get(RegistrationFields.Usuario)).getText());
			String clave = (((TextBox) instance.widgets.get(RegistrationFields.Clave)).getText());
			String claveRepetida = ((PasswordTextBox) widgets.get(RegistrationFields.ConfirmeClave)).getText();

			ListBox lisPaises = (ListBox) instance.widgets.get(RegistrationFields.Pais);
			((UsuarioDto) dto).setPais(lisPaises.getValue(lisPaises.getSelectedIndex()));
			((UsuarioDto) dto).setCiudad(((TextBox) instance.widgets.get(RegistrationFields.Ciudad)).getText());
			((UsuarioDto) dto).setCodPostal(((TextBox) instance.widgets.get(RegistrationFields.CodPostal)).getText());
			((UsuarioDto) dto).setDescripPerfil(((TextBox) instance.widgets.get(RegistrationFields.DescripPerfil))
					.getText());
			
			if (!clave.isEmpty() && !claveRepetida.isEmpty() && clave.equals(claveRepetida))
				((UsuarioDto) dto).setClave(claveRepetida);
			else if (!clave.isEmpty() || !claveRepetida.isEmpty()){
				if(claveRepetida.isEmpty()){
					errorClaveRep = true;
					((UsuarioDto) dto).setClave(clave);
				}else if (clave.isEmpty()){
					errorClave = true;
					((UsuarioDto) dto).setClave(claveRepetida);
				}else if(!clave.isEmpty() && !claveRepetida.isEmpty() && !clave.equals(claveRepetida)){
					errorC=true;
					((UsuarioDto) dto).setClave(claveRepetida);
				}
			}
			
			if (!email.isEmpty() && !emailRepetido.isEmpty() && email.equals(emailRepetido))
				((UsuarioDto) dto).setEmail(emailRepetido);
			else if (!email.isEmpty() || !emailRepetido.isEmpty()){
				if(emailRepetido.isEmpty()){
					errorEmailRep = true;
					((UsuarioDto) dto).setEmail(email);
				}else if (email.isEmpty()){
					errorEmail = true;
					((UsuarioDto) dto).setEmail(emailRepetido);
				}else if (!email.isEmpty() && !emailRepetido.isEmpty() && !email.equals(emailRepetido)){
					errorE=true;
					((UsuarioDto) dto).setEmail(emailRepetido);
				}
			}
			
			if (!validate())
				event.cancel();
		}
	}

	private final class RegistrationSubmitCompleteHandler implements SubmitCompleteHandler
	{
		public void onSubmitComplete(SubmitCompleteEvent event)
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
		Nombre, Apellido, Email,ConfirmeEmail("Confirmaci&oacute;n de E-mail"), Usuario("Nombre de usuario"), Clave("Contrase&ntilde;a"),ConfirmeClave("Confirmaci&oacute;n Contrase&ntilde;a"), Pais("Pa&iacute;s"), Ciudad, CodPostal(
				"C&oacute;digo postal"), DescripPerfil("Descripci&oacute;n del perfil (opcional)"), Logo(
				"Logo o imagen (opcional - PNG, GIF, JPEG. Tama&ntilde;o m&aacute;ximo 200KB");

		private RegistrationFields(String description)
		{
			this.description = description;
		}

		public String description;

		private RegistrationFields()
		{
			description = name();
		}

		public String getDescription()
		{
			return description;
		}

	}

	@Override
	protected IValidator<Dto> getValidator()
	{
		return GWT.create(UsuarioDto.class);
	}
}
