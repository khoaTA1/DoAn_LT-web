package PKG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import PKG.entity.User;
import PKG.service.ItemService;
import PKG.service.UserService;


@Controller
@RequestMapping("/")
public class RedirectController {

	@Autowired
	UserService userserv;
	
	@Autowired
	ItemService itemserv;
	
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
	
	@GetMapping("user/profile")
	public String rUserProfile(ModelMap model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userserv.findByUserName(username).orElse(null);
		
		model.addAttribute("account", user);
		return "user/profilepage";
	}
	
	@PostMapping("redirect/payment/")
	public String rPayment(ModelMap model, @RequestParam("priceAfterDiscount") double price) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = userserv.findByUserName(username).orElse(null);
		System.out.println(user.getUserName());
		long userid = user.getId();
		
		model.addAttribute("userid", userid);
		model.addAttribute("price", price);
		System.out.println(userid);
		return "user/payment";
	}
	
	@GetMapping("user/chatroom")
	public String rChatRoom() {
		return "user/chatroom";
	}
}
