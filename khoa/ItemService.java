package PKG.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import PKG.entity.Item;

public interface ItemService {
	boolean addItem(int cateId, Item item);

	Page<? extends Item> findAndSortItemById(int cateid, int currentPage, int rsPerPage, boolean asc);

	Optional<? extends Item> findById(Long id, int cateId);

}
