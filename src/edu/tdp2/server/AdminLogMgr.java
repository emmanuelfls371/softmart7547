package edu.tdp2.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.hibernate.Session;

import edu.tdp2.client.model.Administrador;
import edu.tdp2.server.db.HibernateUtil;

public class AdminLogMgr
{
	public enum Accion
	{
		Login, Logout, BloqueoCuenta, ActivacionCuenta, CancelacionProyecto, RevisionProyecto
	}

	private static String logPath;
	private static Properties props;

	static
	{
		try
		{
			props = new Properties();
			props.load(FileUploadServlet.class.getClassLoader().getResource("edu/tdp2/server/server.properties")
					.openStream());
			logPath = props.getProperty("adminlog.path");
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static void log(Administrador admin, Accion accion)
	{
		log(admin, accion, null, null);
	}

	public static void log(Administrador admin, Accion accion, String nombreParam, String valorParam)
	{
		try
		{
			AdminLogMgr.doLog(admin, accion, nombreParam, valorParam);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void doLog(Administrador admin, Accion accion, String nombreParam, String valorParam)
			throws IOException
	{
		File logFile = new File(logPath);
		if (!logFile.exists())
			logFile.createNewFile();

		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String line = "ADMIN: " + admin.getLogin() + ", FECHA: " + dateFormat.format(new Date()) + ", ACCION: "
				+ accion.name();
		if (nombreParam != null && valorParam != null)
			line += ", " + nombreParam + ": " + valorParam;
		out.println(line);
		out.close();
	}

	public static void log(String adminUserName, Accion accion, String nombreParam, String valorParam)
	{
		log(getAdmin(adminUserName), accion, nombreParam, valorParam);
	}

	private static Administrador getAdmin(String userName)
	{
		Session sess = HibernateUtil.getSession();
		Administrador admin = (Administrador) sess.createQuery("FROM Administrador WHERE login = ?").setParameter(0,
				userName).uniqueResult();
		sess.close();
		return admin;
	}
}
