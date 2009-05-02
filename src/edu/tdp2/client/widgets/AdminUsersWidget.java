package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.model.Usuario;
import edu.tdp2.client.utils.ClientUtils;

public class AdminUsersWidget extends AdminWidget
{
	private static AdminUsersWidget instance;

	public static AdminUsersWidget getInstance()
	{
		if (instance == null)
			instance = new AdminUsersWidget();
		return instance;
	}

	@Override
	public void load()
	{
		container.clear();
		History.newItem("AdminUsers");

		VerticalPanel vPanel = new VerticalPanel();
		final FlexTable table = new FlexTable();
		table.setCellPadding(10);
		vPanel.add(ClientUtils.getBackAnchor());
		vPanel.add(statusMessage);
		vPanel.add(table);
		container.add(vPanel);

		AsyncCallback<List<Usuario>> callback = new AsyncCallback<List<Usuario>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert("No se pudo recuperar los proyectos");
			}

			public void onSuccess(List<Usuario> usuarios)
			{
				table.setWidget(0, 0, new HTML("Usuario"));
				table.setWidget(0, 1, new HTML("Nombre"));
				table.setWidget(0, 2, new HTML("Email"));
				table.setWidget(0, 3, new HTML("Bloqueado"));
				int row = 1;
				for (Usuario u : usuarios)
				{
					table.setWidget(row, 0, new HTML(u.getLogin()));
					table.setWidget(row, 1, new HTML(u.getNombre() + " " + u.getApellido()));
					table.setWidget(row, 2, new HTML(u.getEmail()));
					table.setWidget(row, 3, getCheckBoxBloqueado(u));
					row++;
				}
			}
		};
		ClientUtils.getSoftmartService().getUsers(callback);
	}

	protected CheckBox getCheckBoxBloqueado(final Usuario u)
	{
		CheckBox c = new CheckBox();
		c.setValue(u.isBloqueado());
		c.addValueChangeHandler(new ValueChangeHandler<Boolean>()
		{
			public void onValueChange(ValueChangeEvent<Boolean> event)
			{
				final boolean value = event.getValue();
				final String prefix = (value ? "" : "des");
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{

					public void onFailure(Throwable caught)
					{
						Window.alert("No se pudo " + prefix + "bloquear al usuario");
					}

					public void onSuccess(String result)
					{
						if (result == null)
							statusMessage.setHTML("El usuario fue " + prefix + "bloqueado");
						else
						{
							Window.alert(result);
							statusMessage.setHTML("Error al intentar " + prefix + "bloquear al usuario");
						}
					}
				};
				ClientUtils.getSoftmartService().setUsuarioBloqueado(u.getId(), event.getValue(), callback);
			}
		});
		return c;
	}
}
