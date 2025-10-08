package PKG.controller;


import java.util.Collection;

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
	public String register(ModelMap model,
			@RequestParam(name = "username", required = true) String usern,
		    @RequestParam(name = "password", required = true) String passwd) {
		
		// Kiểm tra nếu tài khoản đã tồn tại
		if (userserv.existsByUserName(usern)) {
			model.addAttribute("msg", "Username đã tồn tại, vui lòng chọn username khác!");
			return "register";
		}
		
		if (!AAS.registerUser(usern, passwd)) {
			model.addAttribute("msg", "Lỗi hệ thống, vui lòng thử lại!");
			return "register";
		}
		
		model.addAttribute("msg", "Đăng ký thành công! bạn có thể đăng nhập ngay bây giờ");
		return "login";
	}
	
	@PostMapping("login")
	public String login(ModelMap model,
		@RequestParam(name = "username", required = true) String usern,
	    @RequestParam(name = "password", required = true) String passwd) {
		
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
			boolean isAdmin = authoritiesOfThisUser.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
			
			if (isAdmin) return "admin";
			
			return "home";
			
		} catch (Exception authE) {
			model.addAttribute("msg", "Sai tên đăng nhập hoặc mật khẩu");
			System.out.print(authE);
			return "login";
		}
	}
}
