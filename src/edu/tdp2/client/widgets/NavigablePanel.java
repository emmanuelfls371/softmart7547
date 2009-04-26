package edu.tdp2.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
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
		Anchor anchor = new Anchor("Ver proyecto");
		anchor.addClickHandler(getHandlerFormProjectList(projects));
		return anchor;
	}

	protected Anchor getOfferAnchorForProjects(final ProjectList projects)
	{
		Anchor anchor = new Anchor("Ver oferta ganadora");
		anchor.addClickHandler(getOfferHandlerFormProjectList(projects));
		return anchor;
	}

	private ClickHandler getOfferHandlerFormProjectList(final ProjectList projects)
	{
		return ClientUtils.getHandlerForProjects(projects, onShowOfertaDelegate);
	}

	private ClickHandler getHandlerFormProjectList(final ProjectList projects)
	{
		return ClientUtils.getHandlerForProjects(projects, onShowProyectoDelegate);
	}

	private void onShowProyecto(Proyecto project)
	{
		putAlone(new ProjectWidget(project));
	}

	protected void onShowOferta(Proyecto project)
	{
		putAlone(new OfertaWidget(project));
	}

	protected void putAlone(Widget widget)
	{
		clear();
		add(widget);
	}
}
