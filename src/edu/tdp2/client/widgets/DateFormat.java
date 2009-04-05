package edu.tdp2.client.widgets;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;

public class DateFormat implements Format
{
	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd/MM/yyyy");

	public String format(DateBox dateBox, Date date)
	{
		if (date == null)
			return null;
		else
			try
			{
				return DATE_FORMAT.format(date);
			}
			catch (Exception e)
			{
				return null;
			}
	}

	public Date parse(DateBox dateBox, String text, boolean reportError)
	{
		if (text == null || text.trim().isEmpty())
			return null;
		else
			try
			{
				return DATE_FORMAT.parse(text);
			}
			catch (Exception e)
			{
				return null;
			}
	}

	public void reset(DateBox dateBox, boolean abandon)
	{
	}
}