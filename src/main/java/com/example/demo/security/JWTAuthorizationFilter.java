package com.example.demo.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthorizationFilter extends OncePerRequestFilter  {
	
	private static final String HEADER = "Authorization";
	private static final String PREFIX = "Bearer";
	private static final String SECRET = "cpMW1RjvLE41Ra3GwMZYTLhyMwZNesxo";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			if(existeJWTToken(request, response)) {
				System.out.println("Si existe un authorization");
				Claims clams = validateToken(request);
				if(clams.get("roles") != null) {
					setUpSpringAuthentication(clams);
				}else {
					SecurityContextHolder.clearContext();
				}
			}else {
				System.out.println("No tiene un Authorization...");
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			System.out.println("Error: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
		
	}
	
	public static Object getUserInfo(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "").replace(" ", "");
		
		Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
		
		return claims.get("data");
	}
	
	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "").replace(" ", "");
		return Jwts
				.parser()
				.setSigningKey(SECRET.getBytes())
				.parseClaimsJws(jwtToken)
				.getBody();
	}
	
	/**
	 * Metodo para autenticarnos dentro del flujo de spring
	 * @param claims
	 */
	private void setUpSpringAuthentication(Claims claims) {
		
		@SuppressWarnings("unchecked")
		List<String> roles = (List<String>) claims.get("roles");
		
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, 
				roles
				.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList())
				);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
	}
	
	
	private boolean existeJWTToken(HttpServletRequest request, HttpServletResponse response) {
		String authenticateHeader = request.getHeader(HEADER);
		
		if(authenticateHeader == null || !authenticateHeader.startsWith(PREFIX)) {
			return false;
		}
		
		return true;
	}

}
