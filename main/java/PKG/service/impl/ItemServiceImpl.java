package PKG.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import PKG.entity.Cooker;
import PKG.entity.Fan;
import PKG.entity.Fridge;
import PKG.entity.Item;
import PKG.entity.Stove;
import PKG.entity.Tivi;
import PKG.entity.Vacuum;
import PKG.entity.Washer;
import PKG.entity.airCon;
import PKG.repository.item.AirConRepo;
import PKG.repository.item.CookerRepo;
import PKG.repository.item.FanRepo;
import PKG.repository.item.FridgeRepo;
import PKG.repository.item.ItemRepo;
import PKG.repository.item.StoveRepo;
import PKG.repository.item.TiviRepo;
import PKG.repository.item.VacuumRepo;
import PKG.repository.item.WasherRepo;
import PKG.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemRepo itemrepo;
	
	@Autowired
	private AirConRepo acrepo;
	
	@Autowired
	private CookerRepo cookerrepo;
	
	@Autowired
	private FanRepo fanrepo;
	
	@Autowired
	private FridgeRepo fridgerepo;
	
	@Autowired
	private StoveRepo stoverepo;
	
	@Autowired
	private TiviRepo tvrepo;
	
	@Autowired
	private VacuumRepo vcrepo;
	
	@Autowired
	private WasherRepo washerrepo;
	
	// khởi tạo map tạo mới item
	private Map<Integer, BiFunction<Map<String, Object>, String, ? extends Item>> saveMap;
	
	// khởi tạo map cập nhật item
	private Map<Integer, BiFunction<Map<String, Object>, String, Optional<? extends Item>>> updateMap;
	
	// khởi tạo map tìm kiếm ánh xạ từ category id có phân trang
	private Map<Integer, Function<Pageable, Page<? extends Item>>> findMapByCateIdPagination;
	
	// khởi tạo map tìm kiếm ánh xạ từ category id không phân trang
		private Map<Integer, Supplier<List<? extends Item>>> findMapByCateId;
	
	// khởi tạo map tìm kiếm ánh xạ từ category id và item id
	private Map<Integer, Function<Long, Optional<? extends Item>>> findMapByItemId;
	
	// gán key và value cho các map
	public ItemServiceImpl() {
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
		findMapByCateIdPagination = Map.of(8, p -> washerrepo.findAll(p),
	 		 	 						   2, p -> cookerrepo.findAll(p),
	 		 	 						   1, p -> acrepo.findAll(p),
	 		 	 						   3, p -> fanrepo.findAll(p),
	 		 	 						   4, p -> fridgerepo.findAll(p),
	 		 	 						   5, p -> stoverepo.findAll(p),
	 		 	 						   6, p -> tvrepo.findAll(p),
	 		 	 						   7, p -> vcrepo.findAll(p)
	 			 						   );
		
		findMapByCateId = Map.of(8, () -> washerrepo.findAll(),
				 		 	 2, () -> cookerrepo.findAll(),
				 		 	 1, () -> acrepo.findAll(),
				 		 	 3, () -> fanrepo.findAll(),
				 		 	 4, () -> fridgerepo.findAll(),
				 		 	 5, () -> stoverepo.findAll(),
				 		 	 6, () -> tvrepo.findAll(),
				 		 	 7, () -> vcrepo.findAll()
				 			 );
		
		findMapByItemId = Map.of(8, id -> washerrepo.findById(id),
	 		 	 			 2, id -> cookerrepo.findById(id),
	 		 	 			 1, id -> acrepo.findById(id),
	 		 	 			 3, id -> fanrepo.findById(id),
	 		 	 			 4, id -> fridgerepo.findById(id),
	 		 	 			 5, id -> stoverepo.findById(id),
	 		 	 			 6, id -> tvrepo.findById(id),
	 		 	 			 7, id -> vcrepo.findById(id)
	 			 			 );
		
		saveMap = new HashMap<>();
		saveMap.put(1, (data, image) -> this.addAC(data, image));
		saveMap.put(2, (data, image) -> this.addCooker(data, image));
		saveMap.put(3, (data, image) -> this.addFan(data, image));
		saveMap.put(4, (data, image) -> this.addFridge(data, image));
		saveMap.put(5, (data, image) -> this.addStove(data, image));
        saveMap.put(6, (data, image) -> this.addTV(data, image));
        saveMap.put(7, (data, image) -> this.addVC(data, image));
        saveMap.put(8, (data, image) -> this.addWasher(data, image));
        
        updateMap = new HashMap<>();
        updateMap.put(1, (data, image) -> this.updateAC(data, image));
        updateMap.put(2, (data, image) -> this.updateCooker(data, image));
        updateMap.put(3, (data, image) -> this.updateFan(data, image));
        updateMap.put(4, (data, image) -> this.updateFridge(data, image));
        updateMap.put(5, (data, image) -> this.updateStove(data, image));
        updateMap.put(6, (data, image) -> this.updateTV(data, image));
        updateMap.put(7, (data, image) -> this.updateVC(data, image));
        updateMap.put(8, (data, image) -> this.updateWasher(data, image));
	}
	
	// =================== các dịch vụ duyệt =====================
	@Override
	public List<Item> findAll() {
		return itemrepo.findAll();
	}
	
	@Override
	public Optional<Item> findById(Long id) {
		return itemrepo.findById(id);
	}
	
	// tìm kiếm item bằng itemId
	@Override
	public Optional<? extends Item> findById(Long id, int cateId) {
		return findMapByItemId.get(cateId).apply(id);
	}
	
	// tìm tất cả các item và có phân trang
	@Override
	public Page<Item> findAllandSortByPrice(int currentPage, int rsPerPage, boolean asc) {

		Sort sortPrice;
		if (asc) {
			sortPrice = Sort.by("price").ascending();
		} else {
			sortPrice = Sort.by("price").descending();
		}

		Pageable pageable = PageRequest.of(currentPage, rsPerPage, sortPrice);
		
		Page<Item> returnPage = itemrepo.findAll(pageable);

		return returnPage;
	}
	
	// tìm kiếm có phân trang theo id phân loại và sắp xếp theo giá cả (category name)
	@Override
	public Page<? extends Item> findItemByIdAndSortByPrice(int cateid, int currentPage, int rsPerPage, boolean asc) {

		Sort sortPrice;
		if (asc) {
			sortPrice = Sort.by("price").ascending();
		} else {
			sortPrice = Sort.by("price").descending();
		}

		Pageable pageable = PageRequest.of(currentPage, rsPerPage, sortPrice);

		Page<? extends Item> returnPage = findMapByCateIdPagination.get(cateid).apply(pageable);

		return returnPage;
	}
		
	// tìm danh sách item dựa trên cateid có phân trang
	@Override
	public Page<? extends Item> findAllById(int cateId, Pageable pageable) {
		return findMapByCateIdPagination.get(cateId).apply(pageable);
	}
	
	// tìm danh sách item dựa trên cateid không phân trang
	@Override
	public List<? extends Item> findAllById(int cateId) {
		return findMapByCateId.get(cateId).get();
	}
	
	// ===============================================================
	
	// xóa item
	@Override
	public void deleteById(Long id) {
		itemrepo.deleteById(id);
	}

	// thêm hoặc cập nhật item
	@Override
	public Optional<? extends Item> saveItem(int cateId, Map<String, Object> data, String imagePath, boolean isUpdate) {
		if (!isUpdate) {
			Item item = saveMap.get(cateId).apply(data, imagePath);
			return Optional.of(item);
		} else {
			return updateMap.get(cateId).apply(data, imagePath);
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

		
		return acrepo.save(newAC);
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

		return cookerrepo.save(newCooker);
	}

	private Fan addFan(Map<String, Object> data, String imagePath) {

		Fan newFan = new Fan();

		// thêm các đặc tính chung
		newFan = (Fan) addCommonAttr(newFan, data, imagePath);

		// thêm các đặc tính riêng của sản phẩm quạt gió
		newFan.setCanhQuat((String) data.get("canhquat"));

		newFan.setCongSuatMucGio((String) data.get("congsuatmucgio"));

		newFan.setLoaiQuat((String) data.get("loaiquat"));

		return fanrepo.save(newFan);
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

		return fridgerepo.save(newFridge);
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

		return stoverepo.save(newStove);
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

		return tvrepo.save(newTV);
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

		return vcrepo.save(newVC);
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

		return washerrepo.save(newWasher);
	}
	// ==============================================================================================
	
	// =================== các hàm cập nhật đặc trưng riêng của các sản phẩm =============================
	private Optional<airCon> updateAC(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		airCon currentItem = acrepo.findById(((Number) itemId).longValue()).orElse(null);

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
			
			acrepo.save(currentItem);
			
			return Optional.of(currentItem);
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return Optional.empty();
		}
	}

	private Optional<Cooker> updateCooker(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Cooker currentItem = cookerrepo.findById(((Number) itemId).longValue()).orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Cooker) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm nồi cơm điện
			Double congsuat = (double) data.get("congsuat");
			currentItem.setCongSuat(congsuat.floatValue());

			Double dungtich = (double) data.get("dungtich");
			currentItem.setDungTich(dungtich.floatValue());
			
			cookerrepo.save(currentItem);
			
			return Optional.of(currentItem);
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return Optional.empty();
		}
	}

	private Optional<Fan> updateFan(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Fan currentItem = fanrepo.findById(((Number) itemId).longValue()).orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Fan) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm quạt gió
			currentItem.setCanhQuat((String) data.get("canhquat"));

			currentItem.setCongSuatMucGio((String) data.get("congsuatmucgio"));

			currentItem.setLoaiQuat((String) data.get("loaiquat"));
			
			fanrepo.save(currentItem);
			
			return Optional.of(currentItem);
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return Optional.empty();
		}
	}

	private Optional<Fridge> updateFridge(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Fridge currentItem = fridgerepo.findById(((Number) itemId).longValue()).orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Fridge) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm máy tủ lạnh
			Double congsuat = (double) data.get("congsuat");
			currentItem.setCongSuat(congsuat.floatValue());

			Double dungtich = (double) data.get("dungtich");
			currentItem.setDungTich(dungtich.floatValue());

			currentItem.setKieuTu((String) data.get("kieutu"));
			
			fridgerepo.save(currentItem);
			
			return Optional.of(currentItem);
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return Optional.empty();
		}
	}

	private Optional<Stove> updateStove(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Stove currentItem = stoverepo.findById(((Number) itemId).longValue()).orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Stove) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm bếp
			currentItem.setKichThuocVungNau((String) data.get("kichthuocvungnau"));

			currentItem.setLoaiBep((String) data.get("loaibep"));

			Double tongcongsuat = (double) data.get("tongcongsuat");
			currentItem.setTongCongSuat(tongcongsuat.floatValue());
			
			stoverepo.save(currentItem);
			
			return Optional.of(currentItem);
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return Optional.empty();
		}
	}

	private Optional<Tivi> updateTV(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Tivi currentItem = tvrepo.findById(((Number) itemId).longValue()).orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Tivi) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm tivi
			currentItem.setDoPhanGiai((String) data.get("dophangiai"));

			currentItem.setManHinh((String) data.get("manhinh"));

			Double tansoquet = (double) data.get("tansoquet");
			currentItem.setTanSoQuet(tansoquet.floatValue());
			
			tvrepo.save(currentItem);
			
			return Optional.of(currentItem);
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return Optional.empty();
		}
	}

	private Optional<Vacuum> updateVC(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Vacuum currentItem = vcrepo.findById(((Number) itemId).longValue()).orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Vacuum) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm máy hút bụi
			Double congsuathut = (double) data.get("congsuathut");
			currentItem.setCongSuatHut(congsuathut.floatValue());

			Double dooncaonhat = (double) data.get("dooncaonhat");
			currentItem.setDoOnCaoNhat(dooncaonhat.floatValue());

			currentItem.setLoaiMay((String) data.get("loaimay"));
			
			vcrepo.save(currentItem);
			
			return Optional.of(currentItem);
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return Optional.empty();
		}
	}

	private Optional<Washer> updateWasher(Map<String, Object> data, String imagePath) {
		Object itemId = data.get("itemId");
		Washer currentItem = washerrepo.findById(((Number) itemId).longValue()).orElse(null);

		if (currentItem != null) {
			// thêm các đặc tính chung
			currentItem = (Washer) addCommonAttr(currentItem, data, imagePath);

			// thêm các đặc tính riêng của sản phẩm máy giặt
			currentItem.setHieuSuat((String) data.get("hieusuat"));

			Double khoiluonggiat = (double) data.get("khoiluonggiat");
			currentItem.setKhoiLuongGiat(khoiluonggiat.floatValue());

			currentItem.setLoaiMayGiat((String) data.get("loaimaygiat"));
			
			washerrepo.save(currentItem);
			
			return Optional.of(currentItem);
		} else {
			System.out.print("Không tìm thấy item cụ thể");
			return Optional.empty();
		}
	}
	// ==============================================================================================
}