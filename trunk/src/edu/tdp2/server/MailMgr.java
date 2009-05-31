package edu.tdp2.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.tdp2.client.model.Oferta;
import edu.tdp2.client.model.Proyecto;

public class MailMgr
{
	private static Properties mailServerConfig = new Properties();

	/**
	 * Envia los mails necesarios a aquellos usuarios que tengan ofertas mayores que la nueva, y hayan configurado el
	 * alerta
	 */
	public static void enviar(Oferta nueva)
	{
		Proyecto proy = nueva.getProyecto();
		for (Oferta existente : proy.getOfertas())
		{
			if (nueva.getMonto() < existente.getMonto() && nueva.getUsuario().getId() != existente.getUsuario().getId()
					&& existente.getNotificacion().equals("Si"))
			{
				sendEmail(nueva, existente);
			}
		}
	}

	private static void sendEmail(Oferta nueva, Oferta existente)
	{
		String text = mailServerConfig.getProperty("mail.text").replace("$usuarioOrig",
				existente.getUsuario().getNombre()).replace("$nombreProy", nueva.getProyecto().getNombre()).replace(
				"$montoOrig", "" + existente.getMonto()).replace("$usuarioNueva", nueva.getUsuario().getLogin())
				.replace("$montoNueva", "" + nueva.getMonto());

		Session session = Session.getDefaultInstance(mailServerConfig, new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(mailServerConfig.getProperty("mail.from"), mailServerConfig
						.getProperty("mail.from.password"));
			}
		});
		MimeMessage message = new MimeMessage(session);
		try
		{
			InternetAddress to = new InternetAddress(existente.getUsuario().getEmail());
			message.addRecipient(Message.RecipientType.TO, to);
			message.setSubject(mailServerConfig.getProperty("mail.subject"));
			message.setText(text);
			Transport.send(message);
		}
		catch (MessagingException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	static
	{
		fetchConfig();
	}

	private static void fetchConfig()
	{
		InputStream input = null;
		try
		{
			input = MailMgr.class.getClassLoader().getResource("edu/tdp2/server/mail.properties").openStream();
			mailServerConfig.load(input);
		}
		catch (IOException ex)
		{
			System.err.println("Cannot open and load mail server properties file.");
		}
		finally
		{
			try
			{
				if (input != null)
					input.close();
			}
			catch (IOException ex)
			{
				System.err.println("Cannot close mail server properties file.");
			}
		}
	}
}
