package PKG.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import PKG.entity.Item;
import PKG.entity.airCon;

public interface ItemService {

	Optional<? extends Item> saveItem(int cateId, Map<String, Object> data, String imagePath, boolean isUpdate);

	Optional<? extends Item> findById(Long id, int cateId);

	void deleteById(Long id);

	Page<? extends Item> findItemByIdAndSortByPrice(int cateid, int currentPage, int rsPerPage, boolean asc);

	Page<? extends Item> findAllById(int cateId, Pageable pageable);

	List<Item> findAll();

	Page<Item> findAllandSortByPrice(int currentPage, int rsPerPage, boolean asc);

	Optional<Item> findById(Long id);

	List<? extends Item> findAllById(int cateId);
	

}
