package PKG.api;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PKG.constant.GlobalInjection;
import PKG.entity.Item;
import PKG.entity.User;
import PKG.model.inputUpdateUser;
import PKG.model.inputUpdateUser2;
import PKG.service.AccountAuthService;
import PKG.service.ItemService;
import PKG.service.UserService;

@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
	UserService userserv;
	
	@Autowired
	ItemService itemserv;
	
	@Autowired
	GlobalInjection globalmap;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	AccountAuthService AAS;

	@PostMapping("userupdate/{userid}")
	public ResponseEntity<?> updateAccount(ModelMap model, @PathVariable("userid") Long userId,
			@Validated @RequestBody inputUpdateUser inputUser) {

		try {
			User user = userserv.findById(userId).orElse(null);

			if (user == null) {
				return ResponseEntity.notFound().build();
			}

			// kiểm tra username hay email đã tồn tại chưa
			if (!user.getUserName().equals(inputUser.getUserName())
					&& userserv.existsByUserName(inputUser.getUserName())
					|| !user.getEmail().equals(inputUser.getEmail())
							&& userserv.existsByUserName(inputUser.getEmail())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}

			// cập nhật dữ liệu tài khoản
			user.setFullName(inputUser.getFullName());
			user.setPhone(inputUser.getPhone());
			user.setUserName(inputUser.getUserName());

			// kiểm tra xem nếu email có thay đổi
			// nếu có thay đổi email, phải xác thực email lại
			if (!user.getEmail().equals(inputUser.getEmail())) {
				user.setEmail(inputUser.getEmail());
				user.setEmailVerify(false);
			}

			userserv.save(user);

			// đồng bộ với SecurityContext
			UserDetails updatedUser = userDetailsService.loadUserByUsername(user.getUserName());
			Authentication newAuth = new UsernamePasswordAuthenticationToken(
			        updatedUser, updatedUser.getPassword(), updatedUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(newAuth);
			
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping("admin/userupdate/{userid}")
	public ResponseEntity<?> updateAccountByAdmin(ModelMap model, @PathVariable("userid") Long userId,
			@Validated @RequestBody inputUpdateUser2 inputUser) {

		try {
			User user = userserv.findById(userId).orElse(null);

			if (user == null) {
				return ResponseEntity.notFound().build();
			}

			// kiểm tra username hay email đã tồn tại chưa
			if (!user.getUserName().equals(inputUser.getUserName())
					&& userserv.existsByUserName(inputUser.getUserName())
					|| !user.getEmail().equals(inputUser.getEmail())
							&& userserv.existsByUserName(inputUser.getEmail())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}

			// cập nhật dữ liệu tài khoản
			if (!AAS.changePass(user.getUserName(), inputUser.getNewPassw())) throw new Exception("Lỗi hệ thống!");
			user.setFullName(inputUser.getFullName());
			user.setPhone(inputUser.getPhone());
			user.setUserName(inputUser.getUserName());
			user.setEmail(inputUser.getEmail());
			
			userserv.save(user);
			
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	// đọc chi tiết 1 item
	@GetMapping("getitem/{itemid}")
	public ResponseEntity<Map<String, Object>> getItemAPI(ModelMap model, @PathVariable("itemid") Long itemId) {

		Optional<Item> item = itemserv.findById(itemId);

		if (item.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Optional<? extends Item> particularItem = itemserv.findById(itemId, item.get().getCategoryId());

		if (particularItem.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Map<String, String> fieldNames = globalmap.getFieldNames();

		// các trường dữ liệu cần ẩn
		List<String> hiddenFields = Arrays.asList("id", "categoryId", "price", "discount", "description");

		Map<String, Object> result = new HashMap<>();

		// Lấy các trường của lớp cha (Item) và lớp con
		Field[] parentFields = particularItem.get().getClass().getSuperclass().getDeclaredFields();
		Field[] childFields = particularItem.get().getClass().getDeclaredFields();

		for (Field field : parentFields) {
			if (hiddenFields.contains(field.getName()))
				continue;
			try {
				field.setAccessible(true);
				Object value = field.get(particularItem.get());
				if (value != null) {
					result.put(fieldNames.getOrDefault(field.getName(), field.getName()), value);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		for (Field field : childFields) {
			try {
				field.setAccessible(true);
				Object value = field.get(particularItem.get());
				if (value != null) {
					result.put(fieldNames.getOrDefault(field.getName(), field.getName()), value);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}

		return ResponseEntity.ok(result);
	}
}
