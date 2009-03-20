package edu.tdp2.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.utils.ClientUtils;
import edu.tdp2.client.utils.MD5;

public class LoginWidget extends SimplePanel
{
	private static LoginWidget instance;
	private static String currentUser;
	private final TextBox userNameTextBox;
	private final PasswordTextBox passwordTextBox;
	private LoginListener loginListener;
	private SoftmartConstants constants;

	public static LoginWidget getInstance()
	{
		if (instance == null)
			instance = new LoginWidget();
		return instance;
	}

	public static void setCurrentUser(String currentUser)
	{
		LoginWidget.currentUser = currentUser;
	}

	public static String getCurrentUser()
	{
		return currentUser;
	}

	private LoginWidget()
	{
		constants = (SoftmartConstants) GWT.create(SoftmartConstants.class);
		userNameTextBox = new TextBox();
		userNameTextBox.setWidth("200px");
		passwordTextBox = new PasswordTextBox();
		passwordTextBox.setWidth("200px");
		armarWidget();
	}

	private void armarWidget()
	{
		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		SimplePanel simplePanel = new SimplePanel();
		simplePanel.setHeight("50px");
		panel.add(simplePanel);
		FlexTable table = getTable();
		panel.add(table);
		add(panel);
	}

	private FlexTable getTable()
	{
		FlexTable table = new FlexTable();
		HorizontalPanel submitPanel = new HorizontalPanel();
		submitPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		submitPanel.setWidth("100%");
		submitPanel.add(getSubmitButton());
		table.setWidget(0, 0, new HTML("<b>Login</b>"));
		table.setWidget(1, 0, new HTML("Usuario: "));
		table.setWidget(1, 1, userNameTextBox);
		table.setWidget(2, 0, new HTML("Contrase&ntilde;a: "));
		table.setWidget(2, 1, passwordTextBox);
		table.setWidget(3, 1, submitPanel);
		table.setWidth("100px");
		return table;
	}

	private Button getSubmitButton()
	{
		Button submit = new Button("Entrar", new ClickListener()
		{
			public void onClick(Widget sender)
			{
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert("Login incorrecto");
					}

					public void onSuccess(String result)
					{
						Date expire = new Date();
						expire.setTime(expire.getTime() + (1000 * 60 * 60 * 24)); // Un dia
						Cookies.setCookie(constants.loginCookieName(), result, expire);
						setCurrentUser(userNameTextBox.getText());
						if (loginListener != null)
							loginListener.onLogin();
					}
				};
				ClientUtils.getSoftmartService().login(userNameTextBox.getText(), getHash(passwordTextBox.getText()),
						callback);
			}

			private String getHash(String text)
			{
				return MD5.md5(text);
			}
		});
		return submit;
	}

	public TextBox getUserNameTextBox()
	{
		return userNameTextBox;
	}

	public TextBox getPasswordTextBox()
	{
		return passwordTextBox;
	}

	public void setLoginListener(LoginListener loginListener)
	{
		this.loginListener = loginListener;
	}
}
