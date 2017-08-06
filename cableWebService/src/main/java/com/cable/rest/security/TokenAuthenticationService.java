package com.cable.rest.security;

import static java.util.Collections.emptyList;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.cable.rest.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Log4j
public class TokenAuthenticationService {
	
	static final long EXPIRATIONTIME = 864_000_000; // 10 days
	static final String SECRET = "ThisIsASecret";
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String HEADER_STRING = "Authorization";
	
	public static String addAuthentication(String username,UserDto user) {
		String JWTtoken = Jwts
				.builder()
				.setSubject(username)
				.claim("user", user)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		return JWTtoken;
		//res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}
	
	static Authentication getAuthentication(HttpServletRequest request) throws IOException {
		log.error("URL = " + request.getRequestURL());
		String token = request.getHeader(HEADER_STRING);
		if (!StringUtils.isEmpty(token)  && token.startsWith("Bearer") ) {
			// parse the token.
			Claims claims = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			LinkedHashMap userObj= (LinkedHashMap) claims.get("user");
			ObjectMapper mapper=new ObjectMapper();
			String json= mapper.writeValueAsString(userObj);
			 UserDto user = mapper.readValue(json, UserDto.class);
			/*Gson gson = new Gson();
			String json = gson.toJson(userObj,LinkedHashMap.class);
			
			UserDto user = gson.fromJson(json, UserDto.class);
			*/
			return claims != null ? new UsernamePasswordAuthenticationToken(user,
					user, emptyList()) : null;
		}
		return null;
	}
	
	
	
		


}
