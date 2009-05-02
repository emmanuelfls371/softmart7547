package edu.tdp2.client.widgets;

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
		// TODO Auto-generated method stub

	}
}
