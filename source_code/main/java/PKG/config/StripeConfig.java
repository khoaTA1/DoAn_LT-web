package PKG.config;

import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@Configuration
public class StripeConfig {
	private String stripe_api_key = "sk_test_khongduocxem";
	
	@PostConstruct
	public void configureStripe() {
		Stripe.apiKey = stripe_api_key;
	}
}

