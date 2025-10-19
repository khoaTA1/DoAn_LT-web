package PKG.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;

import PKG.entity.Item;

public interface ItemService {

	Optional<? extends Item> saveItem(int cateId, Map<String, Object> data, String imagePath, boolean isUpdate);

	Page<? extends Item> findAndSortItemById(int cateid, int currentPage, int rsPerPage, boolean asc);

	Optional<? extends Item> findById(Long id, int cateId);

	void deleteById(Long id);
	

}
