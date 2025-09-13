package khoa.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import khoa.Constant.DirectoryPath;
import khoa.entity.User;

@Controller
@RequestMapping("/")
public class UserController {
	@PostMapping("general/login")
	public String login(ModelMap model, @RequestParam(name = "username", required = true) String uname,
			@RequestParam(name = "password", required = true) String passw) {

		return "general/login";
	}

	@PostMapping("general/register")
	public String login(ModelMap model, @RequestParam(name = "username", required = true) String uname,
			@RequestParam(name = "fullname", required = true) String fname,
			@RequestParam(name = "email", required = true) String email,
			@RequestParam(name = "phone", required = true) String phone,
			@RequestParam(name = "password", required = true) String passw,
			@RequestParam(name = "avatar", required = false) MultipartFile file) {

		User user = new User();
		user.setUserName(uname);
		user.setFullName(fname);
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassWord(passw);

		if (file.isEmpty()) {
			model.addAttribute("msg", "Vui lòng chọn một file ảnh!");
		} else {
			try {
				String originalFileName = file.getOriginalFilename();

				int index = originalFileName.lastIndexOf(".");

				String ext = originalFileName.substring(index + 1);
				String fileName = System.currentTimeMillis() + "." + ext;

				String filePath = DirectoryPath.dir + "\\" + fileName;
				file.transferTo(new File(filePath));
				user.setAvatar("userAvatars/" + fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "general/register";
	}
}
