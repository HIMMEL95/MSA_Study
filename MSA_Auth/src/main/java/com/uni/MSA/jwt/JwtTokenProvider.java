package com.uni.MSA.jwt;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Value("${spring.jwt.secret}")
	private String secretKey;
	
	@Value("${spring.jwt.access.header}")
	private String accessTokenHeader;
	
	@Value("${spring.jwt.refresh.header}")
	private String refreshTokenHeader;

	private static final long accessTokenValidMillisecond = 1000L * 60 * 60; // 1시간만 토큰 유효
	private static final long refreshTokenValidMillisecond = 1000L * 60 * 60 * 24 * 14; // 2주 토큰 유효
	
//	초기화 콜백을 적용할 수 있는 어노테이션 
//	javax.annotation-api 의존성 필요
	@PostConstruct	 
	protected void init() {
	    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	    log.info("secretKey is : " + secretKey);
	}
	
	public String createAccessToken(String id) throws Exception {
		
		Claims claims = Jwts.claims().setSubject(id);
		
		long expireTime = accessTokenValidMillisecond;
		
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + expireTime);
		
		return Jwts.builder()
				.addClaims(claims)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public String createRefreshToken(String id) throws Exception {
		
		Claims claims = Jwts.claims().setSubject(id);
		
		long expireTime = accessTokenValidMillisecond;
		
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + expireTime);
		
		return Jwts.builder()
				.addClaims(claims)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public boolean validateToken(String jwtToken) throws Exception {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey("test").parseClaimsJws(jwtToken);
			log.info("result is : " + !claims.getBody().getExpiration().before(new Date()));
			return !claims.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			log.info("expired token", e);
			return false;
		} catch (Exception e) {
			log.info("invalid token", e);
			return false;
		}
	}
	
}
