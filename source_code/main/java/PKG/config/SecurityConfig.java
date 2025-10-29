package PKG.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

	@Autowired
	JWTAuthenticationFilter jwtfilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
    @Order(1)
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        http
				.securityMatcher("/api/**", "/payment")
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/getitem/**", "/api/comments/**").permitAll().anyRequest().authenticated())
				// .authenticationProvider(authProvider)
				.exceptionHandling(ex -> ex
		                .authenticationEntryPoint((request, response, authException) -> {
		                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		                    response.getWriter().write("Unauthorized");
		                })
		            )
				.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class).csrf(csrf -> csrf.disable());

        return http.build();
    }

	@Bean
	@Order(2)
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/WEB-INF/**", "/homepage", "/", "/redirect/**", "/user/login**", "/user/register**", "/user/logout",
						"/item/**",
						//"/item/getall**", "/item/sort-by-price/**", "/item/search-bar/**", "/item/getinfo/**",
						"/image/**", "/api/getitem/**", "/user/gencode", "/user/entercode",
						"/error").permitAll()
				.requestMatchers("/user/chatroom").hasAnyAuthority("ADMIN", "USER")
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
				.requestMatchers("/user/**").hasAuthority("USER")
				.anyRequest().authenticated())
				.exceptionHandling(
						exception -> exception.authenticationEntryPoint((request, response, authException) -> {
							response.sendRedirect("/redirect/login");
						}))
				.csrf(csrf -> csrf.disable());

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public HttpFirewall allowUrlEncodedDoubleSlashHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowUrlEncodedDoubleSlash(true);
		firewall.setAllowUrlEncodedSlash(true);
		firewall.setAllowUrlEncodedPercent(true);
        firewall.setAllowUrlEncodedPeriod(true);
		return firewall;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(HttpFirewall httpFirewall) {
		return (web) -> web.httpFirewall(httpFirewall);
	}
	
}