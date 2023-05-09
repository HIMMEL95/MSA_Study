package com.uni.MSA.jwt;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
	
	@PostConstruct
	protected void init() {
	    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
}
