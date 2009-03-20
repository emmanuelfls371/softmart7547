package edu.tdp2.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import edu.tdp2.client.SoftmartService;
import edu.tdp2.client.SoftmartServiceAsync;

public class ClientUtils
{
	public static SoftmartServiceAsync getSoftmartService()
	{
		final SoftmartServiceAsync softmartService = (SoftmartServiceAsync) GWT.create(SoftmartService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) softmartService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "softmart");
		return softmartService;
	}
}
