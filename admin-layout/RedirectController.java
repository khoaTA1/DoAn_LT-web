package PKG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import PKG.entity.User;
import PKG.service.UserService;


@Controller
@RequestMapping("/")
public class RedirectController {

	@Autowired
	UserService userserv;
	
	@GetMapping("redirect/test")
	public String rtest() {
		return "test";
	}
	
	@GetMapping({"", "homepage"})
	public String rwebpage() {
		
		return "redirect:/item/getall";
	}

	@GetMapping("redirect/login")
	public String rlogin() {
		
		return "login";
	}
	
	@GetMapping("redirect/register")
	public String rregister() {
		
		return "register";
	}
	
	@GetMapping("redirect/gencode")
	public String rgencode() {
		return "otpcodeactions/gencodepage";
	}
	
	@GetMapping("redirect/entercode")
	public String rentercode() {
		return "otpcodeactions/entercodepage";
	}
	
	@GetMapping("redirect/changepassw")
	public String rchangepassw() {
		return "changepassw";
	}
	
	@GetMapping("admin/adminpage")
	public String radminpage() {
		return "admin/profilepage_admin";
	}
	
	@GetMapping("user/profile")
	public String rUserProfile(ModelMap model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userserv.findByUserName(username).orElse(null);
		
		model.addAttribute("account", user);
		return "user/profilepage";
	}
	
	@GetMapping("redirect/payment")
	public String rPayment() {
		return "payment";
	}
	
	@GetMapping("redirect/qr_scan")
	public String rQrScan() {
		return "qr_scan";
	}
}
