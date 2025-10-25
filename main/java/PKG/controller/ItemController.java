package PKG.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import PKG.entity.Category;
import PKG.entity.Item;
import PKG.service.CategoryService;
import PKG.service.ItemService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/item")
public class ItemController {

	private static final String defaultResultPerPage = "5";

	@Autowired
	ItemService itemserv;

	@Autowired
	CategoryService cateserv;

	// thêm item
	@PostMapping("addorupdateitem")
	public String addItem(ModelMap model, @RequestParam("data") String dataChain,
			@RequestParam("image") MultipartFile image, @RequestParam("isupdate") boolean isUpdate)
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
				imagePath = "itemImages/" + imageName;

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

	// xóa item
	@PostMapping("delete")
	public String deleteItem(ModelMap model, @RequestParam("itemid") Long itemId) {
		itemserv.deleteById(itemId);

		return "";
	}

	// đọc chi tiết 1 item
	@GetMapping("getinfo/{itemid}")
	public String getItemById(ModelMap model, @PathVariable("itemid") Long itemId, HttpServletResponse resp) {

		Optional<Item> item = itemserv.findById(itemId);

		if (item.isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "homepage/item_detail";
		}

		Optional<? extends Item> returnItem = itemserv.findById(itemId, item.get().getCategoryId());

		if (returnItem.isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "homepage/item_detail";
		}

		model.addAttribute("itemdetail", returnItem.get());
		model.addAttribute("imagepath", returnItem.get().getImage());
		return "homepage/item_detail";
	}

	// ================ các chức năng duyệt item có phân trang và sắp xếp
	// ======================

	// duyệt tất cả item có phân trang (dùng cho khi mới load trang chủ)
	@GetMapping("getall")
	public String getAllItem(ModelMap model, @RequestParam(name = "page", defaultValue = "1") int currentPage,
			@RequestParam(name = "resultPerPage", defaultValue = defaultResultPerPage) int rsPerPage,
			@RequestParam(name = "isAsc", defaultValue = "true") boolean asc) {

		Page<Item> itemPage = itemserv.findAllandSortByPrice(currentPage - 1, rsPerPage, asc);

		model.addAttribute("listItem", itemPage.getContent());
		model.addAttribute("totalPages", itemPage.getTotalPages());
		model.addAttribute("page", currentPage);

		return "homepage/items_list";
	}

	// duyệt item có phân trang theo itemid và sắp xếp theo giá
	@GetMapping("sort-by-price/{cateid}")
	public String getAllandSortbyName(ModelMap model, @PathVariable int cateid,
			@RequestParam(name = "page", defaultValue = "1") int currentPage,
			@RequestParam(name = "resultPerPage", defaultValue = defaultResultPerPage) int rsPerPage,
			@RequestParam(name = "isAsc", defaultValue = "true") boolean asc) {

		Page<? extends Item> itemPage = itemserv.findItemByIdAndSortByPrice(cateid, currentPage - 1, rsPerPage, asc);

		model.addAttribute("listItem", itemPage.getContent());
		model.addAttribute("totalPages", itemPage.getTotalPages());
		model.addAttribute("page", currentPage);

		return "homepage/items_list";
	}

	@GetMapping("search-bar")
	public String searchBar(ModelMap model, @RequestParam("keyword") String keyword,
			@RequestParam(name = "page", defaultValue = "1") int currentPage,
			@RequestParam(name = "resultPerPage", defaultValue = defaultResultPerPage) int rsPerPage,
			@RequestParam(name = "isAsc", defaultValue = "true") boolean asc) {

		// tìm danh sách các category có tên thỏa mãn từ khóa
		List<Category> categories = cateserv.findByCategoryNameContainingIgnoreCase(keyword);

		if (categories == null) {
			// Nếu không tìm thấy category thì trả về danh sách rỗng hoặc thông báo
			model.addAttribute("msg", "Không tìm thấy danh mục phù hợp với từ khóa: " + keyword);
			return "homepage/items_list";
		}

		// tìm và nối danh sách các item tương ứng
		List<Item> listItem = new ArrayList<>();

		for (Category cate : categories) {
			// Giả sử bạn có service lấy Page<Item> theo categoryId
			List<? extends Item> itemsPage = itemserv.findAllById(cate.getId());
			listItem.addAll(itemsPage);
		}

		// sắp xếp lại danh sách theo giá tiền
		Comparator<Item> comparator = Comparator.comparing(Item::getPrice);
		if (!asc) {
			comparator = comparator.reversed();
		}
		listItem.sort(comparator);

		// phân trang kết quả
		int startIndex = (currentPage - 1) * rsPerPage;
		int endIndex = Math.min(startIndex + rsPerPage, listItem.size());
		List<Item> subListItem = listItem.subList(startIndex, endIndex);

		Page<Item> returnPage = new PageImpl<>(subListItem, PageRequest.of(currentPage - 1, rsPerPage),
				listItem.size());

		model.addAttribute("listItem", returnPage.getContent());
		model.addAttribute("totalPages", returnPage.getTotalPages());
		model.addAttribute("page", currentPage);

		return "homepage/items_list";
	}
}