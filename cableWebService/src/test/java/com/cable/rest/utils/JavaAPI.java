package com.cable.rest.utils;

import java.util.*;
public class JavaAPI {
	public static void main(String[] args) {
		Mailin http = new Mailin("https://api.sendinblue.com/v2.0","LKYd2PIqvb4hOMj9");
		Map < String, String > to = new HashMap < String, String > ();
			to.put("divya.c.balakrishnan@accenture.com", "to whom!");

		Map < String, Object > data = new HashMap < String, Object > ();
			data.put("to", to);
			data.put("from", new String [] {"bharathkumar.feb14@gmail.com","from email!"});
			data.put("subject", "My subject");
			data.put("html", "This is the <h1>HTML</h1>");
			//data.put("attachment", new String [] {"https://domain.com/path-to-file/filename1.pdf","https://domain.com/path-to-file/filename1.jpg"});
		String str = http.send_email(data);
		System.out.println(str);
	}
}