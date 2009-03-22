package edu.tdp2.server.exceptions;

public class SoftmartServerException extends RuntimeException
{
	private static final long serialVersionUID = 1339222432261832728L;

	public SoftmartServerException()
	{
		super();
	}

	public SoftmartServerException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public SoftmartServerException(String message)
	{
		super(message);
	}

	public SoftmartServerException(Throwable cause)
	{
		super(cause);
	}
}
