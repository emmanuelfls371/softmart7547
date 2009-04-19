package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.tdp2.client.dto.ContratoDto;
import edu.tdp2.client.utils.ClientUtils;

public class CalificacionRecibidaList extends CalificacionList
{

	protected CalificacionRecibidaList(String user)
	{
		super(user);
	}

	protected void doCall(AsyncCallback<List<ContratoDto>> callback)
	{
		ClientUtils.getSoftmartService().getCalificacionesRecibidas(user, callback);
	}

}
