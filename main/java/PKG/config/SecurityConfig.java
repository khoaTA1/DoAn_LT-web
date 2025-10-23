package PKG.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/WEB-INF/**", "/webpage", "/", "/redirect/**", "/user/login**", "/user/register**",
						"/item/getall**", "/item/sort-by-price/**", "/item/search-bar/**", "/image/**", "/item/getinfo/**", "/error")
				.permitAll().anyRequest().authenticated())
				.formLogin(login -> login.loginPage("/redirect/login").defaultSuccessUrl("/webpage", true).failureUrl("/redirect/login"))
				.logout(logout -> logout.logoutUrl("/user/logout").logoutSuccessUrl("/webpage").permitAll())
				.rememberMe(remember -> remember.key("12345").tokenValiditySeconds(60 * 60 * 24)) // thời gian cookie
																									// trong 1 ngày
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
		return firewall;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(HttpFirewall httpFirewall) {
		return (web) -> web.httpFirewall(httpFirewall);
	}
}