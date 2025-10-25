package PKG.service.impl;

import org.springframework.stereotype.Service;
import PKG.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public void processPayment() {
        // Tạm thời chỉ giả lập việc xử lý thanh toán
        System.out.println("Thanh toan dang duoc xu ly...");
    }
}