package PKG.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.model.PaymentIntent;

import PKG.entity.Payment;
import PKG.entity.User;
import PKG.model.paymentInfo;
import PKG.service.PaymentService;
import PKG.service.UserService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;

	@Autowired
	private UserService userserv;

	@PostMapping("")
	public ResponseEntity<Object> processPayment(@RequestBody paymentInfo inputPayment) {
		try {
			PaymentIntent paymentIntent = paymentService.createPaymentIntent(inputPayment.getAmount());

			Payment payment = new Payment();
			payment.setTransactionId(paymentIntent.getId());
			payment.setAmount(inputPayment.getAmount());
			payment.setStatus(paymentIntent.getStatus());
			payment.setTimestamp(LocalDateTime.now());

			User user = userserv.findById(inputPayment.getUserId()).orElseThrow(() -> new Exception("User not found"));
			payment.setUser(user);

			paymentService.save(payment);

			Map<String, Object> resp = new HashMap<>();
			resp.put("client_secret", paymentIntent.getClientSecret());
            resp.put("id", paymentIntent.getId()); 
            
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build();
		}
	}

	@PostMapping("/confirm-payment")
	public String confirmPayment() {

		return "qr_scan";
	}

}