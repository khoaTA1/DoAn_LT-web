package PKG.controller;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import PKG.constant.DirectoryPath;
import PKG.entity.Item;
import PKG.service.CategoryService;
import PKG.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	ItemService itemserv;
	
	@Autowired
	CategoryService cateserv;
	
	// thêm item
	@PostMapping("addorupdateitem")
	public String addItem(ModelMap model, 
			@RequestParam("data") String dataChain,
			@RequestParam("image") MultipartFile image, 
			@RequestParam("isupdate") boolean isUpdate)
			throws JsonProcessingException {

		String imagePath = "";
		if (image == null || image.isEmpty()) {
			model.addAttribute("msg", "Vui lòng chọn một tệp ảnh!");
			return "tb";
		} else {
			try {
				// chuyển đổi dữ liệu chuỗi json sang map
				ObjectMapper oMapper = new ObjectMapper();

				Map<String, Object> dataMap = oMapper.readValue(dataChain, new TypeReference<>() {
				});

				int cateId = (int) dataMap.get("cateId");

				// kiểm tra xem nếu có phải là update hay không
				// nếu phải thì duyệt rồi xóa ảnh cũ
				if (isUpdate) {
					Object itemId = dataMap.get("itemId");
					Item oldItem = itemserv.findById(((Number) itemId).longValue(), cateId).orElse(null);

					if (oldItem != null) {
						String oldImageName = oldItem.getImage();

						File oldImage = new File(DirectoryPath.dir + "\\" + oldImageName);

						if (oldImage.exists()) {
							oldImage.delete();
						}

					} else {
						model.addAttribute("msg", "Lỗi hệ thống!");

						// debug
						System.out.print("Không tìm thấy item cũ");
						return "tb";
					}
				}

				String originalFileName = image.getOriginalFilename();

				int index = originalFileName.lastIndexOf(".");
				String ext = originalFileName.substring(index + 1);

				// đặt tên file ngẫu nhiên bằng ngày giờ lưu file để tránh trùng
				String imageName = System.currentTimeMillis() + "." + ext;

				// tạo đường dẫn lưu vào entity (String)
				imagePath = "\\itemImages\\" + imageName;

				Item item = itemserv.saveItem(cateId, dataMap, imagePath, isUpdate).orElse(null);
				
				if (item == null) {
					model.addAttribute("msg", "Lỗi hệ thống!");
					return "tb";
				}
				
				image.transferTo(new File(DirectoryPath.dir + imagePath));
				model.addAttribute("msg", "Thêm sản phẩm thành công");
				model.addAttribute("item", item);
				return "ok";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "Lỗi hệ thống!");
				return "tb";
			}
		}
	}
	
	// duyệt item có phân trang và sắp xêp
	@GetMapping("{itemid}")
	public String getAllandSortbyName(ModelMap model, @PathVariable int itemid,
			@RequestParam(name = "page", defaultValue = "1") int currentPage,
			@RequestParam(name = "resultPerPage", defaultValue = "10") int rsPerPage,
			@RequestParam(name = "isAsc", defaultValue = "true") boolean asc) {

		Page<? extends Item> itemPage = itemserv.findAndSortItemById(itemid, currentPage, rsPerPage, asc);

		model.addAttribute("listItem", itemPage.getContent());
		model.addAttribute("totalPages", itemPage.getTotalPages());
		model.addAttribute("page", currentPage);

		return "";
	}
	
	// xóa item
	@PostMapping("delete")
	public String deleteItem(ModelMap model, @RequestParam("itemid") Long itemId) {
		itemserv.deleteById(itemId);
		
		return "";
	}
}