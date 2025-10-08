package PKG.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RedirectController {

	@GetMapping("redirect/login")
	public String rlogin() {
		
		return "login";
	}
	
	@GetMapping("redirect/register")
	public String rregister() {
		
		return "register";
	}
	
	@GetMapping("redirect/forgetPassw")
	public String rforgetPassw() {
		return "forgetpassw";
	}
}
