package PKG.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import PKG.constant.DirectoryPath;
import PKG.entity.Category;
import PKG.service.CategoryService;

@Controller
@RequestMapping("category")
public class CategoryController {
	@Autowired
	CategoryService cateserv;
	
	// duyệt có phân trang
	@PostMapping("list-pagination")
	public String listCate(ModelMap model, @RequestParam(name = "page", defaultValue = "1") int currentPage,
			@RequestParam(name = "resultPerPage", defaultValue = "10") int rsPerPage) {
		
		Pageable pageable = PageRequest.of(currentPage - 1, rsPerPage);
		Page<Category> pageCategory = cateserv.findAll(pageable);
		
		model.addAttribute("listCate", pageCategory.getContent());
		model.addAttribute("totalPage", pageCategory.getTotalPages());
		model.addAttribute("page", currentPage);
		
		return "";
	}
	
	// cập nhật 1 category
	@PostMapping("update")
	public String updateCate(ModelMap model, @RequestParam("catename") String newName,
			@RequestParam("cateid") int cateId,
			@RequestParam(name = "icon") MultipartFile newIcon) {

		Category cate = cateserv.findById(cateId).orElse(null);
		
		if (cate == null) {
			model.addAttribute("msg", "Category không tồn tại!");
			return "editcategory";
		}

		cate.setCategoryName(newName);

		try {
			if (newIcon.getSize() > 0) {
				// Xóa ảnh cũ
				if (cate.getIcon() != null) {
					final String oldIconName = cate.getIcon();

					File oldIcon = new File(DirectoryPath.dir + "\\" + oldIconName);

					if (oldIcon.exists()) {
						oldIcon.delete();
					}
				}

				// Lưu ảnh mới
				String originalFileName = newIcon.getOriginalFilename();

				int index = originalFileName.lastIndexOf(".");

				String ext = originalFileName.substring(index + 1);
				String fileName = System.currentTimeMillis() + "." + ext;

				String filePath = DirectoryPath.dir + "\\categoryIcons\\" + fileName;
				
				cate.setIcon("categoryIcons/" + fileName);
				
				cateserv.save(cate);
				
				newIcon.transferTo(new File(filePath));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
}
