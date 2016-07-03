package com.cable.rest.service;

import java.util.Date;
import javax.mail.internet.MimeMessage;

import lombok.extern.log4j.Log4j;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.cable.rest.dto.EmailDto;

@Service
@Log4j
public class EmailNotificationService {

	
	@Autowired
    private Environment env;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	VelocityEngine velocityEngine;
	
	
	public boolean constructEmailMessage(final EmailDto request) {
		try{
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
		        @Override
                @SuppressWarnings({ "rawtypes", "unchecked" })
				public void prepare(MimeMessage mimeMessage) throws Exception {
		             MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		             message.setTo(request.getTo());
		            // message.setBcc(request.getBcc());
		             message.setFrom(env.getProperty("support.email"));
		             message.setSubject(request.getSubject());
		             message.setSentDate(new Date());
		             
		             
		             String text = VelocityEngineUtils.mergeTemplateIntoString(
		                velocityEngine, request.getTemplateLocation(), "UTF-8", request.getModel());
		             message.setText(text, true);
		             
		            // message.addAttachment(attachmentFilename, dataSource);
		            // message.addInline(contentId, dataSource);
		          }
		       };
		       log.info("Mail method call");
		       mailSender.send(preparator);
		       return true;
		}
		catch(Exception e){
			 log.info("Mail method Exception",e);
			return false;
		}
	      
						
	}
	
	
	
}
