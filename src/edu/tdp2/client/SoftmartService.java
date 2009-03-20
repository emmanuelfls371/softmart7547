package edu.tdp2.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface SoftmartService extends RemoteService
{
	public String login(String userName, String passwordHash);
}
