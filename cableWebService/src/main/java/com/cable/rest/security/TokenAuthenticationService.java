package com.cable.rest.security;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.cable.rest.dto.UserDto;

import static java.util.Collections.emptyList;

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
	
	static Authentication getAuthentication(HttpServletRequest request) {
		log.error("URL = " + request.getRequestURL());
		String token = request.getHeader(HEADER_STRING);
		if (!StringUtils.isEmpty(token)  && token.startsWith("Bearer") ) {
			// parse the token.
			Claims claims = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			
			return claims != null ? new UsernamePasswordAuthenticationToken(claims.get("user"),
					null, emptyList()) : null;
		}
		return null;
	}
	
	
	
		


}
