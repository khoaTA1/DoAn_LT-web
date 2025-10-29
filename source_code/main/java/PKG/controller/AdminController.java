package PKG.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import PKG.constant.DirectoryPath;
import PKG.entity.*;
import PKG.model.inputEditUser;
import PKG.service.*;

/**
 * 🧩 AdminController — Gộp tất cả các chức năng quản trị:
 *  - Hồ sơ admin
 *  - Quản lý người dùng
 *  - Quản lý sản phẩm (Item)
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    // ==================== SERVICE ====================
    @Autowired private UserService userService;
    @Autowired private CategoryService categoryService;
    @Autowired private ItemService itemService;
    @Autowired private CategoryService cateserv;
    @Autowired private PasswdService passserv;
    @Autowired private AccountAuthService AAS;
    
    // ==========================================================
    // 1>> QUẢN LÝ NGƯỜI DÙNG (/admin/user)
    // ==========================================================
    @GetMapping("/user")
    public String listUsers(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<User> users;

        if (keyword != null && !keyword.trim().isEmpty()) {
            users = userService.findByUserNameContaining(keyword);
            users.addAll(userService.findByEmail(keyword).stream().toList());
            model.addAttribute("keyword", keyword);
        } else {
            users = userService.findAll();
        }

        model.addAttribute("userList", users);
        return "admin/user_admin";
    }

    @GetMapping("/user/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/user_add";
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        if (userService.existsByUserName(user.getUserName())) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại!");
            return "admin/user_add";
        }
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "Email đã tồn tại!");
            return "admin/user_add";
        }
        userService.save(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/user/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        Optional<User> u = userService.findById(id);
        if (u.isEmpty()) return "redirect:/admin/user";
        model.addAttribute("user", u.get());
        return "admin/user_edit";
    }

    @PostMapping("/user/edit")
    public String updateUser(@Validated @ModelAttribute("user") inputEditUser inputUser, BindingResult error) {
    	
    	if (error.hasErrors()) {
    		System.out.println("binding result");
            return "admin/user_edit";
        }
    	
    	User user = userService.findById(inputUser.getId()).orElse(null);
    	
    	if (user == null) {
    		System.out.println("user null");
    		return "redirect:/admin/user";
    	}
    	
    	// cập nhật mật khẩu
    	AAS.changePass(user.getUserName(), inputUser.getNewPassw());
    	
    	// cập nhật thông tin user
    	user.setUserName(inputUser.getUserName());
    	user.setEmail(inputUser.getEmail());
    	user.setFullName(inputUser.getFullName());
    	user.setEmailVerify(inputUser.isEmailVerify());
    	user.setRole(inputUser.getRole());
    	user.setPhone(inputUser.getPhone());
    	
    	userService.save(user);
        System.out.println("Cập nhật user từ trang admin");
        return "redirect:/admin/user";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/user";
    }



    // ==========================================================
    // 2>> QUẢN LÝ SẢN PHẨM (/admin/item)
    // ==========================================================
	private static final Map<Integer, Supplier<? extends Item>> map = Map.of(1, () -> new airCon(), 
																			 2, () -> new Cooker(),
																			 3, () -> new Fan(),
																			 4, () -> new Fridge(),
																			 5, () -> new Stove(),
																			 6, () -> new Tivi(),
																			 7, () -> new Vacuum(),
																			 8, () -> new Washer());
    
    @GetMapping("/item/list")
    public String listItems(Model model, @RequestParam(name = "cateId", required = false) Integer cateId) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        List<Item> items = (cateId != null)
        	    ? (List<Item>) itemService.findAllById(cateId)
        	    : itemService.findAll();

        model.addAttribute("listItem", items);
        model.addAttribute("selectedCateId", cateId);

        return "admin/item_list";
    }

    @GetMapping("/item/add")
    public String addItemForm(ModelMap model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product_add";
    }
    
    @GetMapping("/item/adddetail")
    public String addItemFormDetail(ModelMap model, @RequestParam("cateid") int cateId) {
    	Category cate = cateserv.findById(cateId).orElse(null);
    	
    	if (cate == null) {
    		model.addAttribute("msg", "Không tìm thấy danh mục!");
        	return "admin/product_add";
    	}
    	
    	Item item = map.get(cateId).get();
    	
    	// Danh sách lưu các trường riêng của item
        List<String> privateFields = new ArrayList<>();

        // Lấy danh sách các trường riêng của item
        Field[] fields = item.getClass().getDeclaredFields();

        // Duyệt qua các trường và thêm vào danh sách
        for (Field field : fields) {
            // Kiểm tra xem trường có phải là trường riêng của lớp con không
        	privateFields.add(field.getName());  // Thêm tên trường vào danh sách
        }
    	
        model.addAttribute("item", item);
        model.addAttribute("privateFields", privateFields);
        model.addAttribute("cate", cate);
        return "admin/product_add_detail";
    }

    @GetMapping("/item/edit/{id}/{cateId}")
    public String editItemForm(@PathVariable Long id, @PathVariable int cateId, ModelMap model) {
        Item item = (Item) itemService.findById(id, cateId).orElse(null);
        if (item == null) return "redirect:/admin/item/list";

        // Danh sách lưu các trường riêng của item
        List<String> privateFields = new ArrayList<>();

        // Lấy danh sách các trường riêng của item
        Field[] fields = item.getClass().getDeclaredFields();

        // Duyệt qua các trường và thêm vào danh sách
        for (Field field : fields) {
            // Kiểm tra xem trường có phải là trường riêng của lớp con không
        	privateFields.add(field.getName());  // Thêm tên trường vào danh sách
        }
        
        model.addAttribute("item", item);
        model.addAttribute("privateFields", privateFields);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product_edit";
    }

	@PostMapping("/item/addoredit")
	public ResponseEntity<?> addOrEditItem(ModelMap model, @RequestParam("data") String dataChain,
			@RequestParam("image") MultipartFile image, @RequestParam("isupdate") boolean isUpdate)
			throws JsonProcessingException {

		Map<String, Object> resp = new HashMap<>();
		
		String imagePath = "";
		try {
			Item item;
			// chuyển đổi dữ liệu chuỗi json sang map
			ObjectMapper oMapper = new ObjectMapper();

			Map<String, Object> dataMap = oMapper.readValue(dataChain, new TypeReference<>() {
			});

			int cateId = Integer.parseInt((String) dataMap.get("cateId"));

			// kiểm tra xem nếu có phải là update hay không
			// nếu phải thì duyệt rồi xóa ảnh cũ
			if (isUpdate) {
				Object itemId = Integer.parseInt((String) dataMap.get("itemId"));
				Item oldItem = itemService.findById(((Number) itemId).longValue(), cateId).orElse(null);
				
				if (image == null || image.isEmpty()) {
					imagePath = oldItem.getImage();
				} else {
					if (oldItem != null) {
						String oldImageName = oldItem.getImage();

						File oldImage = new File(DirectoryPath.dir + "\\" + oldImageName);

						if (oldImage.exists()) {
							oldImage.delete();
						}

					} else {
						resp.put("msg", "Lỗi hệ thống!");

						// debug
						System.out.print("Không tìm thấy item cũ");
						return ResponseEntity.status(500).body(resp);
					}
					
					imagePath = saveImage(image);
				}

				item = itemService.saveItem(cateId, dataMap, imagePath, isUpdate).orElse(null);
			} else {
				imagePath = saveImage(image);

				item = itemService.saveItem(cateId, dataMap, imagePath, isUpdate).orElse(null);
			}

			if (item == null) {
				resp.put("msg", "Lỗi hệ thống!");
				return ResponseEntity.status(500).body(resp);
			}

			resp.put("msg", "Thêm/cập nhật sản phẩm thành công");
			model.addAttribute("item", item);
			return ResponseEntity.ok(resp);
		} catch (Exception e) {
			e.printStackTrace();
			resp.put("msg", "Lỗi hệ thống!");
			return ResponseEntity.status(500).body(resp);
		}
	}

    @GetMapping("/item/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteById(id);
        return "redirect:/admin/item/list";
    }

    // =================== SAVE IMAGE ===================
    private String saveImage(MultipartFile image) throws IOException {
    	String originalFileName = image.getOriginalFilename();

		int index = originalFileName.lastIndexOf(".");
		String ext = originalFileName.substring(index + 1);

		// đặt tên file ngẫu nhiên bằng ngày giờ lưu file để tránh trùng
		String imageName = System.currentTimeMillis() + "." + ext;
		
		image.transferTo(new File(DirectoryPath.dir + "itemImages/" + imageName));

        return "itemImages/" + imageName;
    }
}
