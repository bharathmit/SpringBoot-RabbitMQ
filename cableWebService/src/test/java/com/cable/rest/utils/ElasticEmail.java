package com.cable.rest.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ElasticEmail {
    
	public static void main(String[] args) {
		SendElasticEmail("bharathkumar.feb14@gmail.com", "9443ea28-88ee-47c6-b9aa-131224be4dcd", "bharathkumar.feb14@gmail.com", "bharath" , "Sample" , "Welcome", "bharathkumar.feb14@gmail.com");
	}
	
    public static String SendElasticEmail(String userName, String apiKey, String from, String fromName, String subject, String body, String to)  {
        
        try {
            
            //Construct the data
            String data = "userName=" + URLEncoder.encode(userName, "UTF-8");
            data += "&api_key=" + URLEncoder.encode(apiKey, "UTF-8");
            data += "&from=" + URLEncoder.encode(from, "UTF-8");
            data += "&from_name=" + URLEncoder.encode(fromName, "UTF-8");
            data += "&subject=" + URLEncoder.encode(subject, "UTF-8");
            data += "&body_html=" + URLEncoder.encode(body, "UTF-8");
            data += "&to=" + URLEncoder.encode(to, "UTF-8");
            
            //Send data
            URL url = new URL("https://api.elasticemail.com/mailer/send");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));            
            String result = rd.readLine();
            wr.close();
            rd.close();
            System.out.println(result);
            return result;
        }
        
        catch(Exception e) {
            
            e.printStackTrace();
        }
		return "";
    }

}

