package edu.tdp2.client.widgets;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.utils.ClientUtils;

public class AdminLoginWidget extends LoginWidget
{
	private static AdminLoginWidget instance;
	private static String currentUser;

	public static String getCurrentUser()
	{
		return currentUser;
	}

	public static AdminLoginWidget getInstance()
	{
		if (instance == null)
			instance = new AdminLoginWidget();
		return instance;
	}

	public static void setCurrentUser(String currentUser)
	{
		AdminLoginWidget.currentUser = currentUser;
	}

	@Override
	protected Widget getRegisterLink()
	{
		return null;
	}

	@Override
	protected ClickHandler getSubmitHandler()
	{
		return new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert(constants.badLogin());
					}

					public void onSuccess(String result)
					{
						if (result.startsWith("@")) // Error devuelto por parametro
							Window.alert(result.substring(1));
						else
						{
							Date expire = new Date();
							expire.setTime(expire.getTime() + 1000 * 60 * 60 * 24); // Un dia
							Cookies.setCookie(softmartConstants.adminLoginCookieName(), result, expire);
							setCurrentUser(userNameTextBox.getText());
							if (loginListener != null)
								loginListener.onLogin();
						}
					}
				};
				ClientUtils.getSoftmartService().adminLogin(userNameTextBox.getText(),
						getHash(passwordTextBox.getText()), callback);
			}
		};
	}

}
