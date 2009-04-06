package edu.tdp2.server.exceptions;

public class BadLoginException extends SoftmartServerException
{
	private static final long serialVersionUID = 1339222432261832728L;

	public BadLoginException()
	{
		super();
	}

	public BadLoginException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BadLoginException(String message)
	{
		super(message);
	}

	public BadLoginException(Throwable cause)
	{
		super(cause);
	}
}
