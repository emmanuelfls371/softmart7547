package edu.tdp2.client.widgets;

import java.util.List;

import com.google.gwt.core.client.GWT;
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

	private AdminUsersConstants constants;

	@Override
	public void load()
	{
		constants = GWT.create(AdminUsersConstants.class);
		History.newItem("AdminUsers");

		VerticalPanel vPanel = new VerticalPanel();
		final FlexTable table = new FlexTable();
		table.setCellPadding(10);
		statusMessage = new HTML();
		vPanel.add(statusMessage);
		vPanel.add(table);
		container.add(vPanel, constants.usuarios());

		AsyncCallback<List<Usuario>> callback = new AsyncCallback<List<Usuario>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failGetProjects());
			}

			public void onSuccess(List<Usuario> usuarios)
			{
				table.setWidget(0, 0, new HTML(constants.usuario()));
				table.setWidget(0, 1, new HTML(constants.nombre()));
				table.setWidget(0, 2, new HTML(constants.email()));
				table.setWidget(0, 3, new HTML(constants.bloqueado()));
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
				final String prefix = value ? "" : constants.prefijoDes();
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{

					public void onFailure(Throwable caught)
					{
						Window.alert(constants.noSePudo() + prefix + constants.bloquearUsuario());
					}

					public void onSuccess(String result)
					{
						if (result == null)
							statusMessage.setHTML(constants.usuarioFue() + prefix + constants.bloqueadoMinusc());
						else
						{
							Window.alert(result);
							statusMessage.setHTML(constants.errorIntentar() + prefix + constants.bloquearUsuario());
						}
					}
				};
				ClientUtils.getSoftmartService().setUsuarioBloqueado(u.getId(), event.getValue(), callback);
			}
		});
		return c;
	}
}
