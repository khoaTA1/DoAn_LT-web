package PKG.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

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
import PKG.entity.Cooker;
import PKG.entity.Fan;
import PKG.entity.Fridge;
import PKG.entity.Item;
import PKG.entity.Stove;
import PKG.entity.Tivi;
import PKG.entity.Vacuum;
import PKG.entity.Washer;
import PKG.entity.airCon;
import PKG.service.CategoryService;
import PKG.service.ItemService;

@Controller
@RequestMapping("/")
public class ItemController {
	@Autowired
	ItemService itemserv;
	
	@Autowired
	CategoryService cateserv;
	
	private Map<Integer, BiFunction<Map<String, Object>, String, ? extends Item>> addMapCon;
	
	private Map<Integer, BiFunction<Map<String, Object>, String, ? extends Item>> updateMapCon;
	
	public ItemController() {
		/*
		cateid = 1 => Máy điều hòa
		cateid = 2 => Nồi cơm điện
		cateid = 3 => Quạt
		cateid = 4 => Tủ lạnh
		cateid = 5 => Bếp
		cateid = 6 => Tivi
		cateid = 7 => Máy hút bụi
		cateid = 8 => Máy giặt
		*/
		addMapCon = new HashMap<>();
        addMapCon.put(1, (data, image) -> this.addAC(data, image));
        addMapCon.put(2, (data, image) -> this.addCooker(data, image));
        addMapCon.put(3, (data, image) -> this.addFan(data, image));
        addMapCon.put(4, (data, image) -> this.addFridge(data, image));
        addMapCon.put(5, (data, image) -> this.addStove(data, image));
        addMapCon.put(6, (data, image) -> this.addTV(data, image));
        addMapCon.put(7, (data, image) -> this.addVC(data, image));
        addMapCon.put(8, (data, image) -> this.addWasher(data, image));	
        
        updateMapCon = new HashMap<>();
        updateMapCon.put(1, (data, image) -> this.updateAC(data, image));
        updateMapCon.put(2, (data, image) -> this.updateCooker(data, image));
        updateMapCon.put(3, (data, image) -> this.updateFan(data, image));
        updateMapCon.put(4, (data, image) -> this.updateFridge(data, image));
        updateMapCon.put(5, (data, image) -> this.updateStove(data, image));
        updateMapCon.put(6, (data, image) -> this.updateTV(data, image));
        updateMapCon.put(7, (data, image) -> this.updateVC(data, image));
        updateMapCon.put(8, (data, image) -> this.updateWasher(data, image));	
	}
	
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

				Map<String, Object> dataMap = oMapper.readValue(dataChain, new TypeReference<>() {});
				
				int cateId = (int) dataMap.get("cateId");
				System.out.print(cateId);

