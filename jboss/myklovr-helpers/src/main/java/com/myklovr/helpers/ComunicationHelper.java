package com.myklovr.helpers;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.myklovr.helpers.constants.MessagesConstants;
import com.myklovr.helpers.constants.PropertiesConstants;
import com.myklovr.helpers.exception.BussinesException;
import com.myklovr.logger.Log;

public class ComunicationHelper {

	public static void sendEmail(String to, String subject, String text) throws BussinesException{
		final String username = PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_MAIL_USER);
		final String password = PropertiesHelper.getStringConfigPropertyEncrypted(PropertiesConstants.CONFIG_MAIL_PASSWORD);

		Properties props = new Properties();
		props.put(PropertiesConstants.CONFIG_MAIL_SMTP_AUTH, PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_MAIL_SMTP_AUTH));
		props.put(PropertiesConstants.CONFIG_MAIL_SMTP_STARTTLS, PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_MAIL_SMTP_STARTTLS));
		props.put(PropertiesConstants.CONFIG_MAIL_SMTP_HOST, PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_MAIL_SMTP_HOST));
		props.put(PropertiesConstants.CONFIG_MAIL_SMTP_PORT, PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_MAIL_SMTP_PORT));

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		    }
		});

		try {

		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(PropertiesHelper.getStringConfigProperty(PropertiesConstants.CONFIG_MAIL_DISPLAY)));
		    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		    message.setSubject(subject);

		    Multipart mp = new MimeMultipart();
		    MimeBodyPart htmlPart = new MimeBodyPart();
		    htmlPart.setContent(text, "text/html");
		    mp.addBodyPart(htmlPart);
		    message.setContent(mp);
		    Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", "username", "password");
		    //Transport.send(message);
	        transport.sendMessage(message, message.getAllRecipients());

		} catch (MessagingException e) {
			Log.error(e.getMessage(), e);
		    throw new BussinesException(PropertiesHelper.getStringMessageProperty(MessagesConstants.ERROR_SEND_MAIL));
		}
	}
	
	public static void sendSMS(String phone, String text){
		
	}
		
}
