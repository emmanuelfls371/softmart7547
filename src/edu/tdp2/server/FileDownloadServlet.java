package edu.tdp2.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadServlet extends HttpServlet
{
	private static final long serialVersionUID = 8789085434182536358L;
	private static Properties props;

	static
	{
		try
		{
			props = new Properties();
			props.load(FileDownloadServlet.class.getClassLoader().getResource("edu/tdp2/server/server.properties")
					.openStream());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.reset();

		ServletOutputStream out = response.getOutputStream();
		out.write(getImage(request, response));

		out.flush();
		out.close();
	}

	private byte[] getImage(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException,
			IOException
	{
		String imageName = request.getParameter("fileName");
		String path = props.getProperty("images.path") + "\\" + imageName;

		System.out.println("Intentando abrir el archivo: " + path);
		File imageFile = new File(path);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "inline;filename=" + imageName);
		if (imageFile.exists())
		{
			System.out.println("Archivo encontrado");
			int length = (int) imageFile.length();
			byte[] imageBytes = new byte[length];
			response.setContentLength(length);
			FileInputStream fis = new FileInputStream(imageFile);
			fis.read(imageBytes);
			return imageBytes;
		}
		return new byte[0];
	}
}
