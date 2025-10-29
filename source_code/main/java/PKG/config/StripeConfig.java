package PKG.config;

import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@Configuration
public class StripeConfig {
	private String stripe_api_key = "sk_test_51SMg3aKQuP6iqjXYc8SjQWHM6BiMlPwLumOa4jzgCeh8MgXbP3fuNVe32ijyCgMiOB2pxgiFSfUyMk6IzRifLddN00JhFfGWs9";
	
	@PostConstruct
	public void configureStripe() {
		Stripe.apiKey = stripe_api_key;
	}
}
