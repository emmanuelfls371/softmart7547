package edu.tdp2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.tdp2.client.SoftmartService;
import edu.tdp2.server.model.Usuario;

public class SoftmartServiceImpl extends RemoteServiceServlet implements SoftmartService
{
	private static final long serialVersionUID = 7772479472218006401L;

	@Override
	public String login(String userName, String passwordHash)
	{
		new Usuario().getNombre();
		// TODO Auto-generated method stub
		return null;
	}
}
