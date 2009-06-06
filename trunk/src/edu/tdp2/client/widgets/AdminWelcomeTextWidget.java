package edu.tdp2.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.tdp2.client.utils.ClientUtils;

public class AdminWelcomeTextWidget extends AdminWidget
{
	private static AdminWelcomeTextWidget instance;

	public static AdminWelcomeTextWidget getInstance()
	{
		if (instance == null)
			instance = new AdminWelcomeTextWidget();
		return instance;
	}

	private TextArea text = new TextArea();
	private VerticalPanel vPanel = new VerticalPanel();
	private AdminWelcomeTextConstants constants = GWT.create(AdminWelcomeTextConstants.class);

	private String locale;

	@Override
	public void load()
	{
		container.add(vPanel, constants.textoWelcome());

		statusMessage = new HTML();
		loadPanel();
	}

	public void setLocale(String locale)
	{
		this.locale = locale;
	}

	private void loadPanel()
	{
		text.setVisibleLines(20);
		setWelcomeText(locale, text);
		text.setWidth("700px");
		vPanel.add(statusMessage);
		vPanel.add(text);

		Button guardarTexto = new Button(constants.guardar());
		guardarTexto.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				AsyncCallback<String> callback = new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert(caught.getMessage());
						statusMessage.setHTML(constants.errorSetTextoBienvenida());
					}

					public void onSuccess(String result)
					{
						statusMessage.setHTML(constants.textoModificado());
					}
				};
				ClientUtils.getSoftmartService().setTextoBienvenida(locale, text.getText(), callback);
			}
		});
		vPanel.add(guardarTexto);
	}

	private void setWelcomeText(String locale, final TextArea widget)
	{
		AsyncCallback<String> callback = new AsyncCallback<String>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			public void onSuccess(String result)
			{
				widget.setText(result);
			}
		};
		ClientUtils.getSoftmartService().getTextoBienvenida(locale, callback);
	}
}
