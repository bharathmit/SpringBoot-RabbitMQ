package com.cable.rest.utils;

import java.io.*;
import java.net.InetAddress;
import java.util.Properties;
import java.util.Date;

import javax.mail.*;
import javax.mail.internet.*;

import com.sun.mail.smtp.*;


public class MailgunEmail {
	
    public static void main(String args[]) throws Exception {
        Properties props = System.getProperties();
        props.put("mail.smtps.host","smtp.mailgun.org");
        props.put("mail.smtps.auth","true");
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("bharathkumar.feb14@gmail.com"));
        msg.setRecipients(Message.RecipientType.TO,
        InternetAddress.parse("bharathkumar.feb14@gmail.com", false));
        msg.setSubject("Hello");
        msg.setText("Testing some Mailgun awesomness");
        msg.setSentDate(new Date());
        SMTPTransport t =
            (SMTPTransport)session.getTransport("smtps");
        t.connect("smtp.mailgun.com", "postmaster@sandbox8d5e29efe3614d11b260af8dc32e76b4.mailgun.org", "2b99f1f7cf45b987142ce3c95d93e82c");
        t.sendMessage(msg, msg.getAllRecipients());
        System.out.println("Response: " + t.getLastServerResponse());
        t.close();
    }
    
    
    
    
    
    
}