				// kiểm tra xem nếu có phải là update hay không
				// nếu phải thì duyệt rồi xóa ảnh cũ
				if (isUpdate) {
					Object itemId = dataMap.get("itemId");
					Item oldItem = itemserv.findById(((Number) itemId).longValue() , cateId).orElse(null);

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
				imagePath = "\\categoryIcons\\" + imageName;

				image.transferTo(new File(DirectoryPath.dir + imagePath));

				// nếu là thêm item
				if (!isUpdate) {
					// tạo và thêm các đặc tính cho sản phẩm theo tên phân loại
					Item item = addMapCon.get(cateId).apply(dataMap, imagePath);

					// gọi service và thêm vào cơ sở dữ liệu
					if (!itemserv.addItem(cateId, item)) {
						model.addAttribute("msg", "Lỗi hệ thống!");
						return "tb";
					}
					
					model.addAttribute("msg", "Thêm sản phẩm thành công");
					model.addAttribute("item", item);
					return "ok";
				} else {	// nếu là update item
					// tìm item dựa trên itemId sẽ được truyền thông qua chuỗi data rồi cập nhật item đó
					Item item = updateMapCon.get(cateId).apply(dataMap, imagePath);
					
					if (!itemserv.addItem(cateId, item)) {
						model.addAttribute("msg", "Lỗi hệ thống!");
						return "tb";
					}
					
					model.addAttribute("msg", "Thêm sản phẩm thành công");
					model.addAttribute("item", item);
					return "ok";
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("msg", "Lỗi hệ thống!");
				return "tb";
			}
		}
	}

	private Item addCommonAttr(Item item, Map<String, Object> data, String imagePath) {

		item.setBrand((String) data.get("brand"));
		item.setCategoryId((int) data.get("cateId"));
		item.setDiscount((double) data.get("discount"));
		item.setName((String) data.get("name"));
		item.setOrigin((String) data.get("origin"));
		item.setPrice((double) data.get("price"));
		item.setImage(imagePath);

		return item;
	}
	
	// =================== các hàm thêm đặc trưng riêng của các sản phẩm =============================
	private airCon addAC(Map<String, Object> data, String imagePath) {
		
		airCon newAC = new airCon();
		
		// thêm các đặc tính chung
		newAC = (airCon) addCommonAttr(newAC, data, imagePath);
		
		// thêm các đặc tính riêng của sản phẩm máy điều hòa
		Double cslamlanh = (double) data.get("cslamlanh");
		newAC.setCongSuatLamLanh(cslamlanh.floatValue());
		
		if (data.get("isInverter").equals("true")) {
			newAC.setInverter(true);
		} else {
			newAC.setInverter(false);
		}
		
		newAC.setLoaiMay((String) data.get("loaimay"));
		
		return newAC;
	}
	
	private Cooker addCooker(Map<String, Object> data, String imagePath) {

		Cooker newCooker = new Cooker();

		// thêm các đặc tính chung
		newCooker = (Cooker) addCommonAttr(newCooker, data, imagePath);

		// thêm các đặc tính riêng của sản phẩm nồi cơm điện
		Double congsuat = (double) data.get("congsuat");
		newCooker.setCongSuat(congsuat.floatValue());

		Double dungtich = (double) data.get("dungtich");
		newCooker.setDungTich(dungtich.floatValue());

		return newCooker;
	}
	
	private Fan addFan(Map<String, Object> data, String imagePath) {

		Fan newFan = new Fan();

		// thêm các đặc tính chung
		newFan = (Fan) addCommonAttr(newFan, data, imagePath);
		
		// thêm các đặc tính riêng của sản phẩm quạt gió
		newFan.setCanhQuat((String) data.get("canhquat"));
		
		newFan.setCongSuatMucGio((String) data.get("congsuatmucgio"));
		
		newFan.setLoaiQuat((String) data.get("loaiquat"));

		return newFan;
	}

	private Fridge addFridge(Map<String, Object> data, String imagePath) {

		Fridge newFridge = new Fridge();

		// thêm các đặc tính chung
		newFridge = (Fridge) addCommonAttr(newFridge, data, imagePath);

		// thêm các đặc tính riêng của sản phẩm máy tủ lạnh
		Double congsuat = (double) data.get("congsuat");
		newFridge.setCongSuat(congsuat.floatValue());
		
		Double dungtich = (double) data.get("dungtich");
		newFridge.setDungTich(dungtich.floatValue());
		
		newFridge.setKieuTu((String) data.get("kieutu"));

		return newFridge;
	}

	private Stove addStove(Map<String, Object> data, String imagePath) {

		Stove newStove = new Stove();

		// thêm các đặc tính chung
		newStove = (Stove) addCommonAttr(newStove, data, imagePath);

		// thêm các đặc tính riêng của sản phẩm bếp
		newStove.setKichThuocVungNau((String) data.get("kichthuocvungnau"));

		newStove.setLoaiBep((String) data.get("loaibep"));
		
		Double tongcongsuat = (double) data.get("tongcongsuat");
		newStove.setTongCongSuat(tongcongsuat.floatValue());

		return newStove;
	}

	private Tivi addTV(Map<String, Object> data, String imagePath) {

		Tivi newTV = new Tivi();

		// thêm các đặc tính chung
		newTV = (Tivi) addCommonAttr(newTV, data, imagePath);

		// thêm các đặc tính riêng của sản phẩm tivi
		newTV.setDoPhanGiai((String) data.get("dophangiai"));
		
		newTV.setManHinh((String) data.get("manhinh"));
		
		Double tansoquet = (double) data.get("tansoquet");
		newTV.setTanSoQuet(tansoquet.floatValue());
		
		return newTV;
	}

	private Vacuum addVC(Map<String, Object> data, String imagePath) {

		Vacuum newVC = new Vacuum();

		// thêm các đặc tính chung
		newVC = (Vacuum) addCommonAttr(newVC, data, imagePath);

		// thêm các đặc tính riêng của sản phẩm máy hút bụi
		Double congsuathut = (double) data.get("congsuathut");
		newVC.setCongSuatHut(congsuathut.floatValue());

		Double dooncaonhat = (double) data.get("dooncaonhat");
		newVC.setDoOnCaoNhat(dooncaonhat.floatValue());
		
		newVC.setLoaiMay((String) data.get("loaimay"));

		return newVC;
	}
	
	private Washer addWasher(Map<String, Object> data, String imagePath) {

		Washer newWasher = new Washer();

		// thêm các đặc tính chung
		newWasher = (Washer) addCommonAttr(newWasher, data, imagePath);

		// thêm các đặc tính riêng của sản phẩm máy giặt
		newWasher.setHieuSuat((String) data.get("hieusuat"));

		Double khoiluonggiat = (double) data.get("khoiluonggiat");
		newWasher.setKhoiLuongGiat(khoiluonggiat.floatValue());
		
		newWasher.setLoaiMayGiat((String) data.get("loaimaygiat"));

		return newWasher;
	}
	// ==============================================================================================
	
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
	
	// =================== các hàm cập nhật đặc trưng riêng của các sản phẩm =============================
	private airCon updateAC(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		airCon currentItem = (airCon) itemserv.findById(((Number) itemId).longValue(), (int) data.get("cateId"))
				.orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (airCon) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm máy điều hòa
			Double cslamlanh = (double) data.get("cslamlanh");
			currentItem.setCongSuatLamLanh(cslamlanh.floatValue());
			
			if (data.get("isInverter").equals("true")) {
				currentItem.setInverter(true);
			} else {
				currentItem.setInverter(false);
			}
			
			currentItem.setLoaiMay((String) data.get("loaimay"));
			return currentItem;
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return null;
		}
	}
		
	private Cooker updateCooker(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Cooker currentItem = (Cooker) itemserv.findById(((Number) itemId).longValue(), (int) data.get("cateId"))
				.orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Cooker) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm nồi cơm điện
			Double congsuat = (double) data.get("congsuat");
			currentItem.setCongSuat(congsuat.floatValue());

			Double dungtich = (double) data.get("dungtich");
			currentItem.setDungTich(dungtich.floatValue());
			return currentItem;
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return null;
		}
	}
	
	private Fan updateFan(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Fan currentItem = (Fan) itemserv.findById(((Number) itemId).longValue(), (int) data.get("cateId"))
				.orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Fan) addCommonAttr(currentItem, data, imagePath);
			
			// thêm các đặc tính riêng của sản phẩm quạt gió
			currentItem.setCanhQuat((String) data.get("canhquat"));
			
			currentItem.setCongSuatMucGio((String) data.get("congsuatmucgio"));
			
			currentItem.setLoaiQuat((String) data.get("loaiquat"));
			return currentItem;
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return null;
		}
	}
	
	private Fridge updateFridge(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Fridge currentItem = (Fridge) itemserv.findById(((Number) itemId).longValue(), (int) data.get("cateId"))
				.orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Fridge) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm máy tủ lạnh
			Double congsuat = (double) data.get("congsuat");
			currentItem.setCongSuat(congsuat.floatValue());
			
			Double dungtich = (double) data.get("dungtich");
			currentItem.setDungTich(dungtich.floatValue());
			
			currentItem.setKieuTu((String) data.get("kieutu"));
			return currentItem;
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return null;
		}
	}
	
	private Stove updateStove(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Stove currentItem = (Stove) itemserv.findById(((Number) itemId).longValue(), (int) data.get("cateId"))
				.orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Stove) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm bếp
			currentItem.setKichThuocVungNau((String) data.get("kichthuocvungnau"));

			currentItem.setLoaiBep((String) data.get("loaibep"));
			
			Double tongcongsuat = (double) data.get("tongcongsuat");
			currentItem.setTongCongSuat(tongcongsuat.floatValue());
			return currentItem;
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return null;
		}
	}
	
	private Tivi updateTV(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Tivi currentItem = (Tivi) itemserv.findById(((Number) itemId).longValue(), (int) data.get("cateId"))
				.orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Tivi) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm tivi
			currentItem.setDoPhanGiai((String) data.get("dophangiai"));
			
			currentItem.setManHinh((String) data.get("manhinh"));
			
			Double tansoquet = (double) data.get("tansoquet");
			currentItem.setTanSoQuet(tansoquet.floatValue());
			return currentItem;
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return null;
		}
	}
	
	private Vacuum updateVC(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Vacuum currentItem = (Vacuum) itemserv.findById(((Number) itemId).longValue(), (int) data.get("cateId"))
				.orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Vacuum) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm máy hút bụi
			Double congsuathut = (double) data.get("congsuathut");
			currentItem.setCongSuatHut(congsuathut.floatValue());

			Double dooncaonhat = (double) data.get("dooncaonhat");
			currentItem.setDoOnCaoNhat(dooncaonhat.floatValue());
			
			currentItem.setLoaiMay((String) data.get("loaimay"));
			return currentItem;
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return null;
		}
	}
	
	private Washer updateWasher(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Washer currentItem = (Washer) itemserv.findById(((Number) itemId).longValue(), (int) data.get("cateId"))
				.orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Washer) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm máy giặt
			currentItem.setHieuSuat((String) data.get("hieusuat"));

			Double khoiluonggiat = (double) data.get("khoiluonggiat");
			currentItem.setKhoiLuongGiat(khoiluonggiat.floatValue());
			
			currentItem.setLoaiMayGiat((String) data.get("loaimaygiat"));
			return currentItem;
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return null;
		}
	}
	// ==============================================================================================
}