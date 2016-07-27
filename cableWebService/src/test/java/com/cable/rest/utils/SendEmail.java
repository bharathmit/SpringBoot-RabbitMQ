package com.cable.rest.utils;

import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.*;  
  
public class SendEmail {  
 public static void main(String[] args) {  
  
  String host="smtp25.elasticemail.com";  
  final String user="bharathkumar.feb14@gmail.com";//change accordingly  
  final String password="9443ea28-88ee-47c6-b9aa-131224be4dcd";//change accordingly  
    
  String to="bharathkumar.feb14@gmail.com";//change accordingly  
  
   //Get the session object  
   Properties props = new Properties();  
   props.put("mail.smtp.host",host);  
   props.put("mail.smtp.auth", "true");  
   props.put("mail.smtp.starttls.enable", "true");
   props.put("mail.smtp.port", "587");
   
   Session session = Session.getDefaultInstance(props,  
    new javax.mail.Authenticator() {  
      protected PasswordAuthentication getPasswordAuthentication() {  
    return new PasswordAuthentication(user,password);  
      }  
    });  
  
   //Compose the message  
    try {  
     MimeMessage message = new MimeMessage(session);  
     message.setFrom(new InternetAddress(user));  
     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
     message.setSubject("javatpoint");  
     message.setText("This is simple program of sending email using JavaMail API");  
       
    //send the message  
     Transport.send(message);  
  
     System.out.println("message sent successfully...");  
   
     } catch (MessagingException e) {e.printStackTrace();}  
 }  
}  