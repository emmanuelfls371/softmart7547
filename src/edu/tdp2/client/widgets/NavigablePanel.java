package edu.tdp2.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.tdp2.client.OfertaWidget;
import edu.tdp2.client.ProjectList;
import edu.tdp2.client.ProjectWidget;
import edu.tdp2.client.model.Proyecto;
import edu.tdp2.client.utils.ClientUtils;
import edu.tdp2.client.utils.OneParamDelegate;

public class NavigablePanel extends VerticalPanel
{
	private NavigablePanelConstants constants = GWT.create(NavigablePanelConstants.class);
	// protected Proyecto proyectoActual;
	// protected ProjectList projs;

	private OneParamDelegate<Proyecto> onShowOfertaDelegate = new OneParamDelegate<Proyecto>()
	{
		public void invoke(Proyecto p)
		{

			onShowOferta(p);
		}
	};

	private OneParamDelegate<Proyecto> onShowProyectoDelegate = new OneParamDelegate<Proyecto>()
	{
		public void invoke(Proyecto p)
		{
			onShowProyecto(p);
		}
	};

	protected Anchor getAnchorForProjects(final ProjectList projects)
	{
		Anchor anchor = new Anchor(constants.verProyecto());
		anchor.addClickHandler(getHandlerFormProjectList(projects));
		return anchor;
	}

	protected Anchor getOfferAnchorForProjects(final ProjectList projects)
	{
		Anchor anchor = new Anchor(constants.verOfertaGanadora());
		anchor.addClickHandler(getOfferHandlerFormProjectList(projects));
		return anchor;
	}

	protected void onShowOferta(Proyecto proyectoActual)
	{

		putAlone(new OfertaWidget(proyectoActual));
	}

	protected void putAlone(Widget widget)
	{
		History.newItem("");
		clear();
		add(widget);
	}

	private ClickHandler getHandlerFormProjectList(final ProjectList projects)
	{
		return ClientUtils.getHandlerForProjects(projects, onShowProyectoDelegate);
	}

	private ClickHandler getOfferHandlerFormProjectList(final ProjectList projects)
	{
		return ClientUtils.getHandlerForProjects(projects, onShowOfertaDelegate);
	}

	private void onShowProyecto(Proyecto project)
	{

		putAlone(new ProjectWidget(project, true));
	}

	/*
	 * protected void putAlone(Widget widget, String historyToken){ History.newItem(historyToken); clear(); add(widget);
	 * }
	 */

}
