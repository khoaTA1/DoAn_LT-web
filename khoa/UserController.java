package PKG.controller;

import java.util.Collection;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import PKG.entity.User;
import PKG.service.AccountAuthService;
import PKG.service.PasswdService;
import PKG.service.UserService;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	UserService userserv;

	@Autowired
	PasswdService passserv;

	@Autowired
	AccountAuthService AAS;

	@PostMapping("register")
	public String register(ModelMap model, @RequestParam(name = "username") String usern,
			@RequestParam(name = "password") String passwd,
			@RequestParam(name = "confirmPassword") String confirm,
			 @RequestParam(name = "email") String email) {
		
		// kiểm tra username đã tồn tại chưa
		if (userserv.existsByUserName(usern)) {
			model.addAttribute("msg", "Username đã tồn tại, vui lòng chọn username khác!");
			return "register";
		}
		
		// kiểm tra email có hợp lệ không
		if (!checkEmailValid(email)) {
			model.addAttribute("msg", "Email không hợp lệ, vui lòng kiểm tra lại");
			return "register";
		}
		
		// kiểm tra email đã được dùng để đăng ký tài khoản khác chưa
		if (userserv.existsByEmail(email)) {
			model.addAttribute("msg", "Email đã được đăng ký, vui lòng nhập email khác!");
			return "register";
		}

		// kiểm tra mật khẩu đã được nhập chưa
		if (passwd == null || confirm == null) {
			model.addAttribute("msg", "Mật khẩu không được để trống!");
			return "register";
		}
		
		// kiểm tra độ mạnh của mật khẩu
		if (!checkPassStrength(passwd)) {
			model.addAttribute("msg", "Mật khẩu cần có độ dài từ 8 kí tự trở lên, bao gồm ít nhất 1 kí tự chữ cái thường, 1 kí tự chữ cái viết hoa, 1 kí tự số và 1 kí tự đặc biệt.");
			return "register";
		}
		
		// 2 mật khẩu mới nhập phải khớp nhau
		if (!passwd.equals(confirm)) {
			model.addAttribute("msg", "Mật khẩu mới không khớp!");
			return "register";
		}
		
		// tiến hành đăng ký tài khoản mới
		if (!AAS.registerUser(usern, passwd, email)) {
			model.addAttribute("msg", "Lỗi hệ thống, vui lòng thử lại!");
			return "register";
		}

		model.addAttribute("msg", "Đăng ký thành công! bạn có thể đăng nhập ngay bây giờ");
		return "login";
	}

	@PostMapping("login")
	public String login(HttpServletRequest req, ModelMap model,
			@RequestParam(name = "username") String usern,
			@RequestParam(name = "password") String passwd) {
				
		// kiểm tra mật khẩu có để trống không
		if (passwd == null) {
			model.addAttribute("msg", "Mật khẩu không được để trống!");
			return "login";
		}
		
		User loginUser = userserv.findByUserName(usern).orElse(null);

		if (loginUser == null) {
			model.addAttribute("msg", "Tài khoản không tồn tại!");
			return "login";
		}

		try {
			// Thực hiện xác thực tài khoản từ service
			Authentication authen = AAS.loginUser(usern, passwd);

			// Lưu vào SecurityContext
			SecurityContextHolder.getContext().setAuthentication(authen);

			Collection<? extends GrantedAuthority> authoritiesOfThisUser = authen.getAuthorities();

			// Kiểm tra xem user này có quyền admin không
			boolean isAdmin = authoritiesOfThisUser.stream()
					.anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

			HttpSession ss = req.getSession();
			ss.setAttribute("account", loginUser);
			
			if (isAdmin) {
				ss.setAttribute("role", "ADMIN");
				return "admin";
			}

			ss.setAttribute("role", "USER");
			return "home";

		} catch (Exception authE) {
			model.addAttribute("msg", "Sai tên đăng nhập hoặc mật khẩu");
			System.out.print(authE);
			return "login";
		}
	}

	@PostMapping("gencode")
	public String genOTPCode(HttpServletRequest req, ModelMap model,
			@RequestParam(name = "email", required = true) String email) {
		String otp_code = "";

		// kiểm tra nếu có tài khoản được đăng ký với email đã cung cấp
		if (userserv.existsByEmail(email)) {
			// Tạo mã OTP 6 số gửi về mail nếu mail đã đăng ký
			otp_code = UserController.generateOTP(6);

			HttpSession ss = req.getSession();
			ss.setAttribute("otp-email", email);
			ss.setAttribute("otp", otp_code);
			ss.setAttribute("otp-timeout", System.currentTimeMillis());

			UserController.sendEmail(email, "OTP thay đổi mật khẩu", otp_code);
		} else {
			System.out.print("Email chua duoc dang ky!");
		}

		//model.addAttribute("msg",
			//	"Nếu email này đã đăng ký cho tài khoản của bạn, bạn sẽ nhận được mã 6 chữ số trong hộp thư của bạn, hãy nhập mã vào đây.");
		return "enterOTP";
	}

	@PostMapping("entercode")
	public String enterOTPCode(HttpServletRequest req, ModelMap model, @RequestParam("code") String otp_code_entered) {
		HttpSession ss = req.getSession();

		// Kiểm tra xem nếu thời gian tồn tại của mã otp có quá 3 phút không
		if ((long) ss.getAttribute("otp-timeout") - System.currentTimeMillis() <= 3 * 60 * 1000) {
			if (ss.getAttribute("otp").equals(otp_code_entered)) {
				return "changepass";
			} else {
				model.addAttribute("msg", "Mã OTP không hợp lệ!");
				return "enterOTP";
			}
		} else {
			model.addAttribute("msg", "Mã OTP đã hết hạn! Vui lòng yêu cầu lại mã OTP");
			return "enterOTP";
		}
	}

	@PostMapping("changepassw")
	public String changePassw(HttpServletRequest req, ModelMap model, 
			@RequestParam("newPass") String newPassw,
			@RequestParam("confirmNewPass") String confirm) {
		
		if (newPassw.isBlank() || confirm.isBlank()) {
			model.addAttribute("msg", "Mật khẩu không được để trống!");
			return "changepass";
		}
		
		// Nếu mật khẩu mới nhập không khớp
		if (!newPassw.equals(confirm)) {
			model.addAttribute("msg", "Mật khẩu mới không khớp!");
			return "changepass";
		}
		
		if (!checkPassStrength(newPassw)) {
			model.addAttribute("msg", "Mật khẩu cần có độ dài từ 8 kí tự trở lên, bao gồm ít nhất 1 kí tự chữ cái thường, 1 kí tự chữ cái viết hoa, 1 kí tự số và 1 kí tự đặc biệt.");
			return "changepass";
		}
		
		HttpSession ss = req.getSession();
		String usern = userserv.findByEmail((String) ss.getAttribute("otp-email")).get().getUserName();
		if (!AAS.changePass(usern, newPassw)) {
			model.addAttribute("msg", "Lỗi hệ thống, vui lòng thử lại!");
			return "changepass";
		}
		
		model.addAttribute("msg", "Đổi mật khẩu thành công!");
		return "login";
	}
	
	//============================== Các hàm phụ: tạo và gửi mã OTP ============================
	// Hàm tạo OTP
	public static String generateOTP(int length) {
		// tạo dải chữ số sẽ có mặt trong mã OTP
		String scope = "0123456789";
		Random rand = new Random();
		StringBuilder otp = new StringBuilder();

		for (int i = 0; i < length; i++) {
			otp.append(scope.charAt(rand.nextInt(scope.length())));
		}

		return otp.toString();
	}

	// Hàm gửi mail
	public static void sendEmail(String to, String subject, String optCode) {
		final String username = "dmd.spdep@gmail.com";
		final String password = "okplegdnzjiimoyr";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session ss = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		String mailContent = "Xin chào,\n" 
							+ "Đây là mã OTP khôi phục mật khẩu của tài khoản được đăng ký trên DienMayDo.\n" 
							+ "Vui lòng không chia sẻ mã OTP này cho bất kỳ ai:\n"
							+ optCode + " (Không chia sẻ cho bất kỳ ai)\n" 
							+ "Trân trọng.";
		try {
			Message message = new MimeMessage(ss);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(mailContent);

			Transport.send(message);
			System.out.println("Email đã được gửi đến: " + to);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	//==============================######################============================
	
	//============================== Hàm phụ kiểm tra tính hợp lệ ============================
	// kiểm tra độ mạnh của mật khẩu
	public static boolean checkPassStrength(String passw) {
		String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>]).*$";
		
		// mật khẩu phải có độ dài từ 8 kí tự trở lên
		if (passw.length() < 8) return false;
		
		// tạo mẫu so sánh
		Pattern pat = Pattern.compile(regex);
		
		// kiểm tra mật khẩu
		Matcher mat = pat.matcher(passw);
		
		return mat.matches();
	}
	
	// kiểm tra email hợp lệ
	public static boolean checkEmailValid(String email) {
		String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		
		// tạo mẫu so sánh
		Pattern pat = Pattern.compile(regex);
		
		// kiểm tra email hợp lệ
		Matcher mat = pat.matcher(email);
		
		return mat.matches();
	}
}
