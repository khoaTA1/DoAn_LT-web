package PKG.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
	@Bean
	public FilterRegistrationBean<SiteMeshFitler> siteMeshFilter() {
		FilterRegistrationBean<SiteMeshFitler> filterRegistration = new FilterRegistrationBean<>();
		filterRegistration.setFilter(new SiteMeshFitler());
		filterRegistration.addUrlPatterns("/*");
		return filterRegistration;
	}
}
