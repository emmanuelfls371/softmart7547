package edu.tdp2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SoftmartServiceAsync
{
	public void login(String userName, String passwordHash, AsyncCallback<String> callback);
}
