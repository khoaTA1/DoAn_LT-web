package PKG.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@PostMapping("add")
	public String addCate(ModelMap model, @RequestParam("catename") String newName,
			@RequestParam(name = "icon") MultipartFile newIcon) {

		Category cate = new Category();
		
		cate.setCategoryName(newName);

		try {
			if (newIcon.getSize() > 0) {

				// Lưu ảnh mới
				String originalFileName = newIcon.getOriginalFilename();

				int index = originalFileName.lastIndexOf(".");

				String ext = originalFileName.substring(index + 1);
				String fileName = System.currentTimeMillis() + "." + ext;

				String filePath = DirectoryPath.dir + "\\categoryIcons\\" + fileName;
				newIcon.transferTo(new File(filePath));
				cate.setIcon("categoryIcons/" + fileName);
				
				cateserv.save(cate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

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
				newIcon.transferTo(new File(filePath));
				cate.setIcon("categoryIcons/" + fileName);
				
				cateserv.save(cate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
}
