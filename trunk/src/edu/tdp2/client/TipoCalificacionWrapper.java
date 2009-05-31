package edu.tdp2.client;

import com.google.gwt.core.client.GWT;

public class TipoCalificacionWrapper
{
	private static TipoCalificacionConstants constants = GWT.create(TipoCalificacionConstants.class);

	public enum TipoCalificacion
	{
		Recibida(constants.de()), Hecha(constants.a());

		private String description;

		private TipoCalificacion(String description)
		{
			this.description = description;
		}

		public String getDescription()
		{
			return description;
		}

	}
}