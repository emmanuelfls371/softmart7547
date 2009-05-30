package edu.tdp2.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;

import edu.tdp2.client.dto.ContratoDto;

public abstract class CalificacionList extends ListBox
{

	protected String user;

	private Map<String, ContratoDto> contratos = new HashMap<String, ContratoDto>();
	private CalificacionConstants constants;

	protected CalificacionList(String user)
	{
		super();
		constants = GWT.create(CalificacionConstants.class);
		this.user = user;
		setWidth("200px");
		load();
	}

	public ContratoDto getSelectedItem()
	{
		int index = getSelectedIndex();
		if (index == -1)
			return null;
		else
			return contratos.get(getValue(index));
	}

	protected abstract void doCall(AsyncCallback<List<ContratoDto>> callback);

	protected void load()
	{
		AsyncCallback<List<ContratoDto>> callback = new AsyncCallback<List<ContratoDto>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(constants.failDoCall());
			}

			public void onSuccess(List<ContratoDto> contratos)
			{
				clear();
				for (ContratoDto contrato : contratos)
					addItem(contrato);
			}
		};
		doCall(callback);
	}

	private void addItem(ContratoDto contrato)
	{
		addItem(contrato.getProyecto().getNombre(), Long.toString(contrato.getIdContrato()));
		contratos.put(Long.toString(contrato.getIdContrato()), contrato);
	}

}
