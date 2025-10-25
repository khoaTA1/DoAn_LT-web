package PKG.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class RedirectController {
	
	@GetMapping("redirect/test")
	public String rtest() {
		return "test";
	}
	
	@GetMapping({"", "webpage"})
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
		return "forgotpassw/gencodepage";
	}
	
	@GetMapping("redirect/entercode")
	public String rentercode() {
		return "forgotpassw/entercodepage";
	}
	
	@GetMapping("redirect/adminpage")
	public String rforgetPassw() {
		return "adminpage";
	}
	
	@GetMapping("user/profile")
	public String rUserProfile() {
		
		return "/user/profile-page";
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
