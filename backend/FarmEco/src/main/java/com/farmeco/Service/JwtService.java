package com.farmeco.Service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.farmeco.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService implements IJwtService {
	
	private String secreteKey="";
	
	
	public JwtService() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator= KeyGenerator.getInstance("HmacSHA256");
		SecretKey key = keyGenerator.generateKey();
		secreteKey= Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public String generateJwToken(User user) {
		
		Map<String, Object> claims= new HashMap<String, Object>();
		claims.put("id", user.getId());
		claims.put("role", user.getRoles());
		claims.put("status", user.getStatus().getIsActive());
		
		
		return Jwts.builder()
				.claims().add(null)
				.subject(user.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+600000))
				.and()
		        .signWith(getKey())
				.compact();
	}

	private Key getKey() {
		System.out.println(secreteKey);
		byte[] key = Base64.getDecoder().decode(secreteKey);
		return Keys.hmacShaKeyFor(key);
	}

	@Override
	public String extractUserName(String token) {
		Claims claims= extractClaims(token);
		return claims.getSubject();
	}

	private Claims extractClaims(String token) {
		try {
			return Jwts.parser()
			.verifyWith((SecretKey) getKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
		} catch (ExpiredJwtException e) {
			throw new ExpiredJwtException(null, null, "Token is Requierd");
		} catch (JwtException e) {
			throw new JwtException("Invalid token");
		}
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		String userName = extractUserName(token);
		Claims claims= extractClaims(token);
		Boolean isExpired=isTokenExpired(claims);
		if(userName.equalsIgnoreCase(userDetails.getUsername()) && !isExpired) {
			return true;
		}
		return false;
	}

	private Boolean isTokenExpired(Claims claims) {
		Date expireDate = claims.getExpiration();
		return expireDate.before(new Date());
		
	}

}
