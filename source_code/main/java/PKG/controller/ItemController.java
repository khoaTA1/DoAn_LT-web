package PKG.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import PKG.entity.Category;
import PKG.entity.Item;
import PKG.service.CategoryService;
import PKG.service.ItemService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/item")
public class ItemController {

	private static final String defaultResultPerPage = "9";

	@Autowired
	ItemService itemserv;

	@Autowired
	CategoryService cateserv;

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
		
		Category cate = cateserv.findById(returnItem.get().getCategoryId()).get();

		model.addAttribute("catename", cate.getCategoryName());
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
		model.addAttribute("isAsc", asc);

		return "homepage/items_list";
	}

	// duyệt item có phân trang theo itemid và sắp xếp theo giá
	@GetMapping("getbycateid/{cateid}")
	public String getAllandSortbyName(ModelMap model, @PathVariable int cateid,
			@RequestParam(name = "page", defaultValue = "1") int currentPage,
			@RequestParam(name = "resultPerPage", defaultValue = defaultResultPerPage) int rsPerPage,
			@RequestParam(name = "isAsc", defaultValue = "true") boolean asc) {

		Page<? extends Item> itemPage = itemserv.findItemByIdAndSortByPrice(cateid, currentPage - 1, rsPerPage, asc);

		model.addAttribute("listItem", itemPage.getContent());
		model.addAttribute("selectedCategoryId", cateid);
		model.addAttribute("totalPages", itemPage.getTotalPages());
		model.addAttribute("page", currentPage);
		model.addAttribute("isAsc", asc);

		return "homepage/items_list";
	}

	@GetMapping("search-bar")
	public String searchBar(ModelMap model, @RequestParam("keyword") String keyword,
			@RequestParam(name = "page", defaultValue = "1") int currentPage,
			@RequestParam(name = "resultPerPage", defaultValue = defaultResultPerPage) int rsPerPage,
			@RequestParam(name = "isAsc", defaultValue = "true") boolean asc) {

		// tìm danh sách các category có tên thỏa mãn từ khóa
		List<Category> categories = cateserv.findByCategoryNameContainingIgnoreCase(keyword);

		if (categories.isEmpty()) {
			// Nếu không tìm thấy category thì trả về danh sách rỗng hoặc thông báo
			System.out.println(">>>>>>>>>>>> cate trống!");
			
			model.addAttribute("msg", "Không tìm thấy danh mục phù hợp với từ khóa: " + keyword);
			return "homepage/items_list";
		}

		// tìm và nối danh sách các item tương ứng
		List<Item> listItem = new ArrayList<>();

		for (Category cate : categories) {
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

		Page<Item> returnPage = new PageImpl<>(subListItem, PageRequest.of(currentPage - 1, rsPerPage), listItem.size());

		model.addAttribute("listItem", returnPage.getContent());
		model.addAttribute("totalPages", returnPage.getTotalPages());
		model.addAttribute("page", currentPage);
		model.addAttribute("isAsc", asc);

		return "homepage/items_list";
	}
}