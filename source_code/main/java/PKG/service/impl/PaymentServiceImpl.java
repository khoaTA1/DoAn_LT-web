package PKG.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import PKG.entity.Payment;
import PKG.repository.PaymentRepo;
import PKG.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	PaymentRepo paymentrepo;

	@Override
	public <S extends Payment> S save(S entity) {
		return paymentrepo.save(entity);
	}

	@Override
	public PaymentIntent createPaymentIntent(double amount) throws Exception {
		System.out.println(amount);
		PaymentIntentCreateParams params = PaymentIntentCreateParams.builder().setAmount((long) amount)
				.setCurrency("vnd").build();

		PaymentIntent paymentIntent = PaymentIntent.create(params);
		return paymentIntent;
	}
}
