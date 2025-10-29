package PKG.service;

import com.stripe.model.PaymentIntent;

import PKG.entity.Payment;

public interface PaymentService {

	<S extends Payment> S save(S entity);

	PaymentIntent createPaymentIntent(double amount) throws Exception;

}
