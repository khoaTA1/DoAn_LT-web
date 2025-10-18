package PKG.service.impl;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import PKG.entity.Item;
import PKG.entity.airCon;
import PKG.entity.Cooker;
import PKG.entity.Fridge;
import PKG.entity.Fan;
import PKG.entity.Stove;
import PKG.entity.Tivi;
import PKG.entity.Vacuum;
import PKG.entity.Washer;
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
	
	// khởi tạo map tạo mới/cập nhật item
	private Map<Integer, Function<Item, ? extends Item>> saveMap;

	// khởi tạo map xóa item
	private Map<String, Consumer<Long>> removeMap;
	
	// khởi tạo map tìm kiếm ánh xạ từ category id
	private Map<Integer, Function<Pageable, Page<? extends Item>>> findMapByCateId;
	
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
		findMapByCateId = Map.of(8, p -> washerrepo.findAll(p),
				 		 	 2, p -> cookerrepo.findAll(p),
				 		 	 1, p -> acrepo.findAll(p),
				 		 	 3, p -> fanrepo.findAll(p),
				 		 	 4, p -> fridgerepo.findAll(p),
				 		 	 5, p -> stoverepo.findAll(p),
				 		 	 6, p -> tvrepo.findAll(p),
				 		 	 7, p -> vcrepo.findAll(p)
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
		
		saveMap = Map.of(8, i -> washerrepo.save((Washer) i), 
			    		 2, i -> cookerrepo.save((Cooker) i), 
			    		 1, i -> acrepo.save((airCon) i),
			    		 3, i -> fanrepo.save((Fan) i),
			    		 4, i -> fridgerepo.save((Fridge) i),
			    		 5, i -> stoverepo.save((Stove) i),
			    		 6, i -> tvrepo.save((Tivi) i),
			    		 7, i -> vcrepo.save((Vacuum) i)
				 		 );
		
		removeMap = Map.of("maygiat", id -> washerrepo.deleteById(id),
				 		 "noicomdien", id -> cookerrepo.deleteById(id),
				 		 "maydieuhoa", id -> acrepo.deleteById(id),
				 		 "quat", id -> fanrepo.deleteById(id),
				 		 "tulanh", id -> fridgerepo.deleteById(id),
				 		 "bep", id -> stoverepo.deleteById(id),
				 		 "tivi", id -> tvrepo.deleteById(id),
				 		 "mayhutbui", id -> vcrepo.deleteById(id)
				 		 );
	}
	
	// tìm item dựa trên itemId, trả về item con cụ thể
	@Override
	public Optional<? extends Item> findById(Long id, int cateId) {
		return findMapByItemId.get(cateId).apply(id);
	}
		
	@Override
	public boolean addItem(int cateId, Item item) {
		
		try {
			saveMap.get(cateId).apply(item);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	// tìm kiếm có phân trang và sắp xếp theo tên phân loại (category name)
	@Override
	public Page<? extends Item> findAndSortItemById(int cateid, int currentPage, int rsPerPage, boolean asc) {
		
		Sort sortPrice;
		if (asc) {
			sortPrice = Sort.by("price").ascending();
		} else {
			sortPrice = Sort.by("price").descending();
		}
		
		Pageable pageable = PageRequest.of(currentPage, rsPerPage, sortPrice);
		
		
		Page<? extends Item> returnPage = findMapByCateId.get(cateid).apply(pageable);
				
		return returnPage;
	}
}
