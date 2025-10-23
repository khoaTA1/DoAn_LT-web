package PKG.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import PKG.entity.Category;
import PKG.service.CategoryService;
import PKG.service.ItemService;

@ControllerAdvice
public class GlobalInjection {
	@Autowired
	CategoryService cateserv;
	
	@Autowired
	ItemService itemserv;
	
	@ModelAttribute("cateList")
	public List<Category> getAllCategories() {
	    return cateserv.findAll();
	}
}
