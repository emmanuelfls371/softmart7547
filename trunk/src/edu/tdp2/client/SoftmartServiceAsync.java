package edu.tdp2.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SoftmartServiceAsync
{
	public void login(String userName, String passwordHash, AsyncCallback<String> callback);

	public void getPaises(AsyncCallback<List<String>> callback);

	public void getCiudades(String pais, AsyncCallback<List<String>> callback);
}
