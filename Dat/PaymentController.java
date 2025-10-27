package PKG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import PKG.entity.OrderDetail;
import PKG.service.OrderDetailService;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/confirm")
    public String confirmPayment(
            @RequestParam("hoten") String hoTen,
            @RequestParam("sdt") String sdt,
            @RequestParam("diachi") String diaChi,
            @RequestParam("phuongthuc") String phuongThuc,
            @RequestParam(value = "tongtien", required = false) Double tongTien, // ✅ Cho phép null
            Model model) {

        // ✅ Nếu tổng tiền không có, đặt mặc định = 0
        if (tongTien == null) {
            tongTien = 0.0;
        }

        // Tạo đối tượng order mới
        OrderDetail order = new OrderDetail();
        order.setHoTenNguoiNhan(hoTen);
        order.setSoDienThoai(sdt);
        order.setDiaChiGiaoHang(diaChi);
        order.setPhuongThucThanhToan(phuongThuc);
        order.setTongTien(tongTien);

        // Lưu vào DB
        orderDetailService.saveOrderDetail(order);

        // Trả thông báo thành công
        model.addAttribute("msg", "Thanh toán thành công! Đơn hàng đã được lưu vào hệ thống.");

        // Trả về trang JSP thông báo
        return "payment_success"; 
    }
}
