package khoa.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import khoa.Constant.DirectoryPath;
import khoa.entity.Category;
import khoa.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin/category")
public class CategoryController {
	@Autowired
	CategoryService cateserv;

	@GetMapping("add")
	public String add(ModelMap model) {
		return "admin/category/add";
	}

	@RequestMapping("")
	public String list(ModelMap model) {
		// gọi hàm findAll() trong services
		List<Category> list = cateserv.findAll();

		// chuyển dữ liệu từ list lên biến categories
		model.addAttribute("categories", list);
		return "admin/category/list";
	}

	@GetMapping("delete/{categoryId}")
	public ModelAndView delet(ModelMap model, @PathVariable("categoryId") int categoryId) {
		cateserv.deleteById(categoryId);
		model.addAttribute("message", "Category is deleted!!!!");
		return new ModelAndView("redirect:/admin/category/searchpaginated", model);
	}

	@RequestMapping("search")
	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
		List<Category> list = null;

		// có nội dung truyền về không, name là tùy chọn khi required=false
		if (StringUtils.hasText(name)) {
			list = cateserv.findByCategoryNameContaining(name);
		} else {
			list = cateserv.findAll();
		}

		model.addAttribute("categories", list);
		return "admin/category/search";
	}

	@PostMapping("saveOrUpdate")
	public String saveupdate(ModelMap model, @RequestParam(name = "categoryName", required = true) String name,
			@RequestParam(name = "icon") MultipartFile file) {
		Category category = new Category();
		category.setCategoryName(name);

		if (file.isEmpty()) {
			model.addAttribute("msg", "Vui lòng chọn một file ảnh!");
		} else {
			try {
				String originalFileName = file.getOriginalFilename();
				
				int index = originalFileName.lastIndexOf(".");
				
				String ext = originalFileName.substring(index + 1);
				String fileName = System.currentTimeMillis() + "." + ext;
				
				String filePath = DirectoryPath.dir + "//" + fileName;
				file.transferTo(new File(filePath));
				category.setImages("categoryIcons/" + fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		cateserv.save(category);

		return "admin/home";
	}
}
