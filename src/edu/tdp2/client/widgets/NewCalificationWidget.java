package edu.tdp2.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.validation.client.interfaces.IValidator;

import edu.tdp2.client.dto.CalificacionDto;
import edu.tdp2.client.dto.Dto;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;

public class NewCalificationWidget extends FormWidget
{
	private static NewCalificationWidget instance;
	private List<String> errMsgs;
	private long projectId;

	public static NewCalificationWidget getInstance(Proyecto project)
	{
		if (instance == null)
			instance = new NewCalificationWidget();
		((Label) instance.widgets.get(CalificacionFields.Proyecto)).setText(project.getNombre());
		//((Label) instance.widgets.get(CalificacionFields.Usuario)).setText(project.getUsuario().getLogin());
		
		instance.projectId = project.getId();
		return instance;
	}

	private NewCalificationWidget()
	{
		tituloWidget = "<b>Calificar</b>";
		anchoWidget = "200px";
		anchoTabla = "100px";
		dto = new CalificacionDto();
		errMsgs = new ArrayList<String>();
		init();
	}

	@Override
	protected IValidator<Dto> getValidator()
	{
		return GWT.create(CalificacionDto.class);
	}

	@Override
	protected void populateWidgets()
	{
		widgets.put(CalificacionFields.Proyecto, new Label());

		//widgets.put(CalificacionFields.Usuario, new Label());

		TextBox t = new TextBox();
		t.setMaxLength(2);
		t.setWidth("30px");
		t.setName(CalificacionFields.Calif.toString());
		widgets.put(CalificacionFields.Calif, t);

		t = new TextBox();
		t.setHeight("100px");
		t.setName(CalificacionFields.Comentario.toString());
		widgets.put(CalificacionFields.Comentario, t);
	}

	@Override
	protected FormFields[] values()
	{
		return CalificacionFields.values();
	}

	@Override
	protected void buildWidget()
	{
		super.buildWidget();
		addSubmitHandler(new CalificacionSubmitHandler());
	}

	@Override
	protected void validate(List<String> errMsgs)
	{
		errMsgs.addAll(this.errMsgs);
	}

	private final class CalificacionSubmitHandler implements SubmitHandler
	{
		public void onSubmit(SubmitEvent event)
		{
			errMsgs.clear();
			dto = new CalificacionDto();
			CalificacionDto califDto = (CalificacionDto) dto;
			try
			{
				califDto.setComentario(((TextBox) instance.widgets.get(CalificacionFields.Comentario)).getText());
				califDto.setCalificacion(Integer.parseInt(((TextBox) instance.widgets.get(CalificacionFields.Calif))
						.getText()));
				califDto.setProyecto(projectId);
				califDto.setUsuario(LoginWidget.getCurrentUser());

				if (!validate())
					return;

				ClientUtils.getSoftmartService().calificar((CalificacionDto) dto, new AsyncCallback<String>()
				{
					public void onFailure(Throwable caught)
					{
						Window.alert("Error inesperado, no se pudo calificar");
					}

					public void onSuccess(String errMsg)
					{
						if (errMsg != null)
							Window.alert(errMsg);
						else
							reload();
					}
				});
			}
			catch (NumberFormatException e)
			{
				errMsgs.add("El formato de la calificacion no es valido");
				validate();
			}
		}
	}

	private enum CalificacionFields implements FormFields
	{
		Proyecto,/* Usuario("Usuario a calificar"),*/ Calif("Calificaci&oacute;n"), Comentario;

		private CalificacionFields(String description)
		{
			this.description = description;
		}

		public String description;

		private CalificacionFields()
		{
			this.description = name();
		}

		public String getDescription()
		{
			return description;
		}
	}
}