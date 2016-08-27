package com.cable.rest.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cable.rest.repository.ConnectionAccountJPARepo;

@Component
public class RandomUtil {
	
    static final String CHARACTER_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String NUMBER_SET="0123456789";
    static Random rnd ;
    
    @Autowired
    ConnectionAccountJPARepo accountRepo;
    
    private RandomUtil() {
    	rnd = new Random();
    }
    
    public String getTrackId( ) 
    {
       StringBuilder sb = new StringBuilder( 7 );
       for( int i = 0; i < 3; i++ ) 
          sb.append( CHARACTER_SET.charAt( rnd.nextInt(CHARACTER_SET.length()) ) );
       for( int i = 0; i < 4; i++ ) 
           sb.append( NUMBER_SET.charAt( rnd.nextInt(NUMBER_SET.length()) ) );
       
       if(StringUtils.isEmpty(accountRepo.findByAccountToken(sb.toString()))){
    	   return sb.toString();
       }
       else{
    	   getTrackId(); 
       }
       return sb.toString();
    }
}
