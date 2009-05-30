package edu.tdp2.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServlet extends HttpServlet
{
	private static final int MAX_LEN_LOGO = 200 * 1024;
	private static final String LOGO_FIELD_NAME = "Logo";
	private static final int MAX_LEN_PROJECT_FILE = 5 * 1024 * 1024;
	private static final String PROJECT_FILE_FIELD_NAME = "Archivo";
	private static final long serialVersionUID = 5436940293464995513L;
	private static Properties props;
	private static final String baseDir;

	static
	{
		try
		{
			props = new Properties();
			props.load(FileUploadServlet.class.getClassLoader().getResource("edu/tdp2/server/server.properties")
					.openStream());
			baseDir = props.getProperty("images.path");
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		resp.reset();
		resp.setContentType("text/html;charset=UTF-8");

		String status;
		try
		{
			status = "OK:" + tryUploadFile(req);
		}
		catch (Exception e)
		{
			status = "ERROR:" + e.getMessage();
			e.printStackTrace();
		}

		ServletOutputStream out = resp.getOutputStream();
		out.write(status.getBytes());
		out.flush();
		out.close();
	}

	private int getMaxLen(String fieldName)
	{
		int maxLen;
		if (fieldName.equals(LOGO_FIELD_NAME))
			maxLen = MAX_LEN_LOGO;
		else if (fieldName.equals(PROJECT_FILE_FIELD_NAME))
			maxLen = MAX_LEN_PROJECT_FILE;
		else
			throw new IllegalArgumentException("Nombre inesperado para el campo que contiene el archivo subido: "
					+ fieldName);
		return maxLen;
	}

	private String getMD5(InputStream is) throws NoSuchAlgorithmException
	{
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] buffer = new byte[8192];
		int read = 0;
		try
		{
			while ((read = is.read(buffer)) > 0)
				digest.update(buffer, 0, read);
			byte[] md5sum = digest.digest();
			BigInteger bigInt = new BigInteger(1, md5sum);
			return bigInt.toString(16);
		}
		catch (IOException e)
		{
			throw new RuntimeException("No pude extraer el MD5 del archivo", e);
		}
	}

	@SuppressWarnings("unchecked")
	private String tryUploadFile(HttpServletRequest req) throws Exception
	{
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(req);

		String fileName = "";
		for (FileItem item : items)
		{
			if (item.isFormField())
				continue;
			if (item.getInputStream().available() > 0)
			{
				String fieldName = item.getFieldName();
				File f = new File(baseDir + File.separator + fieldName + getMD5(item.getInputStream()));
				fileName = f.getName();
				if (!f.exists()) // Si ya existe, no hace falta crearlo
					item.write(f);
				if (!f.exists()) // Si ya lo escribi y todavia no existe estamos en problemas
					throw new FileNotFoundException("No pude crear el archivo");

				int maxLen = getMaxLen(fieldName);
				if (f.length() > maxLen)
				{
					f.delete();
					throw new IllegalArgumentException(props.getProperty("logoSizeError").replaceAll("\\$\\{size\\}",
							((Integer) (maxLen / 1024)).toString()));
				}
			}
			break; // No voy a subir mas de un archivo
		}
		return fileName;
	}
}
