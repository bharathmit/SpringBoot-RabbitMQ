package com.cable.rest.config;

import java.lang.reflect.Method;

import lombok.extern.log4j.Log4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cable.rest.exception.ExceptionResolver;
import com.cable.rest.exception.RestException;

@Service
@Log4j
public class Listener implements MessageListener, ApplicationContextAware {

    @Autowired
    MessageConverter json;

    @Autowired
    RabbitTemplate q;

    ApplicationContext ctx;

    @Override
    public void onMessage(Message message) {
        try {
        	log.info("----- received" + message.getMessageProperties());
           
            Object request = json.fromMessage(message);

            Object service = ctx.getBean(message.getMessageProperties().getHeaders().get("ServiceName").toString());
            Object response = null;
            try {
                Method method = service.getClass().getMethod(
                        message.getMessageProperties().getHeaders().get("ServiceMethodName").toString(), request.getClass());
                response = method.invoke(service, request);
            } 
            catch (Exception e) {
            	if(e.getCause() instanceof RestException){
            		response=ExceptionResolver.businessRule(e.getCause());
            	}
            	else{
            		log.error("Service Exception", e);
            		response=ExceptionResolver.invalidDataAccess(e);
            	}
            }
            
            if(StringUtils.isEmpty(response)){
            	response=ExceptionResolver.nullPointer();
            }

            q.convertAndSend(message.getMessageProperties().getReplyTo(), response);

        } catch (Exception e) {
            log.error("RabbitMQ Message Convert Exception", e);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
