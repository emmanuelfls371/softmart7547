package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.tdp2.client.dto.ContratoDto;
import edu.tdp2.client.utils.ClientUtils;

public class CalificacionHechaList extends CalificacionList
{

	protected CalificacionHechaList(String user)
	{
		super(user);
	}

	@Override
	protected void doCall(AsyncCallback<List<ContratoDto>> callback)
	{
		ClientUtils.getSoftmartService().getCalificacionesHechas(user, callback);
	}
}
