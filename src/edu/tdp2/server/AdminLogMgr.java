package edu.tdp2.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
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
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

	public static String getFilteredLog(Date from, Date to, String admin) throws IOException, ParseException
	{
		File logFile = new File(logPath);
		if (!logFile.exists())
			return "";

		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(logFile));
		String line;
		while ((line = br.readLine()) != null)
		{
			String[] fields = line.split(",");
			String lineAdmin = fields[0].substring("ADMIN: ".length());
			String lineDate = fields[1].substring(" FECHA: ".length());
			boolean matchesFrom = from == null || from.before(dateFormat.parse(lineDate));
			boolean matchesTo = to == null || to.after(dateFormat.parse(lineDate));
			boolean matchesAdmin = admin == null || lineAdmin.equals(admin);
			if (matchesFrom && matchesTo && matchesAdmin)
				sb.append(line + "\n");
		}

		return sb.toString();
	}
}
