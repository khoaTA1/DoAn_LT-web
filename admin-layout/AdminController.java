package PKG.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import PKG.constant.DirectoryPath;
import PKG.entity.*;
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
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private CategoryService categoryService;
    @Autowired private ItemService itemService;


    // ==========================================================
    // 🔹 1️⃣ HỒ SƠ ADMIN (/admin/profile)
    // ==========================================================
    @GetMapping("/profile")
    public String viewAdminProfile(@AuthenticationPrincipal(expression = "username") String username,
                                   ModelMap model) {
        User admin = userService.findByUserName(username).orElse(null);
        if (admin == null) {
            return "redirect:/user/login";
        }

        model.addAttribute("account", admin);
        return "admin/profilepage_admin";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateAdminInfo(@PathVariable("id") Long id, @RequestBody User updatedUser) {
        Map<String, String> errors = new HashMap<>();

        User existing = userService.findById(id).orElse(null);
        if (existing == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Không tìm thấy người dùng!"));
        }

        // Validate dữ liệu
        if (updatedUser.getUserName() == null || updatedUser.getUserName().isBlank()) {
            errors.put("userName", "Tên đăng nhập không được để trống");
        }
        if (updatedUser.getFullName() == null || updatedUser.getFullName().isBlank()) {
            errors.put("fullName", "Họ tên không được để trống");
        }
        if (updatedUser.getEmail() == null || updatedUser.getEmail().isBlank()) {
            errors.put("email", "Email không được để trống");
        }
        if (updatedUser.getPhone() == null || updatedUser.getPhone().isBlank()) {
            errors.put("phone", "Số điện thoại không được để trống");
        }
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        // Kiểm tra trùng username hoặc email
        for (User u : userService.findAll()) {
            if (!u.getId().equals(id)) {
                if (u.getUserName().equalsIgnoreCase(updatedUser.getUserName())) {
                    return ResponseEntity.status(409).body(Map.of("error", "Tên đăng nhập đã tồn tại"));
                }
                if (u.getEmail().equalsIgnoreCase(updatedUser.getEmail())) {
                    return ResponseEntity.status(409).body(Map.of("error", "Email đã được sử dụng"));
                }
            }
        }

        // Cập nhật thông tin
        existing.setUserName(updatedUser.getUserName());
        existing.setFullName(updatedUser.getFullName());
        existing.setEmail(updatedUser.getEmail());
        existing.setPhone(updatedUser.getPhone());
        userService.save(existing);

        // Cập nhật SecurityContext nếu là chính admin đó
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getName().equals(existing.getUserName())) {
            UserDetails updatedDetails = userDetailsService.loadUserByUsername(existing.getUserName());
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    updatedDetails, updatedDetails.getPassword(), updatedDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

        return ResponseEntity.ok(existing);
    }



    // ==========================================================
    // 🔹 2️⃣ QUẢN LÝ NGƯỜI DÙNG (/admin/user)
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
    public String updateUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/user";
    }



    // ==========================================================
    // 🔹 3️⃣ QUẢN LÝ SẢN PHẨM (/admin/item)
    // ==========================================================
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

    @PostMapping("/item/add")
    public String addItem(
            @RequestParam("cateId") int cateId,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("discount") double discount,
            @RequestParam("brand") String brand,
            @RequestParam("origin") String origin,
            @RequestParam("description") String description,
            @RequestParam("imageFile") MultipartFile imageFile
    ) throws IOException {

        String imagePath = saveImage(imageFile);

        Map<String, Object> formData = new HashMap<>();
        formData.put("cateId", cateId);
        formData.put("name", name);
        formData.put("price", price);
        formData.put("discount", discount);
        formData.put("brand", brand);
        formData.put("origin", origin);
        formData.put("description", description);

        itemService.saveItem(cateId, formData, imagePath, false);

        return "redirect:/admin/item/list?cateId=" + cateId;
    }

    @GetMapping("/item/edit/{id}/{cateId}")
    public String editItemForm(@PathVariable Long id, @PathVariable int cateId, ModelMap model) {
        Item item = (Item) itemService.findById(id, cateId).orElse(null);
        if (item == null) return "redirect:/admin/item/list";

        model.addAttribute("item", item);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product_edit";
    }

    @PostMapping("/item/edit")
    public String updateItem(
            @RequestParam("itemId") Long itemId,
            @RequestParam("cateId") int cateId,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("discount") double discount,
            @RequestParam("brand") String brand,
            @RequestParam("origin") String origin,
            @RequestParam("description") String description,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {

        String imagePath = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imagePath = saveImage(imageFile);
        }

        Map<String, Object> formData = new HashMap<>();
        formData.put("itemId", itemId);
        formData.put("cateId", cateId);
        formData.put("name", name);
        formData.put("price", price);
        formData.put("discount", discount);
        formData.put("brand", brand);
        formData.put("origin", origin);
        formData.put("description", description);

        itemService.saveItem(cateId, formData, imagePath, true);

        return "redirect:/admin/item/list?cateId=" + cateId;
    }

    @GetMapping("/item/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteById(id);
        return "redirect:/admin/item/list";
    }

    // =================== SAVE IMAGE ===================
    private String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) return null;

        File uploadDir = new File(DirectoryPath.dir + "/itemImages");
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir.getAbsolutePath(), fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "itemImages/" + fileName;
    }
}
