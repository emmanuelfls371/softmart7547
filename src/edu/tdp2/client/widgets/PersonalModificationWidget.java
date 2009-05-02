package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.dto.MyAccountDto;
import edu.tdp2.client.dto.UsuarioDto;
import edu.tdp2.client.utils.ClientUtils;

public class PersonalModificationWidget extends FormWidget
{
	private MyAccountDto accountDto;

	private static String INVALIDO = "0000";

	public PersonalModificationWidget(MyAccountDto dto)
	{
		tituloWidget = "<b>Modificación de datos personales</b>";
		anchoWidget = "200px";
		anchoTabla = "200px";
		url = "modification";
		accountDto = dto;
		init();
	}

	@Override
	protected FormFields[] values()
	{
		return ModificationFields.values();
	}

	@Override
	protected void populateWidgets()
	{
		TextBox t = new TextBox();
		t.setMaxLength(50);
		t.setName(ModificationFields.Nombre.toString());
		t.setText(((MyAccountDto) accountDto).getNombre());
		widgets.put(ModificationFields.Nombre, t);

		t = new TextBox();
		t.setMaxLength(50);
		t.setName(ModificationFields.Apellido.toString());
		t.setText(((MyAccountDto) accountDto).getApellido());
		widgets.put(ModificationFields.Apellido, t);

		t = new TextBox();
		t.setMaxLength(255);
		t.setName(ModificationFields.Email.toString());
		t.setText(((MyAccountDto) accountDto).getEmail());
		widgets.put(ModificationFields.Email, t);

		/*
		 * t = new TextBox(); t.setMaxLength(50); t.setName(ModificationFields.Usuario.toString());
		 * t.setText(((MyAccountDto) accountDto).getUsuario()); widgets.put(ModificationFields.Usuario, t);
		 */

		final ListBox lisPaises = new ListBox();
		lisPaises.setName(ModificationFields.Pais.toString());
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
				int pos = 1;
				int selectedItem = 1;
				for (String pais : paises)
				{
					if (pais.equals(((MyAccountDto) accountDto).getPais()))
						selectedItem = pos;
					lisPaises.addItem(pais, pais);
					pos++;
				}
				lisPaises.setItemSelected(selectedItem, true);
			}
		};
		ClientUtils.getSoftmartService().getPaises(callback);
		widgets.put(ModificationFields.Pais, lisPaises);

		t = new TextBox();
		t.setMaxLength(50);
		t.setName(ModificationFields.Ciudad.toString());
		t.setText(((MyAccountDto) accountDto).getCiudad());
		widgets.put(ModificationFields.Ciudad, t);
	}

	@Override
	protected void buildWidget()
	{
		super.buildWidget();
		addSubmitCompleteHandler(new ModificationSubmitCompleteHandler());
		addSubmitHandler(new ModificationSubmitHandler());
	}

	@Override
	protected void validate(List<String> errMsgs)
	{

	}

	private final class ModificationSubmitHandler implements SubmitHandler
	{
		public void onSubmit(SubmitEvent event)
		{
			dto = new UsuarioDto();
			((UsuarioDto) dto).setNombre(((TextBox) widgets.get(ModificationFields.Nombre)).getText());
			((UsuarioDto) dto).setApellido(((TextBox) widgets.get(ModificationFields.Apellido)).getText());
			((UsuarioDto) dto).setEmail(((TextBox) widgets.get(ModificationFields.Email)).getText());
			// ((UsuarioDto) dto).setUsuario(((TextBox) widgets.get(ModificationFields.Usuario)).getText());
			ListBox lisPaises = (ListBox) widgets.get(ModificationFields.Pais);
			((UsuarioDto) dto).setPais(lisPaises.getValue(lisPaises.getSelectedIndex()));
			((UsuarioDto) dto).setCiudad(((SuggestBox) widgets.get(ModificationFields.Ciudad)).getText());

			((UsuarioDto) dto).setCodPostal(INVALIDO);
			((UsuarioDto) dto).setClave(INVALIDO);
			((UsuarioDto) dto).setUsuario(INVALIDO);

			if (!validate())
				event.cancel();

		}
	}

	private final class ModificationSubmitCompleteHandler implements SubmitCompleteHandler
	{
		public void onSubmitComplete(SubmitCompleteEvent event)
		{

			String results = event.getResults();
			if (results.startsWith("OK:"))
			{
				((UsuarioDto) dto).setLogo(results.split(":", 2)[1]);

				ClientUtils.getSoftmartService().update((UsuarioDto) dto, ((MyAccountDto) accountDto).getUsuario(),
						new AsyncCallback<String>()
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

	private enum ModificationFields implements FormFields
	{
		Nombre, Apellido, Email, Pais("Pa&iacute;s"), Ciudad;

		private ModificationFields(String description)
		{
			this.description = description;
		}

		public String description;

		private ModificationFields()
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
		return GWT.create(UsuarioDto.class);
	}
}
