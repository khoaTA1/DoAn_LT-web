package PKG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import PKG.service.PaymentService;

@Controller
public class PaymentController {
	 @Autowired
	    private PaymentService paymentService;

    @PostMapping("/confirm-payment")
    public String confirmPayment() {
        // Sau này bạn có thể thêm xử lý lưu thông tin thanh toán ở đây
        return "qr_scan"; // ✅ trỏ đến trang qr_scan.jsp
    }

}
