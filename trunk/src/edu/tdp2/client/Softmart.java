package edu.tdp2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.widgets.DestacadosWidget;
import edu.tdp2.client.widgets.LoginListener;
import edu.tdp2.client.widgets.LoginWidget;
import edu.tdp2.client.widgets.MyAccountWidget;
import edu.tdp2.client.widgets.NewProjectWidget;
import edu.tdp2.client.widgets.RegistrationWidget;
import edu.tdp2.client.widgets.SearchWidget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Softmart implements EntryPoint, LoginListener
{
	public interface Images extends ImageBundle
	{
		AbstractImagePrototype footer();

		AbstractImagePrototype header();

		AbstractImagePrototype header2();
	}

	private class SoftmartHistoryHandler implements ValueChangeHandler<String>
	{
		@SuppressWarnings("incomplete-switch")
		public void onValueChange(ValueChangeEvent<String> event)
		{
			HistoryToken token;
			try
			{
				token = HistoryToken.valueOf(event.getValue());
			}
			catch (IllegalArgumentException e)
			{
				return;
			}

			switch (token)
			{
			case onShowNewProject:
				onShowNewProject();
				break;
			case onShowRegistration:
				onShowRegistration();
				break;
			case Welcome:
				showWelcome();
				break;
			case onShowMyAccount:
				onShowMyAccount();
				break;
			case onShowSearch:
				onShowSearch();
				break;
			}
		}
	}

	private SoftmartConstants constants;
	private I18nConstants i18nConstants;
	private VerticalPanel centerPanel;
	private AbsolutePanel northPanel;
	private Images images;

	public void onLogin()
	{
		centerPanel.clear();
		northPanel.clear();
		Image header2 = images.header2().createImage();
		northPanel.add(header2);
		String loginCookie = Cookies.getCookie(constants.loginCookieName());
		String currentUser = loginCookie.split(";")[0];
		LoginWidget.setCurrentUser(currentUser);
		northPanel.add(new HTML(constants.welcomeUser() + (currentUser == null ? "" : currentUser)
				+ constants.toSoftmart()));

		FocusPanel logout = new FocusPanel();
		logout.setSize("30px", "10px");
		logout.addStyleName("cursorPointer");
		logout.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onLogout();
			}
		});
		northPanel.add(logout, 342, 5);

		FocusPanel login = new FocusPanel();
		login.setSize("37px", "10px");
		login.addStyleName("cursorPointer");
		login.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onLogin();
			}
		});
		northPanel.add(login, 51, 5);

		FocusPanel showMyAccount = new FocusPanel();
		showMyAccount.setSize("57px", "10px");
		showMyAccount.addStyleName("cursorPointer");
		showMyAccount.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onShowMyAccount();
			}
		});
		northPanel.add(showMyAccount, 93, 5);

		FocusPanel showNewProject = new FocusPanel();
		showNewProject.setSize("90px", "10px");
		showNewProject.addStyleName("cursorPointer");
		showNewProject.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onShowNewProject();
			}
		});
		northPanel.add(showNewProject, 247, 5);

		FocusPanel showSearch = new FocusPanel();
		showSearch.setSize("90px", "10px");
		showSearch.addStyleName("cursorPointer");
		showSearch.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				onShowSearch();
			}
		});
		northPanel.add(showSearch, 150, 5);

		onShowDestacados();
	}

	public void onLogout()
	{
		Cookies.setCookie(constants.loginCookieName(), "");
		LoginWidget.setCurrentUser(null);
		northPanel.clear();
		showWelcome();
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		if (RootPanel.get("SoftmartIdentifierDiv") == null)
			return;

		constants = GWT.create(SoftmartConstants.class);
		i18nConstants = GWT.create(I18nConstants.class);
		images = (Images) GWT.create(Images.class);
		History.addValueChangeHandler(new SoftmartHistoryHandler());

		HorizontalPanel hPanel = new HorizontalPanel();
		DockPanel dPanel = new DockPanel();

		dPanel.add(getFooterPanel(), DockPanel.SOUTH);
		dPanel.add(getCenterPanel(), DockPanel.CENTER);
		dPanel.add(getHeaderPanel(), DockPanel.NORTH);
		dPanel.add(getNorthPanel(), DockPanel.NORTH);
		showWelcome();
		History.newItem(HistoryToken.Welcome.toString());

		hPanel.add(dPanel);
		hPanel.setWidth("100%");
		hPanel.setCellHorizontalAlignment(dPanel, HasHorizontalAlignment.ALIGN_CENTER);
		RootPanel.get().add(hPanel);
	}

	public void onShowNewProject()
	{
		putAlone(NewProjectWidget.getInstance(), HistoryToken.onShowNewProject.toString());
	}

	public void onShowRegistration()
	{
		putAlone(RegistrationWidget.getInstance(), HistoryToken.onShowRegistration.toString());
	}

	private Panel getCenterPanel()
	{
		centerPanel = new VerticalPanel();
		centerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		centerPanel.setSize("100%", "450px");
		return centerPanel;
	}

	private Widget getFooterPanel()
	{
		AbsolutePanel panel = new AbsolutePanel();
		SimplePanel bannerPanel = new SimplePanel();
		Image header = images.footer().createImage();
		bannerPanel.add(header);
		panel.add(bannerPanel);

		final ListBox lisIdiomas = new ListBox();
		for (int i = 0; i < i18nConstants.idiomas().length; i++)
		{
			lisIdiomas.addItem(i18nConstants.idiomas()[i], i18nConstants.locales()[i]);
			if (constants.idioma().equals(i18nConstants.idiomas()[i]))
				lisIdiomas.setSelectedIndex(i);
		}
		lisIdiomas.addChangeHandler(new ChangeHandler()
		{
			public void onChange(ChangeEvent event)
			{
				Window.open("Softmart.html" + lisIdiomas.getValue(lisIdiomas.getSelectedIndex()), "_self", "");
			}
		});
		panel.add(lisIdiomas, 0, 0);

		return panel;
	}

	private Panel getHeaderPanel()
	{
		SimplePanel panel = new SimplePanel();
		Image header = images.header().createImage();
		panel.add(header);
		return panel;
	}

	private Panel getNorthPanel()
	{
		northPanel = new AbsolutePanel();
		return northPanel;
	}

	private void onShowDestacados()
	{
		centerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		centerPanel.add(new DestacadosWidget());
	}

	private void onShowMyAccount()
	{
		putAlone(new MyAccountWidget(LoginWidget.getCurrentUser()), HistoryToken.onShowMyAccount.toString());
	}

	private void onShowSearch()
	{
		putAlone(new SearchWidget(), HistoryToken.onShowSearch.toString());
	}

	private void putAlone(Widget widget, String historyToken)
	{
		History.newItem(historyToken);
		centerPanel.clear();
		centerPanel.add(widget);
	}

	private void showWelcome()
	{
		centerPanel.clear();

		String loginCookie = Cookies.getCookie(constants.loginCookieName());
		if (loginCookie != null && !loginCookie.equals(""))
			onLogin();
		else
		{
			centerPanel.clear();
			LoginWidget loginWidget = LoginWidget.getInstance();
			centerPanel.add(loginWidget);
			HorizontalPanel hPanel = new HorizontalPanel();
			hPanel.setWidth("739px");
			hPanel.add(new HTML(((WelcomeConstants) GWT.create(WelcomeConstants.class)).text()));
			hPanel.setSpacing(20);
			centerPanel.add(hPanel);
			loginWidget.getUserNameTextBox().setText("");
			loginWidget.getPasswordTextBox().setText("");
			loginWidget.setLoginListener(this);
		}
	}

}