package edu.tdp2.client.widgets;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.SoftmartConstants;
import edu.tdp2.client.utils.ClientUtils;
import edu.tdp2.client.utils.MD5;

public class LoginWidget extends SimplePanel
{
	private static LoginWidget instance;
	private static String currentUser;

	public static String getCurrentUser()
	{
		return currentUser;
	}

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

	private final TextBox userNameTextBox;
	private final PasswordTextBox passwordTextBox;

	private LoginListener loginListener;

	private SoftmartConstants softmartConstants;

	private LoginConstants constants;

	private LoginWidget()
	{
		softmartConstants = GWT.create(SoftmartConstants.class);
		constants = GWT.create(LoginConstants.class);
		userNameTextBox = new TextBox();
		userNameTextBox.setWidth("200px");
		passwordTextBox = new PasswordTextBox();
		passwordTextBox.setWidth("200px");
		buildWidget();
	}

	public TextBox getPasswordTextBox()
	{
		return passwordTextBox;
	}

	public TextBox getUserNameTextBox()
	{
		return userNameTextBox;
	}

	public void setLoginListener(LoginListener loginListener)
	{
		this.loginListener = loginListener;
	}

	private void buildWidget()
	{
		VerticalPanel panel = new VerticalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		SimplePanel simplePanel = new SimplePanel();
		simplePanel.setHeight("50px");
		panel.add(simplePanel);
		FlexTable table = getTable();
		panel.add(table);
		add(panel);

	}

	private Widget getRegisterLink()
	{
		Anchor link = new Anchor(constants.registrese(), true);
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
		submitPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		submitPanel.setWidth("100%");
		Button submit = new Button("Entrar", new ClickHandler()
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
							Cookies.setCookie(softmartConstants.loginCookieName(), result, expire);
							setCurrentUser(userNameTextBox.getText());
							if (loginListener != null)
								loginListener.onLogin();
						}
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

	private FlexTable getTable()
	{
		FlexTable table = new FlexTable();
		HTML prompt = new HTML(constants.inicieSesion());
		prompt.setWidth("150px");
		table.setWidget(0, 0, prompt);
		table.setWidget(1, 1, getRegisterLink());
		table.setWidget(2, 0, new HTML(constants.usuario()));
		table.setWidget(2, 1, userNameTextBox);
		table.setWidget(3, 0, new HTML(constants.contrasena()));
		table.setWidget(3, 1, passwordTextBox);
		table.setWidget(4, 1, getSubmitPanel());
		return table;
	}
}
