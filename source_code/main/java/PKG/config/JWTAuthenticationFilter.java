package PKG.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	JWT jwtserv;

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	HandlerExceptionResolver handlerExceptionResolver;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp,
			@NonNull FilterChain filterchain) throws ServletException, IOException {
		
		/*
		String uri = req.getRequestURI();
		if (!uri.startsWith("/api")) {
			filterchain.doFilter(req, resp);
			return;
		}*/
		
		String authHeader = req.getHeader("Authorization");
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterchain.doFilter(req, resp);
			return;
		}
		
		try {
			String jwt = authHeader.substring(7);
			String username = jwtserv.extractUsername(jwt);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			if (username != null && auth == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				if (jwtserv.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
					SecurityContextHolder.getContext().setAuthentication(authToken);
					
					System.out.println("Auth in context: " + SecurityContextHolder.getContext().getAuthentication());
				}
			}
			
			filterchain.doFilter(req, resp);
		} catch (Exception e) {
			handlerExceptionResolver.resolveException(req, resp, null, e);
		}
	}

}
