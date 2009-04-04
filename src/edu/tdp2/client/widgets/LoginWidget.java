package edu.tdp2.client.widgets;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.SoftmartConstants;
import edu.tdp2.client.SoftmartMessages;
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
	private SoftmartMessages messages;

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
		messages = (SoftmartMessages) GWT.create(SoftmartMessages.class);
		userNameTextBox = new TextBox();
		userNameTextBox.setWidth("200px");
		passwordTextBox = new PasswordTextBox();
		passwordTextBox.setWidth("200px");
		buildWidget();
	}

	private void buildWidget()
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
		table.setWidget(0, 0, new HTML("<b>Iniciar sesi&oacute;n</b>"));
		table.setWidget(0, 1, getRegisterLink());
		table.setWidget(1, 0, new HTML("Usuario: "));
		table.setWidget(1, 1, userNameTextBox);
		table.setWidget(2, 0, new HTML("Contrase&ntilde;a: "));
		table.setWidget(2, 1, passwordTextBox);
		table.setWidget(3, 1, getSubmitPanel());
		table.setWidth("100px");
		return table;
	}

	private Widget getRegisterLink()
	{
		Hyperlink link = new Hyperlink("Registrarse", true, "");
		link.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				loginListener.onShowRegistration();
			}
		});
		return link;
	}

	private HorizontalPanel getSubmitPanel()
	{
		HorizontalPanel submitPanel = new HorizontalPanel();
		submitPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		submitPanel.setWidth("100%");
		Button submit = new Button("Entrar", new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert(messages.badLogin());
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
		submitPanel.add(submit);
		return submitPanel;
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
