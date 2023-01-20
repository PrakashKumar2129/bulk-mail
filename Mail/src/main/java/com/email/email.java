package com.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class email {
	
    public static void sendEmail(String host, String port, final String userName, final String password, String[] toAddressArray, String subject, String message) 
    		throws AddressException, MessagingException {
 
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
 
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        
        InternetAddress[] toAddress = new InternetAddress[toAddressArray.length];
        
        for(int i=0; i<toAddressArray.length; i++)
        	toAddress[i] = new InternetAddress(toAddressArray[i]);
        
        msg.addRecipients(Message.RecipientType.TO, toAddress);
        
        msg.setSubject(subject);
        
        msg.setSentDate(new Date());
        
        msg.setText(message);
 
        // sends the e-mail
        Transport.send(msg);
 
    }

}

